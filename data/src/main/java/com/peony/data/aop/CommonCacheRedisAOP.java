package com.peony.data.aop;

import com.peony.common.entity.Entity;
import com.peony.common.util.StringUtils;
import com.peony.data.config.CacheConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 切面缓存的redis实现
 * @author li.tiancheng
 * 创建时间 2020-9-17下午15:12:00
 */
@Aspect
@Component
public class CommonCacheRedisAOP extends CommonCacheAOP {
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> objTemplate;
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> setTemplate;
    private static final String IDS = "_ids";
    /**
     * 缓存前缀
     */
    private static final String tag = "";
    /**
     * system.web.properties中use_cache=0配置,0:表示不启用缓存,1表示启用缓存
     * @return
     */
    protected boolean isCache() {
            return true;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        long time = System.currentTimeMillis();
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        Object retVal = null;

        if(!isCache()){
            retVal = pjp.proceed();//正常执行程序
            return retVal;
        }

        //如果方法配置了cache
        if(method.isAnnotationPresent(CacheConfig.class)){
            CacheConfig cache = method.getAnnotation(CacheConfig.class);
            String namespace = cache.namespace();
            if(StringUtils.isEmpty(namespace)){
                namespace = pjp.getTarget().getClass().getSimpleName();
            }
            String methodName = method.getName();
			if(methodName.indexOf(METHOD_FIND) == 0 || methodName.indexOf(METHOD_GET) == 0 || methodName.indexOf(METHOD_LIST) == 0){//如果是查询方法
                namespace = tag+ "_" + namespace;
                String key = getKey(pjp, namespace, cache.key(), cache.useMD5());
                ValueOperations<String, Object> vo = objTemplate.opsForValue();
                Object cacheObj = vo.get(key);
                if(cacheObj == null){//没命中缓存
                    retVal = pjp.proceed();//正常执行程序
                    vo.set(key, (Serializable)retVal, cache.time(), TimeUnit.SECONDS);//将结果放入缓存
                    //将key放入namespace中
                    SetOperations<String, String> so = setTemplate.opsForSet();
                    if(method.getName().equals("findById")) {
                        so.add(namespace + IDS, key);
                    }else {
                        so.add(namespace, key);
                    }
//    				System.out.println("缓存无效\t" + getKey(pjp, namespace, cache.key(), false));
                }else{//命中缓存
                    retVal = cacheObj;//直接返回结果,不执行程序
//    				System.out.println("缓存命中\t" + getKey(pjp, namespace, cache.key(), false));
                }
            }else if(methodName.indexOf(METHOD_DELETE) == 0 ||
            		methodName.indexOf(METHOD_SAVE) == 0 ||
            				methodName.indexOf(METHOD_UPDATE) == 0){//如果是增删改方法
                retVal = pjp.proceed();//正常执行程序
                if (cache.deleteNamespace()) {// 清除namespace
                    String[] namespaces = namespace.split(",");
                    for (int i = 0; i < namespaces.length; i++) {
                        deleteKeys(tag + "_" + namespaces[i]);
                    }

                }
                if(method.getName().indexOf(METHOD_SAVE) == 0) {
                    if(retVal instanceof Entity) {
                        processSaveMethod(retVal, tag + "_" + namespace, pjp, cache.time());
                    }
                    if(retVal instanceof List) {
                        List<Object> list = (List<Object>)retVal;
                        for (int i = 0, len = list.size(); i < len; i++) {
                            if(list.get(i) instanceof Entity) {
                                processSaveMethod(list.get(i), tag + "_" + namespace, pjp, cache.time());
                            }
                        }
                    }
                }
                if(method.getName().indexOf(METHOD_UPDATE) == 0) {
                    if (cache.deleteNamespace()) {// 清除namespace
                        String[] namespaces = namespace.split(",");
                        for (int i = 0; i < namespaces.length; i++) {
                            deleteKeys(tag + "_" + namespaces[i]+ IDS);
                        }
                    }
                }
            }else {
            	retVal = pjp.proceed();
            }
        }else{
            retVal = pjp.proceed();//正常执行程序
//    		System.out.println("未配置cache标签\t" + pjp.getTarget().getClass().getSimpleName() + "\t" + method.getName());
        }

        time = System.currentTimeMillis() - time;
        //记录被切面代理的所有方法的执行时间
//        LogFactory.getInstance().getLog(LogFactory.LOG_TIME).info(new Throwable(), getTimeMessage(pjp, time));
        return retVal;
    }

    /**
     * 处理save方法,直接将保存的object放入缓存中
     * 并把key单独存放在namespace + "_ids"的集合中,不与其他key放在一起
     * @param retVal
     * @param namespace
     * @param pjp
     * @param cacheTime
     */
    private void processSaveMethod(Object retVal, String namespace, ProceedingJoinPoint pjp, int cacheTime) {
        if(retVal instanceof Entity) {
            Entity cm = (Entity)retVal;
            String key = this.getKey(pjp, namespace, String.valueOf(cm.getId()));
            ValueOperations<String, Object> vo = objTemplate.opsForValue();
            vo.set(key, (Serializable)retVal, cacheTime, TimeUnit.SECONDS);
            SetOperations<String, String> so = setTemplate.opsForSet();
            so.add(namespace + IDS, key);
        }
    }

    /**
     * 删除namespace对应的key
     * @param namespace
     */
    private void deleteKeys(String namespace) {
        // 获得namespace下所有key
        SetOperations<String, String> set = setTemplate.opsForSet();
        Set<String> sets = set.members(namespace);
        // 清除所有key
        setTemplate.delete(sets);
        setTemplate.delete(namespace);
        // System.out.println("清除缓存\t" +getKey(pjp, namespace,
        // cache.key(), cache.useMD5())+ "\t"+ deleteKeys.size());
    }

}
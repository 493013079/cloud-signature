package com.peony.data.aop;

import com.peony.common.entity.Entity;
import com.peony.common.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 缓存切面的公共类
 * 主要实现3个功能
 * 1.记录切面中方法的执行时间,记录到time.log中
 * 2.把切面中方法抛出的异常记录到exception.log中
 * 3.在doAround中判断方法是否配置了cache,如果配置了cache就进行下列操作
 * 一.判断方法名称,如果是find开头的,先再缓存中找,找到缓存直接返回,不执行方法,找不到缓存再执行方法,然后将结果缓存,并将key保存在namespace中
 * 二.如果是增删改方法,正常执行,如果配置了清除namespace,就将namespace对应的缓存清除
 * 第三个功能由实现类去实现
 *
 * @author li.tiancheng
 * 创建时间 2020-9-17下午17:04:17
 */
@Slf4j
public abstract class CommonCacheAOP {
    protected static final String SPLIT = "_";
    protected static final String METHOD_FIND = "find";
    protected static final String METHOD_DELETE = "delete";
    protected static final String METHOD_SAVE = "save";
    protected static final String METHOD_UPDATE = "update";
    protected static final String METHOD_GET = "get";
    protected static final String METHOD_LIST = "list";

    public abstract Object doAround(ProceedingJoinPoint pjp) throws Throwable;

    /**
     * 返回执行时间详细信息(类.方法(参数...)用时:n)
     *
     * @param pjp
     * @param time
     * @return
     */
    protected String getTimeMessage(ProceedingJoinPoint pjp, long time) {
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        StringBuilder sb = new StringBuilder();
        sb.append(pjp.getTarget().getClass().getName()).append(".").append(method.getName()).append("(");
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            sb.append(args[i].getClass().getSimpleName());
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")\t用时(秒):").append(time / 1000);

        return sb.toString();
    }

    public void doAfter(JoinPoint jp) {

    }

    public void doBefore(JoinPoint jp) {
    }

    /**
     * 把切面中所有异常都记录到exception.log中
     *
     * @param jp
     * @param ex
     */
    public void doThrowing(JoinPoint jp, Throwable ex) {
        Exception e2 = null;

        try {
            e2 = (Exception) ex;
            log.error("CommonCacheAOP中记录异常错误!", e2);
        } catch (Exception var5) {
            System.out.println("CommonCacheAOP中记录异常错误!");
        }

    }

    /**
     * 生成缓存的key
     * 参数md5为false,key的格式为:namespace_methodName_参数类型_参数值_参数类型_参数值_....
     * 参数md5为true, key的格式为:namespace_methodName_md5(参数类型_参数值_参数类型_参数值_....),性能有损耗,大约20毫秒,
     * 参数如果有map或list建议使用md5,如果参数都是基本类型,建议不适用md5
     *
     * @param pjp
     * @param namespace 如果为空,用类名代替
     * @param useMD5
     * @return
     */
    protected String getKey(ProceedingJoinPoint pjp, String namespace, String key, boolean useMD5) {
        StringBuffer sb = new StringBuffer();
        namespace = namespace + SPLIT + pjp.getTarget().getClass().getSimpleName();
        if (StringUtils.isEmpty(key)) {
            MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
            Object[] args = pjp.getArgs();
            if (useMD5) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        if (args[i] instanceof Entity) {
                            sb.append(args[i].getClass().getSimpleName()).append(SPLIT).append(((Entity) args[i]).getId()).append(SPLIT);
                        } else {
                            sb.append(args[i].getClass().getSimpleName()).append(SPLIT).append(args[i].toString()).append(SPLIT);
                        }
                    } else {
                        sb.append("null");
                    }
                }
                String md5 = MD5Utils.getMD5(sb.toString());
                sb = new StringBuffer();
                sb.append(namespace).append(SPLIT).append(joinPointObject.getMethod().getName())
                        .append(SPLIT).append(md5);
            } else {
                sb.append(namespace).append(SPLIT).append(joinPointObject.getMethod().getName()).append(SPLIT);
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        if (args[i] instanceof Entity) {
                            sb.append(args[i].getClass().getSimpleName()).append(SPLIT).append(((Entity) args[i]).getId()).append(SPLIT);
                        } else {
                            sb.append(args[i].getClass().getSimpleName()).append(SPLIT).append(args[i].toString()).append(SPLIT);
                        }
                    } else {
                        sb.append("null");
                    }
                }
            }
        } else {
            sb.append(namespace).append(SPLIT).append(useMD5 ? MD5Utils.getMD5(key) : key);
        }
        return sb.toString();
    }

    /**
     * save方法时生成key
     *
     * @param pjp
     * @param namespace
     * @param id
     * @return
     */
    protected String getKey(ProceedingJoinPoint pjp, String namespace, String id) {
        StringBuffer sb = new StringBuffer();
        namespace = namespace + SPLIT + pjp.getTarget().getClass().getSimpleName();
        sb.append(namespace).append(SPLIT).append("findById").append(SPLIT);
        sb.append("String").append(SPLIT).append(id).append(SPLIT);
        return sb.toString();
    }
}

package com.peony.web.aop;

import com.peony.common.entity.field.PermissionKey;
import com.peony.common.entity.field.RoleType;
import com.peony.common.web.Logical;
import com.peony.common.web.helper.AuthHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author hk
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationAspect {

    @NotNull
    private final AuthHelper authHelper;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void patchMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() {
    }

    @Pointcut("execution(public * com.peony.web.controller..*.*(..))")
    public void methodPointCut() {
    }

    @Around("(getMapping() || postMapping() || putMapping() || patchMapping() || deleteMapping()) && methodPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        checkToken(joinPoint);
        checkRole(joinPoint);
        checkPermission(joinPoint);
        return joinPoint.proceed();
    }

    private void checkToken(ProceedingJoinPoint joinPoint) {
        Authenticated annotation = getAnnotation(joinPoint, Authenticated.class);
        if (annotation != null && !annotation.value()) {
            return;
        }
        authHelper.checkUser();
    }

    private void checkRole(ProceedingJoinPoint joinPoint) {
        RequireRole annotation = getAnnotation(joinPoint, RequireRole.class);
        if (annotation == null) {
            return;
        }
        RoleType[] roleTypes = annotation.value();
        authHelper.checkRole(roleTypes);
    }

    private void checkPermission(ProceedingJoinPoint joinPoint) {
        RequirePermissions annotation = getAnnotation(joinPoint, RequirePermissions.class);
        if (annotation == null) {
            return;
        }
        PermissionKey[] permissionKeys = annotation.value();
        Logical logical = annotation.logic();
        authHelper.checkPermission(permissionKeys, logical);
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        // 首先从类读取注解信息
        Class cls = joinPoint.getSignature().getDeclaringType();
        log.info("进入切面，类为: {}", cls.getName());
        T annotation = (T) cls.getAnnotation(annotationClass);

        // 从方法里面读取认证的注解信息,如果存在注解信息，那么覆盖类的注解信息
        if (joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            if (method.getAnnotation(annotationClass) != null) {
                annotation = method.getAnnotation(annotationClass);
            }
        }
        return annotation;
    }

}

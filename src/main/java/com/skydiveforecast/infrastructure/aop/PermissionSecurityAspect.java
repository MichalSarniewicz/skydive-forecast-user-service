package com.skydiveforecast.infrastructure.aop;

import com.skydiveforecast.domain.annotation.PermissionSecurity;
import com.skydiveforecast.infrastructure.security.PermissionSecurityService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionSecurityAspect {

    private final PermissionSecurityService permissionSecurityService;

    @Around("@annotation(com.skydiveforecast.domain.annotation.PermissionSecurity)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PermissionSecurity annotation = method.getAnnotation(PermissionSecurity.class);

        if (annotation == null) {
            throw new AccessDeniedException("Access Denied: PermissionSecurity annotation not found on method.");
        }

        if (!permissionSecurityService.hasPermission(annotation.permission())) {
            throw new AccessDeniedException("Access Denied: You do not have the required permission '" + annotation.permission() + "'");
        }

        return joinPoint.proceed();
    }
}
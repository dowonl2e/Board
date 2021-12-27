package com.myapp.board.aop;

import java.util.Collections;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
@Aspect
public class LoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.myapp.board..controller.*Controller.*(..))"
			+ "or execution(* com.myapp.board..service.*impl.*(..))"
			+ "or execution(* com.myapp.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String type = "";
		//실행되는 컨틀롤러(패키지 전체 포함) 정보를 가져온다.
		String name = joinPoint.getSignature().getDeclaringTypeName();
		Object[] params = joinPoint.getArgs();
		if(name.contains("Controller")) {
			type = "Controller ==> ";
		}
		else if(name.contains("Service")) {
			type = "Service ==> ";
		}
		else if(name.contains("Mapper")) {
			type = "Mapper ==> ";
		}
		
		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		if(!ObjectUtils.isEmpty(params)) {
			for(Object param : params) {
				if(!ObjectUtils.isEmpty(param)) {
					logger.debug("Parameter ==> " + param.toString());
				}
			}
		}
		return joinPoint.proceed();
	}
}

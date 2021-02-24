package com.libs.logging;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libs.logging.annotation.Logging;

@Aspect
public class LoggingAspect {
	
	@Around(value="@annotation(loggingAnnotation)", argNames="loggingAnnotation")
	public Object LogWrapper(ProceedingJoinPoint pjp, Logging loggingAnnotation) throws Throwable {
		Date start = new Date();
		
		Logger log = LoggerFactory.getLogger(pjp.getSignature().getDeclaringTypeName());
		log.info("- Execute {}.{}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
		logParameter(pjp);
		try {
			Object retVal = pjp.proceed();
			Date end = new Date();
			log.info("- Finish Execute with success {}.{}() in {}ms", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), end.getTime() - start.getTime());
			return retVal;
		} catch (Throwable e) {
			Date end = new Date();
			log.error("- Finish Execute with error {}.{}() in {}ms", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), end.getTime() - start.getTime(), e);
			throw e;
		}
	}

	private void logParameter(ProceedingJoinPoint pjp) {
		Logger log = LoggerFactory.getLogger(pjp.getSignature().getDeclaringTypeName());

		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		Parameter[] params = method.getParameters();
		for (int i=0; i<params.length; i++) {
			Parameter param = params[i];
			Object arg = pjp.getArgs()[i];
			if(param.isAnnotationPresent(Logging.class)) {
				if(arg == null) {
					log.info("Parameter [{}], value : null", param.getName());
				} else if(arg instanceof String) {
					log.info("Parameter [{}], value : {}", param.getName(), arg);
				} else {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String json = mapper.writeValueAsString(arg);
						log.info("Parameter [{}], value : {}", param.getName(), json);
					} catch (JsonProcessingException e) {
						log.info("Parameter [{}], value : {}", param.getName(), arg);
					}
				}
			}
		}
	}

	
}

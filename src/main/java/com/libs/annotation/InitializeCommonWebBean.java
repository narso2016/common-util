package com.libs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.libs.config.InitializerCommonWeb;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(InitializerCommonWeb.class)
public @interface InitializeCommonWebBean {

}

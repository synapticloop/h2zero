package com.synapticloop.h2zero.generator.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks a class as being a validator of the h2zero file which
 * will be invoked when the h2zero file has been parsed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface H2ZeroValidator {
}

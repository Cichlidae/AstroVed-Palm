/**
 * 
 */
package ru.premaservices.extention.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


/**
 * @author Karthik/Phil Zoio via Prema 
 * 
 * (http://karthikg.wordpress.com/2009/11/08/learn-to-customize-spring-mvc-controller-method-arguments/)
 * (http://code.google.com/p/impala-extensions/source/browse/trunk/impala-extension-mvc)
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttribute {
	
	/**
     * The name of the Session attribute to bind to.
     */
    String value() default "";

    /**
     * Whether the parameter is required.
     * Default is true, leading to an exception thrown in case
     * of the parameter missing in the request. Switch this to
     * false if you prefer a
     * null in case of the parameter missing.
     */
    boolean required() default true;

}

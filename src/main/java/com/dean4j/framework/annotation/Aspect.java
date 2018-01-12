package com.dean4j.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author hunan
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}

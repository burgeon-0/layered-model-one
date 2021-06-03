package org.burgeon.sbd.adapter.common.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不能与@RequestBody、@ModelAttribute一起使用，
 * 如果使用了Spring的参数注解，Spring会优先使用Spring默认的参数解析器
 *
 * @author Sam Lu
 * @date 2021/6/2
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CamelCaseToSnakeCase {
}

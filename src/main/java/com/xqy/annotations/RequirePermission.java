package com.xqy.annotations;

import java.lang.annotation.*;

/**
 * 权限管理
 *
 * @Author xu
 * @Date 2023/9/30 11:54
 * @Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    // 权限码
    String code() default "";
}

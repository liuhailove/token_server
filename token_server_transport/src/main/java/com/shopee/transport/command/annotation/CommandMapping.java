package com.shopee.transport.command.annotation;

import java.lang.annotation.*;

/**
 * 命令映射
 *
 * @author honggang.liu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface CommandMapping {

    /**
     * 命令路径
     */
    String name();

    /**
     * 命令描述
     */
    String desc();
}

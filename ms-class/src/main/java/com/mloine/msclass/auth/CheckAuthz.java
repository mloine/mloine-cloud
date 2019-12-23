package com.mloine.msclass.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description: 
 *
 * @author: mloine
 *      Retention 制定注解的保留策略
 *          RUNTIME 直接会在字节码中存在 并且可以通过反射获取
 *      元注解：注解咋注解类上的注解
 *
 * @create: 2019/12/20 22:24
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthz {
    /**
     * 判断用户角色
     * @return
     */
    String hasRole();
}

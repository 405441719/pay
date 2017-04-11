package com.payinxl.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @类功能说明：请求校验注解，需要进行校验session及权限的方法添加
 * @公司名称：Noname
 * @作者：Miner
 * @创建时间：2013-1-3 下午08:10:32
 * @版本：V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestSecurity{
	ActionType[] value();
}
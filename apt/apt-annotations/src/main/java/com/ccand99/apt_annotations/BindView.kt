package com.ccand99.apt_annotations

/**
 * 用来注入view
 * @author markrenChina
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class BindView(
    val value: Int
)

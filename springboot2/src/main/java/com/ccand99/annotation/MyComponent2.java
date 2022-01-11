package com.ccand99.annotation;

import java.lang.annotation.*;

/**
 * {@link MyComponent} 派生
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyComponent //元注解 ，实现 @Component "派生性"
public @interface MyComponent2 {
}

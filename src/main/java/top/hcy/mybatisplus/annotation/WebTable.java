package top.hcy.mybatisplus.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface WebTable {
    String value() default "";
}
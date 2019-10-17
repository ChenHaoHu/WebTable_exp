package top.hcy.mybatisplus.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface WebField {
    String value() default "";
}
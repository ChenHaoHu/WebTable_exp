package top.hcy.webtable.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface WebMapper {
    Class value();
}
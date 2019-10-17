package top.hcy.mybatisplus.config;


import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.hcy.mybatisplus.annotation.WebMapper;
import top.hcy.mybatisplus.annotation.WebTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Configuration
public class AnnotateConfig {

    String packageName = "top.hcy.mybatisplus";


    @Bean
    public  Set<Class<?>> webTableClass(){
        Reflections f = new Reflections(packageName);
        Set<Class<?>> set = f.getTypesAnnotatedWith(WebTable.class);
        return set;
    }

    @Bean
    public HashMap<String,Class> webMapperClass(){
        Reflections f = new Reflections(packageName);
        Set<Class<?>> set = f.getTypesAnnotatedWith(WebMapper.class);
        HashMap<String,Class> map =new HashMap<>();
        Iterator<Class<?>> iterator = set.iterator();
        while (iterator.hasNext()){
            Class<?> next = iterator.next();
            WebMapper declaredAnnotation = next.getDeclaredAnnotation(WebMapper.class);
            Class value = declaredAnnotation.value();
            map.put(value.getSimpleName(),next);
        }
        return map;
    }



}

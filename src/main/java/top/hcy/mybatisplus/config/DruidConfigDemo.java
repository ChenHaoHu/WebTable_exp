package top.hcy.mybatisplus.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid 配置类
 * @author gby
 * 2018-09-04
 */
@SuppressWarnings("all")
@Configuration
public class DruidConfigDemo {

    /**
     * 配置监控服务器
     *
     * @return 返回监控注册的servlet对象
     */
    @Bean
    public ServletRegistrationBean statViewServletDemo() {
        ServletRegistrationBean srb = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加控制台管理用户
        srb.addInitParameter("loginUsername", "hcy");
        srb.addInitParameter("loginPassword", "hcy");
        // 是否能够重置数据
        srb.addInitParameter("resetEnable", "true");
        return srb;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilterDemo() {
        FilterRegistrationBean frb = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        frb.addUrlPatterns("/*");
        // 忽略过滤格式
        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return frb;
    }

}

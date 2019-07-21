package org.activiti.app.ui;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * @ClassName ActivitiUIApplication
 * @Description app-ui程序入口类
 * @Author wangmingliang
 * @Date 2019-07-21 17:13
 */
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@Import({ApplicationConfiguration.class})
public class ActivitiUIApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiUIApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return builder.sources(ActivitiUIApplication.class);
    }

    @Bean
    public ServletRegistrationBean apiDispatcher(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        DispatcherServlet api =
                new DispatcherServlet();
        api.setContextClass(AnnotationConfigWebApplicationContext.class);
        api.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());
        servletRegistrationBean.setServlet(api);
        // 拦截路径
        servletRegistrationBean.addUrlMappings("/api/*");
        // 优先级
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setAsyncSupported(true);
        servletRegistrationBean.setName("api");
        return  servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean appDispatcher(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        DispatcherServlet app =
                new DispatcherServlet();
        app.setContextClass(AnnotationConfigWebApplicationContext.class);
        app.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());
        servletRegistrationBean.setServlet(app);
        servletRegistrationBean.addUrlMappings("/app/*");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setAsyncSupported(true);
        servletRegistrationBean.setName("app");
        return  servletRegistrationBean;
    }


}

package com.watering.config;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                    allowedOrigins("http://localhost:8000"). //允许跨域的域名，可以用*表示允许任何域名使用
                    allowedMethods("*"). //允许任何方法（post、get等）
                    allowedHeaders("*"). //允许任何请求头
                    allowCredentials(true). //带上cookie信息
                    exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }

//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //  你需要跨域的地址  注意这里的 127.0.0.1 != localhost
//        // * 表示对所有的地址都可以访问
//        corsConfiguration.addAllowedOrigin("http://192.168.31.42:8000");
//        //  跨域的请求头
//        corsConfiguration.addAllowedHeader("*"); // 2
//        //  跨域的请求方法
//        corsConfiguration.addAllowedMethod("*"); // 3
//        //加上了这一句，大致意思是可以携带 cookie
//        //最终的结果是可以 在跨域请求的时候获取同一个 session
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        //配置 可以访问的地址
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }




}

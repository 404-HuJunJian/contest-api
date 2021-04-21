package com.watering.config;

import com.alibaba.fastjson.JSONObject;
import com.watering.constant.LoginResponseCodeConst;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.security.MySecurityInterceptor;
import com.watering.security.MyUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/22/12:35
 * @Description: springSecurity配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //获取数据库中的用户返回userDetail
    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    //权限授权过滤器
    @Autowired
    private MySecurityInterceptor mySecurityInterceptor;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                //设置全部路径需要认证
                .antMatcher("/**").authorizeRequests()
                //匹配的路径全部运行通过
                .antMatchers("/login**","logout**","/swagger**","/**","/**/*.jpg","/**/*.png").permitAll()
                //这里使用的时候不能带ROLE_前缀,其实用了数据库导入url可以不要ROLE_前缀
//                .antMatchers("/uel").hasRole("role")
                //所有路径需要认证
                .anyRequest().authenticated()
                .and()
                //这里必须要写formLogin()，不然原有的UsernamePasswordAuthenticationFilter不会出现，也就无法配置我们重新的UsernamePasswordAuthenticationFilter
                //开启登录
                .formLogin()
                .permitAll()
                .and()
                //开启登出
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                //.deleteCookies()
                .and()
                .csrf().disable();

        //session管理
        http.sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy());

        //解决x-frame-options deny
        http.headers().frameOptions().disable();

        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http
                .addFilterAt(usernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                //未登录进行请求返回信息
                .authenticationEntryPoint(authenticationEntryPoint());
        //添加url和权限授权过滤器
        http
                .addFilterAt(mySecurityInterceptor,FilterSecurityInterceptor.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        http.csrf().disable()
                .cors()
                .and().servletApi().disable()
                .requestCache().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置密码加密格式
        //配置userDetailsService 通过username获得到用户的权限等信息
        //会把登录请求的password加密
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //配置自定义过滤器
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        //如果这里不配置就要自己写AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        //设置认证成功处理器
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        //设置认证失败处理器
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        //设置过滤的url
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    //认证成功处理器
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                String responseString= JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.LOGIN_SUCCESS));
                responseHandler(httpServletResponse,responseString);
            }
        };
    }

    //认证失败处理器
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                String responseString = JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.ACCOUNT_ERROR));
                responseHandler(httpServletResponse,responseString);
            }
        };
    }

    //登出成功处理器
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                String responseString = JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.LOGOUT_SUCCESS));
                responseHandler(httpServletResponse,responseString);
            }
        };
    }

    //未登录访问处理器
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                String responseString= JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.LOGIN_ERROR));
                responseHandler(httpServletResponse,responseString);
            }
        };
    }

    //授权失败处理器
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                      String responseString = JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.NOT_HAVE_PRIVILEGES));
                      responseHandler(httpServletResponse,responseString);
            }
        };
    }

    //session过期或无效处理
    //好像过期会自动给一个sessionId
    //无效不会自动生成
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return (httpServletRequest, httpServletResponse) -> {
            String responseString = JSONObject.toJSONString(ResponseDTO.wrap(LoginResponseCodeConst.SESSION_ERROR));
            responseHandler(httpServletResponse,responseString);
        };
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        //指定允许跨域的请求(*所有)：http://wap.ivt.guansichou.com
//        configuration.setAllowedOrigins(Arrays.asList("http://192.168.31.42:8000"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
//        // setAllowCredentials(true) is important, otherwise:
//        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
//        configuration.setAllowCredentials(true);
//        // setAllowedHeaders is important! Without it, OPTIONS preflight request
//        // will fail with 403 Invalid CORS request
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "X-User-Agent", "Content-Type"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    //返回统一处理
    private void responseHandler(HttpServletResponse httpServletResponse, String responseString) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(responseString);
        printWriter.flush();
        printWriter.close();
    }

}

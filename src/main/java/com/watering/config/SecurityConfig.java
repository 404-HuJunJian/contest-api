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
 * @Description: springSecurity?????????
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //?????????????????????????????????userDetail
    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    //?????????????????????
    @Autowired
    private MySecurityInterceptor mySecurityInterceptor;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                //??????????????????????????????
                .antMatcher("/**").authorizeRequests()
                //?????????????????????????????????
                .antMatchers("/login**","logout**","/swagger**","/**","/**/*.jpg","/**/*.png").permitAll()
                //??????????????????????????????ROLE_??????,???????????????????????????url????????????ROLE_??????
//                .antMatchers("/uel").hasRole("role")
                //????????????????????????
                .anyRequest().authenticated()
                .and()
                //??????????????????formLogin()??????????????????UsernamePasswordAuthenticationFilter????????????????????????????????????????????????UsernamePasswordAuthenticationFilter
                //????????????
                .formLogin()
                .permitAll()
                .and()
                //????????????
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                //.deleteCookies()
                .and()
                .csrf().disable();

        //session??????
        http.sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy());

        //??????x-frame-options deny
        http.headers().frameOptions().disable();

        //????????????Filter??????????????????UsernamePasswordAuthenticationFilter
        http
                .addFilterAt(usernamePasswordAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                //?????????????????????????????????
                .authenticationEntryPoint(authenticationEntryPoint());
        //??????url????????????????????????
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
        //????????????????????????
        //??????userDetailsService ??????username?????????????????????????????????
        //?????????????????????password??????
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //????????????????????????
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        //????????????????????????????????????AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        //???????????????????????????
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        //???????????????????????????
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        //???????????????url
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    //?????????????????????
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

    //?????????????????????
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

    //?????????????????????
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

    //????????????????????????
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

    //?????????????????????
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

    //session?????????????????????
    //??????????????????????????????sessionId
    //????????????????????????
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
//        //???????????????????????????(*??????)???http://wap.ivt.guansichou.com
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

    //??????????????????
    private void responseHandler(HttpServletResponse httpServletResponse, String responseString) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(responseString);
        printWriter.flush();
        printWriter.close();
    }

}

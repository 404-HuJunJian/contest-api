package com.watering.security;

import com.alibaba.fastjson.JSONObject;
import com.watering.utils.GetRequestJsonUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/22/17:26
 * @Description: 获取用户并进行认证过滤器
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        try {
            JSONObject jsonObject = GetRequestJsonUtil.getRequestJsonObject(request);
//            System.out.println(jsonObject);
            username = String.valueOf(jsonObject.get("username"));
            password = String.valueOf(jsonObject.get("password"));
            username = (username != null) ? username : "";
            password = (password != null) ? password : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

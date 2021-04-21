package com.watering.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/08/16:36
 * @Description:
 */
public class GetCurrentUser {

    public static String getUserRole(){
        Collection<GrantedAuthority> collection = (Collection) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<GrantedAuthority> iterator = collection.iterator();
        return iterator.next().getAuthority();
    }

    public static Object getUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

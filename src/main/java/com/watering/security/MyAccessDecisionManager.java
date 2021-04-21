package com.watering.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/22/16:50
 * @Description: 权限认证管理器
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {


    /**
     * @param authentication  包含了UserDetails用户信息
     * @param object  包含了request请求信息
     * @param collection  权限资源获取中getAttributes(Object object)方法中返回的权限集合
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        //资源不需要权限
        if(collection == null || collection.size() <= 0){
            return;
        }

        //用户权限和资源所需权限对比
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while(iterator.hasNext()){
            String needRole = iterator.next().getAttribute();
            for (GrantedAuthority grantRole:authentication.getAuthorities()){
                if(needRole.trim().equals(grantRole.getAuthority().trim())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("no privilege");
    }

    //返回true
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    //返回true
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

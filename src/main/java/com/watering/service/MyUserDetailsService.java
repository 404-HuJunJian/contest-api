package com.watering.service;

import com.alibaba.druid.util.StringUtils;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/22/16:39
 * @Description: 根据登录名获取UserDetails(用service获取)
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;

    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    //根据用户名数据库查找用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        List<GrantedAuthority> list = new  ArrayList<>();
        if(username.contains("_")){
            HrEntity hrEntity = hrEntityMapper.selectByUserName(username);
            ManagerEntity managerEntity = managerEntityMapper.selectByUserName(username);
            if(null!=hrEntity&&null!=managerEntity){
                throw new UsernameNotFoundException("用户名重复");
            }else if (null != hrEntity){
                list.add(generateGrantedAuthority(RoleDTO.Type.ROLE_HR));
                hrEntity.setAuthorities(list);
                userDetails = hrEntity;
            }else if (null != managerEntity){
                list.add(generateGrantedAuthority(RoleDTO.Type.ROLE_MANAGER));
                managerEntity.setAuthorities(list);
                userDetails = managerEntity;
            }else{
                throw new UsernameNotFoundException("用户名不存在");
            }
        }else{
            EnterpriseEntity enterpriseEntity= enterpriseEntityMapper.selectByUserName(username);
            if (null == enterpriseEntity)
                throw new UsernameNotFoundException("用户名不存在");
            list.add(generateGrantedAuthority(RoleDTO.Type.ROLE_ENTERPRISE));
            enterpriseEntity.setAuthorities(list);
            userDetails = enterpriseEntity;
        }
        return userDetails;
    }

    //根据角色选择生成权限
    private GrantedAuthority generateGrantedAuthority(RoleDTO.Type type){
        return new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return type.getType();
            }
        };
    }

}

package com.watering.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.access.ConfigAttribute;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/21/16:56
 * @Description: 角色类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO implements ConfigAttribute {

    private Integer id;
    private String name;

    //返回角色的名称用于权限认证
    @Override
    public String getAttribute() {
        return this.name;
    }

    public enum Type{
        ROLE_ENTERPRISE("ROLE_ENTERPRISE"),ROLE_HR("ROLE_HR"),ROLE_MANAGER("ROLE_MANAGER");

        private String type;

        Type(String type){
            this.type = type;
        }

        public String getType(){
            return type;
        }

    }

}

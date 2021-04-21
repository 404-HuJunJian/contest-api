package com.watering.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/21/16:16
 * @Description: 权限类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PermissionDTO {

    private Integer id;
    private List<ConfigAttribute> roles;
    private String url;
    private String description;

}

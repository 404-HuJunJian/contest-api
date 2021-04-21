package com.watering.domain.DTO.manager;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/01/17:13
 * @Description:
 */
@Data
public class ManagerAddDTO {
    private String username;
    private String password;
    private String name;
    private Integer depid;
}

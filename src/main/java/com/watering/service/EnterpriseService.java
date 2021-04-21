package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.EnterpriseVO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/10/18:03
 * @Description:
 */
public interface EnterpriseService {
    public ResponseDTO<EnterpriseVO> getInfo();
    public ResponseDTO checkUserName(String username);
}

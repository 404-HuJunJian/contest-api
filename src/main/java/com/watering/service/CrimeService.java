package com.watering.service;

import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.crime.CrimeAddDTO;
import com.watering.domain.VO.CrimeVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/13/17:29
 * @Description:
 */
public interface CrimeService {

    public ResponseDTO<List<CrimeVO>> findAllCrime(Integer empid);

    public ResponseDTO<List<CrimeVO>> findCurCrime(Integer carid);

    public ResponseDTO crimeInput(CrimeAddDTO crime);

}

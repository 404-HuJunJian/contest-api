package com.watering.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.watering.constant.FileTypeEnum;
import com.watering.dao.*;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.RoleDTO;
import com.watering.domain.DTO.career.CareerAddDTO;
import com.watering.domain.DTO.employee.EmployeeAddDTO;
import com.watering.domain.DTO.employee.EmployeeUpdateBaseDTO;
import com.watering.domain.DTO.employee.EmployeeUpdateCareerDTO;
import com.watering.domain.DTO.search.KeyWord;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.DTO.search.SearchFilter;
import com.watering.domain.VO.CareerVO;
import com.watering.domain.VO.EmployeeSimpleVO;
import com.watering.domain.VO.EmployeeVO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.entity.*;
import com.watering.service.EmployeeService;
import com.watering.service.FileUploadService;
import com.watering.service.OccupationService;
import com.watering.utils.GetCurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/10/15:58
 * @Description:
 */
@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;
    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;
    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;
    @Autowired
    private HrEntityMapper hrEntityMapper;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CareerEntityMapper careerEntityMapper;
    @Autowired
    private AttendanceEntityMapper attendanceEntityMapper;
    @Autowired
    private PerformanceEntityMapper performanceEntityMapper;
    @Autowired
    private OccupationService occupationService;

    @Override
    public ResponseDTO<EmployeeVO> findEmployeeBasicInfo(Integer empid) {
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(empid);
        String enterpriseName = null;
        String departmentName = null;
        HrVO hrVO = null;
        if(employeeEntity.isHired()){
            hrVO = new HrVO();
            enterpriseName = enterpriseEntityMapper.selectByPrimaryKey(employeeEntity.getEntid()).getName();
            departmentName = departmentEntityMapper.selectByPrimaryKey(employeeEntity.getDepid()).getName();
            HrEntity hrEntity =hrEntityMapper.selectByPrimaryKey(employeeEntity.getHrid());
            BeanUtils.copyProperties(hrEntity,hrVO);
            hrVO.setEnterprise(enterpriseName);
        }
        EmployeeVO employeeVO =new EmployeeVO();
        BeanUtils.copyProperties(employeeEntity,employeeVO);
        employeeVO.setEnterprise(enterpriseName);
        employeeVO.setDepartment(departmentName);
        employeeVO.setHr(hrVO);
        return ResponseDTO.succData(employeeVO);
    }

    //修改档案注意图片修改
    @Override
    public ResponseDTO updateEmployeeBasicInfo(EmployeeUpdateBaseDTO employeeUpdateBaseDTO) throws FileNotFoundException {
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(employeeUpdateBaseDTO.getId());
        String newPhoto = employeeUpdateBaseDTO.getPhoto();
        String newResume = employeeUpdateBaseDTO.getResume();
        String oldPhoto = employeeEntity.getPhoto();
        String oldResume = employeeEntity.getResume();
        newPhoto = checkImgUrl(newPhoto,oldPhoto,FileTypeEnum.IMG_PHOTO);
        newResume = checkImgUrl(newResume,oldResume,FileTypeEnum.IMG_RESUME);
        BeanUtils.copyProperties(employeeUpdateBaseDTO,employeeEntity);
        employeeEntity.setPhoto(newPhoto);
        employeeEntity.setResume(newResume);
        employeeEntityMapper.updateByPrimaryKey(employeeEntity);
        return ResponseDTO.succMsg("档案更新成功");
    }

    //新增基本信息档案
    @Override
    public ResponseDTO<Integer> addEmployeeBasicInfo(EmployeeAddDTO employeeAddDTO) throws FileNotFoundException {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        String newPhoto = checkImgUrl(employeeAddDTO.getPhoto(),FileTypeEnum.IMG_PHOTO);
        String newResume = checkImgUrl(employeeAddDTO.getResume(),FileTypeEnum.IMG_RESUME);
        BeanUtils.copyProperties(employeeAddDTO,employeeEntity);
        employeeEntity.setPhoto(newPhoto);
        employeeEntity.setResume(newResume);
        employeeEntityMapper.insert(employeeEntity);
        return ResponseDTO.succData(employeeEntity.getId());
    }

    //录入雇员，修改career经历
    //新增career
    @Override
    @Transactional
    public ResponseDTO employ(CareerAddDTO careerAddDTO) {
        HrEntity hrEntity = (HrEntity) GetCurrentUser.getUser();
        EmployeeUpdateCareerDTO employeeUpdateCareerDTO = careerAddDTO.getEmployee();
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(employeeUpdateCareerDTO.getEmpid());
        //生成经历
        CareerEntity careerEntity = new CareerEntity();
        careerEntity.setCtime(new Date());
        careerEntity.setDepid(employeeUpdateCareerDTO.getDepid());
        careerEntity.setEmpid(employeeUpdateCareerDTO.getEmpid());
        careerEntity.setEntid(hrEntity.getEntid());
        careerEntity.setOccid(careerAddDTO.getOccid());
        careerEntity.setStime(new Date());
        careerEntityMapper.insert(careerEntity);
        employeeEntity.setDepid(employeeUpdateCareerDTO.getDepid());
        employeeEntity.setEntid(hrEntity.getEntid());
        employeeEntity.setHired(true);
        employeeEntity.setInnid(employeeUpdateCareerDTO.getInnid());
        employeeEntity.setHrid(hrEntity.getId());
        employeeEntityMapper.updateByPrimaryKey(employeeEntity);
        occupationService.addCount(careerAddDTO.getOccid());
        return ResponseDTO.succMsg("录用成功");
    }

    //离职
    //简历中删除career信息
    //计算平均分
    //结束一段经历
    //加工龄
    //设置emp_ooc_mapper
    @Override
    @Transactional
    public ResponseDTO quit(Integer empid) {
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(empid);
        CareerEntity careerEntity = careerEntityMapper.selectLastCareerByEmpId(empid);
        //计算平均分
        careerEntity.setAttendance(attendanceEntityMapper.selectAvgByCarid(careerEntity.getId()));
        careerEntity.setPerformance(performanceEntityMapper.selectAvgByCarid(careerEntity.getId()));
        careerEntity.setEtime(new Date());
        //加工龄
        Double originalSeniority = employeeEntity.getSeniority();
        Double seniority = Double.valueOf(careerEntity.getStime().getTime() - careerEntity.getEtime().getTime())/(1000*60*60*24*365L);
        if(null != originalSeniority)
            employeeEntity.setSeniority(originalSeniority+seniority);
        else
            employeeEntity.setSeniority(seniority);
        employeeEntity.setHired(false);
        employeeEntity.setDepid(null);
        employeeEntity.setEntid(null);
        employeeEntity.setInnid(null);
        employeeEntity.setHrid(null);
        //设置emp_ooc_mapper
        List<OccupationEntity> occupationEntities = occupationService.listParentOccupation(careerEntity.getOccid()).getData();
        employeeEntityMapper.insertHistoryOccupation(new Date(),empid,careerEntity.getOccid());
        for (OccupationEntity occupationEntity:occupationEntities){
            employeeEntityMapper.insertHistoryOccupation(new Date(),empid,occupationEntity.getId());
        }
        careerEntityMapper.updateByPrimaryKey(careerEntity);
        employeeEntityMapper.updateByPrimaryKey(employeeEntity);
        return ResponseDTO.succMsg("离职成功");
    }

    //如果关键词是部门，那么就先找到该公司部门的编号
    //过滤器是职业，先找出符合employee
    //必须是本公司的
    @Override
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> innerEmployeeSearch(SearchDTO searchDTO) {
        List<SearchFilter> searchFilters = searchDTO.getFilters();
        KeyWord keyWord = searchDTO.getKey();
        //获得公司id
        Integer entid = null;
        String role = GetCurrentUser.getUserRole();
        Object object = GetCurrentUser.getUser();
        if(role.equals(RoleDTO.Type.ROLE_HR.getType())){
            HrEntity hrEntity = (HrEntity) object;
            entid = hrEntity.getEntid();
        }else if(role.equals(RoleDTO.Type.ROLE_MANAGER.getType())){
            ManagerEntity managerEntity = (ManagerEntity) object;
            entid = managerEntity.getEntid();
        }else{
            EnterpriseEntity enterpriseEntity = (EnterpriseEntity) object;
            entid = enterpriseEntity.getId();
        }
        //如果关键词是部门，那么就先找到该公司部门的编号
        if(keyWord.getType()== KeyWord.Type.DEPARTMENT){
            List<DepartmentEntity> list = departmentEntityMapper.listByEntidLikeName(entid,keyWord.getValue());
            String newKeyWord = "";
            for (DepartmentEntity departmentEntity:list){
                newKeyWord = newKeyWord+departmentEntity.getId()+",";
            }
            newKeyWord = newKeyWord.substring(0,newKeyWord.lastIndexOf(","));
            keyWord.setValue(newKeyWord);
        }
        //处理过滤器们
        modifyFilter(searchFilters);
        //增加公司过滤器
        SearchFilter enterpriseFilter = new SearchFilter();
        enterpriseFilter.setType(SearchFilter.Type.ENTERPRISE);
        enterpriseFilter.setStart(entid);
        searchFilters.add(enterpriseFilter);
        //分页查找
        return pageSearch(searchDTO,false);
    }


    @Override
    public ResponseDTO<PageInfo<EmployeeSimpleVO>> dropEmployeeSearch(SearchDTO searchDTO) {
        List<SearchFilter> searchFilters = searchDTO.getFilters();
        //处理过滤器们
        modifyFilter(searchFilters);
        //新增过滤器
        SearchFilter hiredFilter = new SearchFilter();
        hiredFilter.setType(SearchFilter.Type.HIRED);
        hiredFilter.setStart(0);
        searchFilters.add(hiredFilter);
        //分页处理
        return pageSearch(searchDTO,true);
    }


    /**
     *
     @param oldImgUrl:原来的图片路径
     @param newImgUrl:新的图片路径
     @param type:图片文件类型
     @return :确定下来的图片路径
     */
    private String checkImgUrl(String newImgUrl,String oldImgUrl,FileTypeEnum type) throws FileNotFoundException {
        if(null!=newImgUrl){
            if(fileUploadService.checkFile(newImgUrl, type))
                newImgUrl = type.getUrl() + newImgUrl;
            else
                newImgUrl = oldImgUrl;
        }else{
            newImgUrl = oldImgUrl;
        }
        return newImgUrl;
    }

    private String checkImgUrl(String newImgUrl,FileTypeEnum type) throws FileNotFoundException {
        return checkImgUrl(newImgUrl,null,type);
    }

    //处理过滤器的值，把前端的数据进行分解
    private void trimFilterValue(SearchFilter searchFilter){
        String originalFilterValue = searchFilter.getValue();
        if (originalFilterValue.startsWith("-")){
            searchFilter.setEnd(originalFilterValue.substring(1,originalFilterValue.length()));
        }else if (originalFilterValue.endsWith("-")){
            searchFilter.setStart(originalFilterValue.substring(0,originalFilterValue.lastIndexOf("-")));
        }else {
            searchFilter.setStart(originalFilterValue.substring(0,originalFilterValue.indexOf("-")));
            searchFilter.setEnd(originalFilterValue.substring(originalFilterValue.indexOf("-")+1,originalFilterValue.length()));
        }
    }

    //调整过滤器,对一些特殊数据进行处理
    private void modifyFilter(List<SearchFilter> searchFilters){
        for (SearchFilter searchFilter:searchFilters){
            trimFilterValue(searchFilter);
            if(searchFilter.getType()==SearchFilter.Type.BIRTH){
                Integer start = Integer.valueOf((String) searchFilter.getStart());
                Integer end = Integer.valueOf((String)searchFilter.getEnd());
                System.out.println(end);
                Long perYear = 1000*60*60*24*365L;
                Date startDate =new Date(new Date().getTime()-end*perYear);
                Date endDate = new Date(new Date().getTime()-start*perYear);
                searchFilter.setStart(startDate);
                searchFilter.setEnd(endDate);
            }else if (searchFilter.getType()==SearchFilter.Type.OCCUPATION) {
                String newFilterValue = "";
                Integer occid = Integer.valueOf((String)searchFilter.getStart());
                List<Integer> list = employeeEntityMapper.listEmpidByHistoryOccid(occid);
                for (Integer empid:list){
                    newFilterValue = newFilterValue+empid+",";
                }
                newFilterValue = newFilterValue.substring(0,newFilterValue.lastIndexOf(","));
                searchFilter.setStart(newFilterValue);
            }
        }
    }

    /**
     *
     * @param searchDTO:查找用的DTO
     * @param isDrop:是不是查询离职的档案
     * @return 查找结果
     */
    //分页查找
    private ResponseDTO<PageInfo<EmployeeSimpleVO>> pageSearch(SearchDTO searchDTO,boolean isDrop){
        PageHelper.startPage(searchDTO.getPage(),searchDTO.getPageSize());
        Page<EmployeeEntity> employeeEntities = employeeEntityMapper.listBySearchDTO(searchDTO);
        Page<EmployeeSimpleVO> employeeSimpleVOS = new Page<>();
        employeeSimpleVOS.setTotal(employeeEntities.getTotal());
        for(EmployeeEntity employeeEntity:employeeEntities){
            EmployeeSimpleVO employee;
            if(isDrop){
                employee = new EmployeeSimpleVO();
                BeanUtils.copyProperties(employeeEntity,employee);
            }else {
                EmployeeVO employeeVO = new EmployeeVO();
                BeanUtils.copyProperties(employeeEntity,employeeVO);
                employeeVO.setDepartment(departmentEntityMapper.selectByPrimaryKey(employeeEntity.getDepid()).getName());
                employee = employeeVO;
            }
            employeeSimpleVOS.add(employee);
        }
        PageInfo<EmployeeSimpleVO> pageInfo = new PageInfo<>(employeeSimpleVOS);
        return ResponseDTO.succData(pageInfo);
    }


}

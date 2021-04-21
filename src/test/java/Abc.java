import com.watering.ApiMain8081;
import com.watering.domain.DTO.search.KeyWord;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.DTO.search.SearchFilter;
import com.watering.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/18/22:04
 * @Description:
 */
@SpringBootTest(classes = ApiMain8081.class)
public class Abc {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test(){
        SearchDTO searchDTO = new SearchDTO();
        KeyWord keyWord = new KeyWord();
        keyWord.setType(KeyWord.Type.NAME);
        keyWord.setValue("三");
        searchDTO.setKey(keyWord);
        employeeService.innerEmployeeSearch(searchDTO);
    }


}

package com.watering.domain.DTO.search;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/18:46
 * @Description:
 */
@Data
public class SearchFilter {
    private String value;
    private Object start;
    private Object end;
    private Type type;

    //年龄、性别、工龄、学历、职业、(公司)、(离职)
    public enum Type{
        BIRTH("BIRTH"),GENDER("GENDER"),SENIORITY("SENIORITY"),DEGREE("DEGREE"),OCCUPATION("OCCUPATION"),ENTERPRISE("ENTERPRISE"),HIRED("HIRED");

//        出勤率、业绩、态度、能力、
//        ATTENDANCE("ATTENDANCE"),PERFORMANCE("PERFORMANCE"),ATTITUDE("ATTITUDE"),ABILITY("ABILITY"),

        private String type;

        Type(String type) {
            this.type=type;
        }

        public String getType() {
            return type;
        }
    }

}

package com.watering.domain.DTO.search;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/02/17:07
 * @Description:
 */
@Data
public class KeyWord {
    private String value;
    private Type type;

    public enum Type{
        NAME("NAME"),MAJOR("MAJOR"),DEPARTMENT("DEPARTMENT");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

}

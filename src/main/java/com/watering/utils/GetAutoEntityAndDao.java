package com.watering.utils;

import org.mybatis.generator.api.ShellRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/24/19:33
 * @Description:
 */
public class GetAutoEntityAndDao {

    // 该配置文件放在src\\main\\resources\\该路径下即可
    public static void main(String[] args) {
        args = new String[] { "-configfile", "src\\main\\resources\\mybatis-generator.xml", };
        ShellRunner.main(args);
    }

}

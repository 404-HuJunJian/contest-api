package com.watering.constant;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/21/14:39
 * @Description: 响应码常量类
 */
public class ResponseCodeConst {

    static {
        // 系统功能，从0开始
        ResponseCodeContainer.register(ResponseCodeConst.class, 0, 200);
        ResponseCodeContainer.register(LoginResponseCodeConst.class, 201, 300);
        ResponseCodeContainer.register(FileResponseCodeConst.class,301,400);

    }

    public static final ResponseCodeConst SUCCESS = new ResponseCodeConst(100, "操作成功", true);

    public static final ResponseCodeConst ERROR_PARAM = new ResponseCodeConst(101, "参数异常！");

    public static final ResponseCodeConst SYSTEM_ERROR = new ResponseCodeConst(102, "系统错误");

    public static ResponseCodeConst REQUEST_METHOD_ERROR = new ResponseCodeConst(103, "请求方式错误");

    public static final ResponseCodeConst NOT_EXISTS = new ResponseCodeConst(104, "数据不存在");

//    public static final ResponseCodeConst DEVELOPMENT = new ResponseCodeConst(105, "此功能正在开发中");

//    public static final ResponseCodeConst NOT_EXISTS = new ResponseCodeConst(106, "数据不存在");

    public static ResponseCodeConst JSON_FORMAT_ERROR = new ResponseCodeConst(105, "JSON格式或请求参数错误");

    public static ResponseCodeConst PARAM_NOT_NULL = new ResponseCodeConst(106,"参数不能为空");

    protected int code;

    protected String msg;

    protected boolean success;

    public ResponseCodeConst() {
    }

    protected ResponseCodeConst(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
        ResponseCodeContainer.put(this);
    }

    protected ResponseCodeConst(int code, String msg, boolean success) {
        super();
        this.code = code;
        this.msg = msg;
        this.success = success;
        ResponseCodeContainer.put(this);
    }

    protected ResponseCodeConst(int code) {
        super();
        this.code = code;
        ResponseCodeContainer.put(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    public static void init() {
//        log.info("ResponseCodeConst init....");
//    }

    // =======================分割=======================



    /**
     * 内部类，用于检测code范围
     *
     * @author Anders
     */
    private static class ResponseCodeContainer {

        private static final Map<Integer, ResponseCodeConst> RESPONSE_CODE_MAP = new HashMap<>();

        private static final Map<Class<? extends ResponseCodeConst>, int[]> RESPONSE_CODE_RANGE_MAP = new HashMap<>();

        /**
         * id的范围：[start, end]左闭右闭
         *
         * @param clazz
         * @param start
         * @param end
         */
        //注册并限定范围
        private static void register(Class<? extends ResponseCodeConst> clazz, int start, int end) {
            if (start > end) {
                throw new IllegalArgumentException("<ResponseDTO> start > end!");
            }

            if (RESPONSE_CODE_RANGE_MAP.containsKey(clazz)) {
                throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s already exist!", clazz.getSimpleName()));
            }
            RESPONSE_CODE_RANGE_MAP.forEach((k, v) -> {
                if ((start >= v[0] && start <= v[1]) || (end >= v[0] && end <= v[1])) {
                    throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s 's id range[%d,%d] has " + "intersection with " + "class:%s", clazz.getSimpleName(), start, end,
                            k.getSimpleName()));
                }
            });

            RESPONSE_CODE_RANGE_MAP.put(clazz, new int[]{start, end});

            // 提前初始化static变量，进行范围检测
            Field[] fields = clazz.getFields();
            if (fields.length != 0) {
                try {
                    fields[0].get(clazz);
                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    log.error("", e);
                }
            }
        }

        //常量类第一次初始化的时候会调用该方法(进入容器判断)
        public static void put(ResponseCodeConst codeConst) {
            int[] idRange = RESPONSE_CODE_RANGE_MAP.get(codeConst.getClass());
            //该类没有在容器中注册
            if (idRange == null) {
                throw new IllegalArgumentException(String.format("<ResponseDTO> Class:%s has not been registered!", codeConst.getClass().getSimpleName()));
            }
            int code = codeConst.code;
            //该状态码不在注册的范围内
            if (code < idRange[0] || code > idRange[1]) {
                throw new IllegalArgumentException(String.format("<ResponseDTO> Id(%d) out of range[%d,%d], " + "class:%s", code, idRange[0], idRange[1], codeConst.getClass().getSimpleName()));
            }
            //该状态码已使用
            if (RESPONSE_CODE_MAP.keySet().contains(code)) {
//                log.error(String.format("<ResponseDTO> Id(%d) out of range[%d,%d], " + "class:%s  code is repeat!", code, idRange[0], idRange[1], codeConst.getClass().getSimpleName()));
                System.out.println("重复了");
                System.exit(0);
            }
            //添加该状态码进入容器
            RESPONSE_CODE_MAP.put(code, codeConst);
        }

    }


}

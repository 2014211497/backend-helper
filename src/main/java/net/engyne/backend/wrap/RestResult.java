package net.engyne.backend.wrap;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * RestResult 用于封装返回给HTTP请求的JSON字符串结果
 */
public class RestResult {

    public static final Integer CODE_200 = 200; // 成功
    public static final Integer CODE_400 = 400; // 失败
    public static final Integer CODE_401 = 401; // 未登录
    public static final Integer CODE_403 = 403; // 无权限
    public static final Integer CODE_500 = 500; // 服务器内部错误

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";
    public static final String STATUS_ERROR = "error";

    private Integer code;
    private String status;
    private Object data;

    public RestResult(Integer code, String status, Object data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson() {
        return JSON.toJSONString(this, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue);
    }

    public static String create(ExecResult r) {
        return r.isOk() ? RestResult.success(r.getData()) : RestResult.fail(r.getData());
    }

    public static String success() {
        return RestResult.success("");
    }

    /**
     * 请求成功
     */
    public static String success(Object data) {
        return new RestResult(CODE_200, STATUS_SUCCESS, data).toJson();
    }

    /**
     * 未登录
     */
    public static String unauth(Object data) {
        return new RestResult(CODE_401, STATUS_FAIL, data).toJson();
    }

    /**
     * 请求失败
     */
    public static String fail(Object data) {
        return new RestResult(CODE_400, STATUS_FAIL, data).toJson();
    }

    /**
     * 无权限
     */
    public static String deny(Object data) {
        return new RestResult(CODE_403, STATUS_FAIL, data).toJson();
    }

    /**
     * 服务器内部错误
     */
    public static String error(Object data) {
        return new RestResult(CODE_500, STATUS_ERROR, data).toJson();
    }

}

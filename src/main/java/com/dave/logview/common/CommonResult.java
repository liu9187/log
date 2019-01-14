package com.dave.logview.common;

/**
 * Created by Dave on 2018/6/16.
 * 通用返回
 */
public class CommonResult {
    /**
     * 响应码常量类
     *
     */
    public static class Code {
        /**
         * 请求成功
         */
        public static final int SUCCESS = 200;
        /**
         * 请求失败
         */
        public static final int FAILURE = 1001;
        /**
         * 已存在
         */
        public static final int EXISTS = 1003;
        /**
         * 服务器端异常
         */
        public static final int SERVER_ERROR = 5000;
    }

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private CommonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static CommonResult success() {
        return new CommonResult(CommonResult.Code.SUCCESS, "请求成功", null);
    }

    public static CommonResult success(String msg) {
        return new CommonResult(CommonResult.Code.SUCCESS, msg, null);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(CommonResult.Code.SUCCESS, null, data);
    }

    public static CommonResult error() {
        return new CommonResult(CommonResult.Code.SERVER_ERROR, null, null);
    }

    public static CommonResult error(String msg) {
        return new CommonResult(CommonResult.Code.SERVER_ERROR, msg, null);
    }
    public static CommonResult error(Object data) {
        return new CommonResult(CommonResult.Code.SERVER_ERROR, "执行失败", data);
    }

    public static CommonResult build(Integer code) {
        return new CommonResult(code, null, null);
    }

    public static CommonResult build(Integer code, String msg, Object data) {
        return new CommonResult(code, msg, data);
    }

    public static CommonResult build(Integer code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public Integer getcode() {
        return code;
    }

    public void setcode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

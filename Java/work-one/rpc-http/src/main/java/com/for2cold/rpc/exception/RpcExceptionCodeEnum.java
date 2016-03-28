package com.for2cold.rpc.exception;

/**
 * Created by jasme on 16/3/16.
 */
public enum RpcExceptionCodeEnum {

    DATA_PARSER_ERROR("DATA_PARSER_ERROR", "数据解析错误"),
    NO_BEAN_FOUND("NO_BEAN_FOUND", "未找到Bean"),
    INVOKE_REQUEST_ERROR("INVOKE_REQUEST_ERROR", "RPC调用错误");

    private String code;

    private String msg;

    RpcExceptionCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

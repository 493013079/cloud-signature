package com.peony.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> {

    public static final RestResult<Object> SUCCESS = success().build();

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 编码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public static <T> RestResult.RestResultBuilder<T> success() {
        return success("请求成功");
    }

    public static <T> RestResult.RestResultBuilder<T> success(String message) {
        return RestResult.<T>builder()
                .success(true)
                .message(message);
    }

    public static <T> RestResult.RestResultBuilder<T> failure(String message) {
        return RestResult.<T>builder()
                .success(false)
                .message(message);
    }

    public static <T> RestResult<T> failure(RestErrorCode code) {
        return RestResult.<T>failure(code.getMessage()).code(code.name()).build();
    }

    public static <T> RestResult<T> successData(T data) {
        return RestResult.<T>success().data(data).build();
    }

}
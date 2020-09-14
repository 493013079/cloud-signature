package com.peony.common.web;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestException extends RuntimeException {

    private HttpStatus httpStatus;

    private RestResult restResult;

    public RestException(HttpStatus httpStatus, RestResult restResult) {
        super(restResult.getMessage());
        this.httpStatus = httpStatus;
        this.restResult = restResult;
    }

    public RestException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.restResult = RestResult.failure(message).code(httpStatus.name()).build();
    }

    public RestException(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.restResult = RestResult.failure(message).code(code).build();
    }

    public RestException(RestErrorCode restErrorCode) {
        this(restErrorCode.getHttpStatus(), restErrorCode.getMessage());
    }

    public static final RestException UNAUTHORIZED = new RestException(HttpStatus.UNAUTHORIZED, "没有登录");

    public static final RestException FORBIDDEN = new RestException(HttpStatus.FORBIDDEN, "没有权限");

    public static final RestException NOT_FOUND = new RestException(HttpStatus.NOT_FOUND, "资源不存在");

    public static final RestException SERVICE_UNAVAILABLE = new RestException(HttpStatus.SERVICE_UNAVAILABLE, "服务不可用");

}
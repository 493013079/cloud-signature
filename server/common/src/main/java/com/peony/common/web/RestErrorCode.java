package com.peony.common.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author hk
 * @date 2019/11/22
 */
@Getter
@AllArgsConstructor
public enum RestErrorCode {

    //E0xxxxx系统相关
    OPERATE_FAIL(HttpStatus.SERVICE_UNAVAILABLE, "操作失败"),
    FORMAT_ERROR(HttpStatus.BAD_REQUEST, "输入格式非法"),
    DATA_ERROR(HttpStatus.BAD_REQUEST, "输入数值不合理"),
    SYSTEM_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "系统错误"),
    WRONG_PARAM(HttpStatus.BAD_REQUEST, "参数错误或为空"),
    CONNECT_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "与第三方服务器通信异常"),
    AUTH_FAILED(HttpStatus.UNAUTHORIZED, "认证失败"),
    AUTH_NOT_ENOUGH(HttpStatus.FORBIDDEN, "权限不足"),
    NOT_EXIST(HttpStatus.NOT_FOUND, "不存在"),
    HAS_EXIST(HttpStatus.BAD_REQUEST, "已存在"),
    OWN_RESOURCE(HttpStatus.FORBIDDEN, "拥有资源，无法删除"),
    NAME_DUPLICATED(HttpStatus.BAD_REQUEST, "命名重复"),
    PARAM_TIME_ERROR(HttpStatus.BAD_REQUEST, "参数时间范围错误"),
    PARAM_SCALE_ERROR(HttpStatus.BAD_REQUEST, "参数数值范围错误"),
    REQUEST_ADDRESS_ERROR(HttpStatus.BAD_REQUEST, "请求地址或方法错误"),
    METHOD_ARGUMENT_MISMATCH(HttpStatus.BAD_REQUEST, "请求参数格式错误, 或者请求参数非法"),
    METHOD_ARGUMENT_MISSING(HttpStatus.BAD_REQUEST, "页面请求参数缺失"),
    REMOTE_ACCESS_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "远程调用服务异常"),

    //用户相关
    USER_NOT_EXIST(HttpStatus.NOT_FOUND, "用户不存在"),
    USER_HAS_EXIST(HttpStatus.BAD_REQUEST, "用户已存在"),
    NAME_DUPLICATION(HttpStatus.BAD_REQUEST, "名字重复"),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "用户未登录"),
    REPEATED_LOGIN(HttpStatus.BAD_REQUEST, "重复登录"),
    USER_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "用户账号或密码错误"),
    LOGOUT_FAIL(HttpStatus.SERVICE_UNAVAILABLE, "退出失败"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "登录失效"),
    ACCOUNT_EXIST(HttpStatus.BAD_REQUEST, "账号已存在"),
    TOKEN_IS_EXPIRE(HttpStatus.UNAUTHORIZED, "token已过期"),
    TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "token错误"),

    // permission相关
    PERMISSION_NOT_EXIST(HttpStatus.NOT_FOUND, "权限不存在"),
    ROLE_NOT_EXIST(HttpStatus.NOT_FOUND, "角色不存在"),
    USER_HAS_NO_PERMISSIONS(HttpStatus.FORBIDDEN, "用户没有任何权限, 无法登陆"),
    ROLE_CAN_NOT_DELETE(HttpStatus.FORBIDDEN, "该角色无法删除,存在关联用户"),

    // message相关
    MESSAGE_NOT_EXIST(HttpStatus.NOT_FOUND, "消息记录不存在"),

    // organization相关
    ORGANIZATION_NOT_EXIST(HttpStatus.NOT_FOUND, "组织不存在"),
    ORGANIZATION_HAS_CHILD(HttpStatus.FORBIDDEN, "组织无法删除，存在关联子企业"),
    ORGANIZATION_ROOT(HttpStatus.FORBIDDEN, "根组织无法删除"),

    FILE_EMPTY(HttpStatus.BAD_REQUEST, "上传文件为空"),
    FILE_TYPE_ERROR(HttpStatus.BAD_REQUEST, "上传文件为空"),
    FILE_NOT_EXIST(HttpStatus.BAD_REQUEST, "文件不存在"),


    TICKETS_NOT_EXIST(HttpStatus.NOT_FOUND, "票据不存在"),
    SIGNATURE_NOT_EXIST(HttpStatus.NOT_FOUND, "签名不存在"),

    ;

    private HttpStatus httpStatus;

    private String message;

}
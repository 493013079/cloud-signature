package com.peony.common.web.advice;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.peony.common.web.RestException;
import com.peony.common.web.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;

/**
 * @author 辛毅
 * @date 2019/11/23
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 一般异常处理
     *
     * @param request       请求
     * @param restException 一般异常
     * @return 响应
     */
    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResult> restExceptionHandler(HttpServletRequest request, RestException restException) {
        log.warn(request.getMethod() + " " + request.getServletPath() + " " + restException.getMessage());
        return ResponseEntity.status(restException.getHttpStatus()).body(restException.getRestResult());
    }

    /**
     * 表单字段验证失败异常处理
     *
     * @param request                         请求
     * @param methodArgumentNotValidException 验证失败异常
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResult> methodArgumentNotValidExceptionHandler(HttpServletRequest request,
                                                                             MethodArgumentNotValidException methodArgumentNotValidException) {
        log.warn(request.getMethod() + " " + request.getServletPath() + " " + methodArgumentNotValidException.getMessage());
        List<FieldError> allErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String message = allErrors.stream().findFirst().map(FieldError::getDefaultMessage).orElse("参数错误");
        Map<String, List<String>> data = allErrors.stream()
                .collect(Collectors.toMap(FieldError::getField,
                        fieldError -> Lists.newArrayList(fieldError.getDefaultMessage()),
                        (messages1, messages2) -> Lists.newArrayList(Iterables.concat(messages1, messages2))));
        RestResult restResult = RestResult.failure(message)
                .code(HttpStatus.BAD_REQUEST.name())
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResult);
    }

    /**
     * 请求字段不合法异常处理
     *
     * @param request                        请求
     * @param servletRequestBindingException 请求字段不合法异常
     * @return 响应
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<RestResult> servletRequestBindingExceptionHandler(HttpServletRequest request,
                                                                            ServletRequestBindingException servletRequestBindingException) {
        log.warn(request.getMethod() + " " + request.getServletPath() + " " + servletRequestBindingException.getMessage());
        RestResult restResult = RestResult.failure(servletRequestBindingException.getMessage())
                .code(HttpStatus.BAD_REQUEST.name())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResult);
    }

    /**
     * 404异常处理
     *
     * @param request                 请求
     * @param noHandlerFoundException 404异常
     * @return 响应
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestResult> noHandlerFoundExceptionHandler(HttpServletRequest request,
                                                                     NoHandlerFoundException noHandlerFoundException) {
        log.warn(request.getMethod() + " " + request.getServletPath() + " " + noHandlerFoundException.getMessage());
        RestResult restResult = RestResult.failure(noHandlerFoundException.getMessage())
                .code(HttpStatus.NOT_FOUND.name())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResult);
    }

    /**
     * 文件大小超出限制异常
     *
     * @param request                        请求
     * @param fileSizeLimitExceededException 文件大小超出限制异常
     * @return 响应
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<RestResult> fileSizeLimitExceededExceptionHandler(HttpServletRequest request,
                                                                            FileSizeLimitExceededException fileSizeLimitExceededException) {
        log.warn(request.getMethod() + " " + request.getServletPath() + " " + fileSizeLimitExceededException.getMessage());
        long permittedSize = fileSizeLimitExceededException.getPermittedSize();
        String permittedSizeFormat = DataSize.ofBytes(permittedSize).toMegabytes() + "MB";
        RestResult restResult = RestResult.failure("文件大小超出限制 " + permittedSizeFormat)
                .code(HttpStatus.BAD_REQUEST.name())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResult);
    }

    /**
     * 嵌套类型异常处理
     *
     * @param request                请求
     * @param nestedRuntimeException 异常
     * @return 响应
     */
    @ExceptionHandler(NestedRuntimeException.class)
    public ResponseEntity<RestResult> nestedRuntimeExceptionHandler(HttpServletRequest request,
                                                                    NestedRuntimeException nestedRuntimeException) {
        Throwable rootCause = nestedRuntimeException.getRootCause();
        if (rootCause instanceof RestException) {
            return restExceptionHandler(request, (RestException) rootCause);
        } else if (rootCause instanceof FileSizeLimitExceededException) {
            return fileSizeLimitExceededExceptionHandler(request, (FileSizeLimitExceededException) rootCause);
        } else {
            return defaultExceptionHandler(request, nestedRuntimeException);
        }
    }

    /**
     * 未知异常处理
     *
     * @param request   请求
     * @param exception 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResult> defaultExceptionHandler(HttpServletRequest request, Exception exception) {
        String message = exception.getMessage();
        log.error(message, exception);
        RestResult restResult = RestResult.failure("服务器错误: " + message)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResult);
    }

}
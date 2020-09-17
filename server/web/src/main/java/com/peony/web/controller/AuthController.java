package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.AuditLog;
import com.peony.common.entity.Permission;
import com.peony.common.entity.User;
import com.peony.common.entity.field.AuditLogType;
import com.peony.common.entity.filter.UserFilter;
import com.peony.common.service.AuditLogService;
import com.peony.common.service.UserService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import com.peony.common.web.RestResult;
import com.peony.common.web.helper.UserHelper;
import com.peony.web.aop.Authenticated;
import com.peony.web.entity.AuthVO;
import com.peony.web.entity.form.user.LoginFormVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hk
 * @date 2019/11/21
 */
@Api(value = "认证接口", tags = "认证接口")
@ApiSort(5)
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final UserHelper userHelper;

    private final UserService userService;

    private final AuditLogService auditLogService;

    public AuthController(UserHelper userHelper,
                          UserService userService,
                          AuditLogService auditLogService) {
        this.userHelper = userHelper;
        this.userService = userService;
        this.auditLogService = auditLogService;
    }

    @ApiOperationSort(20)
    @ApiOperation("当前登陆用户信息")
    @GetMapping
    @Authenticated(false)
    public RestResult<AuthVO> info() {
        User currentUser = authHelper.checkUser();
        AuthVO authVO = ConvertUtils.convert(currentUser, AuthVO.class);

        List<Permission> permissions = userHelper.getPermissions(currentUser);
        List<String> permissionKeys = permissions.stream().map(Permission::getKey).collect(Collectors.toList());
        List<String> permissionNames = permissions.stream().map(Permission::getName).collect(Collectors.toList());
        authVO.setPermissionKeys(permissionKeys);
        authVO.setPermissionNames(permissionNames);

        return RestResult.successData(authVO);
    }

    @ApiOperationSort(10)
    @ApiOperation("用户登陆")
    @PostMapping
    @Authenticated(false)
    public RestResult<AuthVO> login(@RequestBody @Valid LoginFormVO loginFormVO) {
        UserFilter userFilter = UserFilter.builder()
                .account(loginFormVO.getAccount())
                .build();
        User user = userService.findOne(userFilter);
        Optional<User> optionalUser = Optional.ofNullable(user);

        // 校验账号
        if (!optionalUser.isPresent()) {
            log.warn("登录账号：{}不存在", loginFormVO.getAccount());
            throw new RestException(RestErrorCode.USER_PASSWORD_ERROR);
        }

        // 校验密码
        if (!user.getPassword().equals(userHelper.encodePassword(loginFormVO.getPassword()))) {
            log.warn("密码错误：{}", loginFormVO.getPassword());
            throw new RestException(RestErrorCode.USER_PASSWORD_ERROR);
        }

        // 保存用户信息到session
        String token = authHelper.setCurrentUser(user);
        log.info("当前登录用户:{}", user.getAccount());
        AuthVO authVO = ConvertUtils.convert(user, AuthVO.class);
        authVO.setToken(token);

        AuditLog auditLog = AuditLog.builder()
                .type(AuditLogType.LOGIN)
                .userId(user.getId())
                .createdDate(ZonedDateTime.now())
                .build();
        auditLogService.save(auditLog);
        return RestResult.successData(authVO);
    }

    @ApiOperationSort(30)
    @ApiOperation("登出")
    @DeleteMapping
    @Authenticated(false)
    public RestResult logout() {
        Optional<User> optionalCurrentUser = authHelper.currentUser();
        if (!optionalCurrentUser.isPresent()) {
            return RestResult.SUCCESS;
        }

        User currentUser = optionalCurrentUser.get();
        authHelper.setCurrentUser(null);

        AuditLog auditLog = AuditLog.builder()
                .type(AuditLogType.LOGOUT)
                .userId(currentUser.getId())
                .createdDate(ZonedDateTime.now())
                .build();
        auditLogService.save(auditLog);
        return RestResult.SUCCESS;
    }

}

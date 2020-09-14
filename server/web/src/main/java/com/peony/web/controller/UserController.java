package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Organization;
import com.peony.common.entity.User;
import com.peony.common.entity.field.UserType;
import com.peony.common.entity.filter.UserFilter;
import com.peony.common.service.UserService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import com.peony.common.web.RestResult;
import com.peony.common.web.helper.UserHelper;
import com.peony.web.aop.RequirePermissions;
import com.peony.web.entity.UserVO;
import com.peony.web.entity.form.user.UserAddFormVO;
import com.peony.web.entity.form.user.UserPasswordUpdateFormVO;
import com.peony.web.entity.form.user.UserUpdateFormVO;
import com.peony.web.entity.option.OptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.peony.common.entity.field.PermissionKey.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author hk
 * @date 2019/10/24
 */
@Api(value = "用户接口", tags = "用户接口")
@ApiSort(10)
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    private final UserHelper userHelper;

    public UserController(UserService userService, UserHelper userHelper) {
        this.userService = userService;
        this.userHelper = userHelper;
    }

    @ApiOperationSort(10)
    @ApiOperation("用户选择器选项")
    @ApiImplicitParam(name = "keyword", value = "关键字模糊匹配, 用户姓名")
    @GetMapping("/options")
    public RestResult<List<OptionVO>> getOptions(@RequestParam(required = false) String keyword) {
        String path = authHelper.checkUser().getOrganizationPath();
        UserFilter userFilter = UserFilter.builder()
                .keyword(keyword)
                .organizationPath(path)
                .build();
        List<User> users = userService.findAll(userFilter);
        List<OptionVO> optionVOS = OptionVO.buildList(users, User::getName, User::getId);
        return RestResult.successData(optionVOS);
    }

    @ApiOperationSort(20)
    @ApiOperation("条件查找用户分页结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号, 模糊查询"),
            @ApiImplicitParam(name = "name", value = "名字, 模糊查询"),
            @ApiImplicitParam(name = "organizationId", value = "组织id"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码, 模糊查询"),
            @ApiImplicitParam(name = "page", value = "页数，从 0 开始", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createdDate,desc", paramType = "query", allowMultiple = true)
    })
    @GetMapping
    public RestResult<Page<UserVO>> find(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "account", required = false) String account,
                                         @RequestParam(value = "organizationId", required = false) Organization organization,
                                         @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                         @ApiIgnore @PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable) {
        String currentOrganizationPath = authHelper.checkUser().getOrganizationPath();
        Integer organizationId = Optional.ofNullable(organization).map(Organization::getId).orElse(null);
        UserFilter userFilter = UserFilter.builder()
                .keyword(keyword)
                .account(account)
                .organizationId(organizationId)
                .organizationPath(currentOrganizationPath)
                .phoneNumber(phoneNumber)
                .build();
        Page<User> userPage = userService.findAll(userFilter, pageable);

        return RestResult.successData(ConvertUtils.convert(userPage, UserVO.class));
    }

    @ApiOperationSort(30)
    @ApiOperation("添加用户")
    @PostMapping
    @RequirePermissions(SYSTEM_USER_ADD)
    public RestResult add(@RequestBody @Valid UserAddFormVO userAddFormVO) {
        checkUserAccount(userAddFormVO.getAccount());

        User user = User.builder()
                .name(userAddFormVO.getName())
                .account(userAddFormVO.getAccount())
                .password(userHelper.encodePassword(userAddFormVO.getPassword()))
                .email(userAddFormVO.getEmail())
                .organizationId(userAddFormVO.getOrganizationId())
                .roleId(userAddFormVO.getRoleId())
                .phoneNumber(userAddFormVO.getPhoneNumber())
                .type(UserType.CUSTOM)
                .build();

        userService.save(user);
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(40)
    @ApiOperation("更新用户")
    @PutMapping("/{id}")
    @RequirePermissions(SYSTEM_USER_UPDATE)
    public RestResult<UserVO> update(@PathVariable("id") User user,
                                     @RequestBody @Valid UserUpdateFormVO userUpdateFormVO) {

        user.setName(userUpdateFormVO.getName());
        user.setRoleId(userUpdateFormVO.getRoleId());
        user.setOrganizationId(userUpdateFormVO.getOrganizationId());
        user.setPhoneNumber(userUpdateFormVO.getPhoneNumber());
        user.setEmail(userUpdateFormVO.getEmail());

        User savedUser = userService.save(user);

        // 不返回用户密码
        savedUser.setPassword(null);
        return RestResult.successData(ConvertUtils.convert(savedUser, UserVO.class));
    }

    @ApiOperationSort(50)
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "organizationPath", required = true)
    @DeleteMapping("/{id}")
    @RequirePermissions(SYSTEM_USER_DELETE)
    public RestResult delete(@PathVariable("id") User user) {
        User currentUser = authHelper.checkUser();

        // 不能删除自己
        Integer userId = user.getId();
        if (currentUser.getId().equals(userId)) {
            throw new RuntimeException();
        }

        userService.deleteById(user.getId());
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(60)
    @ApiOperation("修改密码")
    @PatchMapping("/{id}/password")
    public RestResult<UserVO> updatePassword(@PathVariable("id") User user,
                                             @Valid @RequestBody UserPasswordUpdateFormVO formVO) {
        user.setPassword(userHelper.encodePassword(formVO.getPassword()));
        User savedUser = userService.save(user);

        // 不返回用户密码
        savedUser.setPassword(null);
        return RestResult.successData(ConvertUtils.convert(savedUser, UserVO.class));
    }

    @ApiOperationSort(70)
    @ApiOperation("获取单个用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "organizationPath", required = true)
    @GetMapping("/{id}")
    public RestResult findOne(@PathVariable("id") User user) {
        return RestResult.successData(ConvertUtils.convert(user, UserVO.class));
    }

    private void checkUserAccount(String account) {
        UserFilter userFilter = UserFilter.builder()
                .account(account)
                .build();
        Optional<User> optionalUser = userService.findOne(userFilter);

        if (optionalUser.isPresent()) {
            log.info("用户账号重复：{}", account);
            throw new RestException(RestErrorCode.USER_HAS_EXIST);
        }
    }

}

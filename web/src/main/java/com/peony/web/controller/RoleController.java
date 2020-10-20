package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Role;
import com.peony.common.entity.filter.RoleFilter;
import com.peony.common.entity.filter.UserFilter;
import com.peony.common.service.RoleService;
import com.peony.common.service.UserService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import com.peony.common.web.RestResult;
import com.peony.web.aop.RequirePermissions;
import com.peony.web.entity.RoleVO;
import com.peony.web.entity.form.role.RoleAddFormVO;
import com.peony.web.entity.form.role.RoleUpdateFormVO;
import com.peony.web.entity.option.OptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

import static com.peony.common.entity.field.PermissionKey.*;

/**
 * @author hk
 * @date 2019/10/25
 */
@Api(value = "角色接口", tags = "角色接口")
@ApiSort(20)
@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController {

    private final RoleService roleService;

    private final UserService userService;

    public RoleController(RoleService roleService,
                          UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @ApiOperationSort(10)
    @ApiOperation("角色选择器选项")
    @ApiImplicitParam(name = "keyword", value = "关键字模糊匹配, 角色名称")
    @GetMapping("/options")
    public RestResult<List<OptionVO>> getOptions(@RequestParam(required = false) String keyword) {
        RoleFilter roleFilter = RoleFilter.builder()
                .keyword(keyword)
                .build();
        List<Role> roles = roleService.findAll(roleFilter);
        List<OptionVO> optionVOS = OptionVO.buildList(roles, Role::getName, Role::getId);
        return RestResult.successData(optionVOS);
    }

    @ApiOperationSort(20)
    @ApiOperation("条件查找角色分页结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名字, 模糊匹配"),
            @ApiImplicitParam(name = "create", value = "角色的创建者, 模糊匹配"),
            @ApiImplicitParam(name = "page", value = "页数，从 0 开始", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createdDate,desc", paramType = "query", allowMultiple = true)
    })
    @GetMapping
    public RestResult<Page<RoleVO>> find(@RequestParam(value = "keyword", required = false) String keyword,
                                         @ApiIgnore @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        RoleFilter roleFilter = RoleFilter.builder()
                .keyword(keyword)
                .build();
        Page<Role> rolePage = roleService.findAll(roleFilter, pageable);
        Page<RoleVO> roleVOPage = ConvertUtils.convert(rolePage, RoleVO.class);
        return RestResult.successData(roleVOPage);
    }

    @ApiOperationSort(30)
    @ApiOperation("添加角色")
    @PostMapping
    @RequirePermissions(SYSTEM_ROLE_ADD)
    public RestResult add(@RequestBody @Valid RoleAddFormVO roleAddFormVO) {
        Role role = Role.builder()
                .name(roleAddFormVO.getName())
                .description(roleAddFormVO.getDescription())
                .permissionIds(roleAddFormVO.getPermissionIds())
                .build();
        roleService.save(role);
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(40)
    @ApiOperation("修改角色")
    @PutMapping("/{id}")
    @RequirePermissions(SYSTEM_ROLE_UPDATE)
    public RestResult update(@PathVariable("id") Role role,
                             @RequestBody @Valid RoleUpdateFormVO roleUpdateFormVO) {
        role.setName(roleUpdateFormVO.getName());
        role.setDescription(roleUpdateFormVO.getDescription());
        role.setPermissionIds(roleUpdateFormVO.getPermissionIds());
        roleService.save(role);
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(50)
    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    @RequirePermissions(SYSTEM_ROLE_DELETE)
    public RestResult delete(@PathVariable("id") Role role) {
        UserFilter userFilter = UserFilter.builder()
                .roleId(role.getId())
                .build();
        long userCount = userService.count(userFilter);
        if (userCount > 0) {
            throw new RestException(RestErrorCode.ROLE_CAN_NOT_DELETE);
        }
        roleService.deleteById(role.getId());
        return RestResult.SUCCESS;
    }

}

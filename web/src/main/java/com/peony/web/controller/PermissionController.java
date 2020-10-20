package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Permission;
import com.peony.common.service.PermissionService;
import com.peony.common.web.RestResult;
import com.peony.web.entity.option.TreeOptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hk
 * @date 2019/10/25
 */
@Api(value = "权限接口", tags = "权限接口")
@ApiSort(30)
@Slf4j
@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperationSort(10)
    @ApiOperation("权限列表树")
    @GetMapping("/options")
    public RestResult<List<TreeOptionVO>> tree() {
        List<Permission> permissions = permissionService.findAll();
        List<TreeOptionVO> tree = TreeOptionVO.buildTree(permissions,
                Permission::getName,
                Permission::getId,
                Permission::getParentId);
        return RestResult.successData(tree);
    }

}

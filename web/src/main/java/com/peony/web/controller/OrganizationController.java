package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Organization;
import com.peony.common.entity.field.OrganizationType;
import com.peony.common.entity.filter.OrganizationFilter;
import com.peony.common.service.OrganizationService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import com.peony.common.web.RestResult;
import com.peony.web.aop.RequirePermissions;
import com.peony.web.entity.OrganizationVO;
import com.peony.web.entity.form.organization.OrganizationAddFormVO;
import com.peony.web.entity.form.organization.OrganizationUpdateFormVO;
import com.peony.web.entity.option.TreeOptionVO;
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

import static com.peony.common.entity.field.PermissionKey.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author hk
 * @date 2019/10/25
 */
@Api(value = "组织接口", tags = "组织接口")
@ApiSort(40)
@Slf4j
@RestController
@RequestMapping("/organizations")
public class OrganizationController extends BaseController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperationSort(10)
    @ApiOperation("组织树选择器选项")
    @ApiImplicitParam(name = "keyword", value = "关键字模糊匹配, 单位名称", paramType = "query")
    @GetMapping("/options")
    public RestResult<List<TreeOptionVO>> getOptions(@RequestParam(required = false) String keyword) {
        String path = authHelper.checkUser().getOrganizationPath();
        OrganizationFilter organizationFilter = OrganizationFilter.builder()
                .keyword(keyword)
                .path(path)
                .build();
        List<Organization> organizations = organizationService.findAll(organizationFilter);
        List<TreeOptionVO> tree = TreeOptionVO.buildTree(organizations,
                Organization::getName,
                Organization::getId,
                Organization::getParentId);
        return RestResult.successData(tree);
    }

    @ApiOperationSort(20)
    @ApiOperation("组织列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字模糊匹配, 单位名称"),
            @ApiImplicitParam(name = "page", value = "页数，从 0 开始", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createdDate,desc", paramType = "query", allowMultiple = true)
    })
    @GetMapping
    public RestResult<Page<OrganizationVO>> find(@RequestParam(value = "keyword", required = false) String keyword,
                                                 @ApiIgnore @PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable) {
        String path = authHelper.checkUser().getOrganizationPath();
        OrganizationFilter organizationFilter = OrganizationFilter.builder()
                .keyword(keyword)
                .path(path)
                .build();
        Page<Organization> organizations = organizationService.findAll(organizationFilter, pageable);
        Page<OrganizationVO> organizationVOPage = ConvertUtils.convert(organizations, OrganizationVO.class);
        return RestResult.successData(organizationVOPage);
    }

    @ApiOperationSort(30)
    @ApiOperation("添加组织")
    @PostMapping
    @RequirePermissions(SYSTEM_ORGANIZATION_ADD)
    public RestResult<OrganizationVO> add(@RequestBody @Valid OrganizationAddFormVO organizationAddFormVO) {
        Organization organization = Organization.builder()
                .name(organizationAddFormVO.getName())
                .type(OrganizationType.CUSTOM)
                .build();
        Organization savedOrganization = organizationService.save(organization);
        String path = savedOrganization.getId() + "-";
        savedOrganization.setPath(path);
        Organization parent = organizationAddFormVO.getParent();
        if (parent != null) {
            savedOrganization.setPath(parent.getPath() + path);
            savedOrganization.setParentId(parent.getId());
        }
        savedOrganization = organizationService.save(savedOrganization);
        OrganizationVO organizationVO = ConvertUtils.convert(savedOrganization, OrganizationVO.class);
        return RestResult.successData(organizationVO);
    }

    @ApiOperationSort(40)
    @ApiOperation("修改组织")
    @PutMapping("/{id}")
    @RequirePermissions(SYSTEM_ORGANIZATION_UPDATE)
    public RestResult<OrganizationVO> update(@PathVariable("id") Organization organization,
                                             @RequestBody @Valid OrganizationUpdateFormVO organizationUpdateFormVO) {
        organization.setName(organizationUpdateFormVO.getName());
        organizationService.save(organization);
        OrganizationVO organizationVO = ConvertUtils.convert(organization, OrganizationVO.class);
        return RestResult.successData(organizationVO);
    }

    @ApiOperationSort(50)
    @ApiOperation("删除组织")
    @ApiImplicitParam(name = "id", value = "组织id", example = "1", paramType = "organizationPath", required = true)
    @DeleteMapping("/{id}")
    @RequirePermissions(SYSTEM_ORGANIZATION_DELETE)
    public RestResult delete(@PathVariable("id") Organization organization) {
        OrganizationFilter organizationFilter = OrganizationFilter.builder()
                .parentId(organization.getId())
                .build();
        List<Organization> children = organizationService.findAll(organizationFilter);

        if (!children.isEmpty()) {
            log.error("存在子企业，无法删除，id:{}", organization.getId());
            throw new RestException(RestErrorCode.ORGANIZATION_HAS_CHILD);
        }
        if (OrganizationType.SYSTEM.equals(organization.getType())) {
            throw new RestException(RestErrorCode.ORGANIZATION_ROOT);
        }

        organizationService.deleteById(organization.getId());
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(60)
    @ApiOperation("获取单个组织信息")
    @ApiImplicitParam(name = "id", value = "组织id", example = "1", paramType = "organizationPath", required = true)
    @GetMapping("/{id}")
    public RestResult<OrganizationVO> findOne(@PathVariable("id") Organization organization) {
        return RestResult.successData(ConvertUtils.convert(organization, OrganizationVO.class));
    }

}

package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.AuditLog;
import com.peony.common.entity.field.AuditLogType;
import com.peony.common.entity.filter.AuditLogFilter;
import com.peony.common.service.AuditLogService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestResult;
import com.peony.web.aop.RequirePermissions;
import com.peony.web.entity.AuditLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.ZonedDateTime;

import static com.peony.common.entity.field.PermissionKey.AUDIT_LOG;

/**
 * @author 辛毅
 * @date 2019/12/3
 */
@Api(value = "审计日志接口", tags = "审计日志接口")
@ApiSort(60)
@Slf4j
@RestController
@RequestMapping("/audit-logs")
public class AuditLogController extends BaseController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @ApiOperationSort(10)
    @ApiOperation("条件查找消息分页结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键词，用户姓名"),
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "objectId", value = "对象ID"),
            @ApiImplicitParam(name = "objectType", value = "对象类型"),
            @ApiImplicitParam(name = "type", value = "操作类型", dataTypeClass = AuditLogType.class),
            @ApiImplicitParam(name = "startTime", value = "开始日期", example = "2019-01-01T00:00:00+08:00"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", example = "2020-01-01T00:00:00+08:00"),
            @ApiImplicitParam(name = "page", value = "页数，从 0 开始", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createdDate,desc", paramType = "query", allowMultiple = true),
    })
    @GetMapping
    @RequirePermissions(AUDIT_LOG)
    public RestResult<Page<AuditLogVO>> find(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) Integer userId,
                                             @RequestParam(required = false) String objectId,
                                             @RequestParam(required = false) String objectType,
                                             @RequestParam(required = false) AuditLogType type,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime,
                                             @ApiIgnore @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        String organizationPath = authHelper.checkUser().getOrganizationPath();
        AuditLogFilter auditLogFilter = AuditLogFilter.builder()
                .keyword(keyword)
                .userId(userId)
                .organizationPath(organizationPath)
                .type(type)
                .objectId(objectId)
                .objectType(objectType)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Page<AuditLog> auditLogPage = auditLogService.findAll(auditLogFilter, pageable);
        Page<AuditLogVO> auditLogVOPage = ConvertUtils.convert(auditLogPage, AuditLogVO.class);
        return RestResult.successData(auditLogVOPage);
    }

}

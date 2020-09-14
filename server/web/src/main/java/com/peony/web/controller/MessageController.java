package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Message;
import com.peony.common.entity.User;
import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import com.peony.common.entity.filter.MessageFilter;
import com.peony.common.service.MessageService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestResult;
import com.peony.web.aop.RequirePermissions;
import com.peony.web.entity.MessageVO;
import com.peony.web.entity.form.message.MessageAddFormVO;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.ZonedDateTime;

import static com.peony.common.entity.field.PermissionKey.SYSTEM_MESSAGE;

/**
 * @author hk
 * @date 2019/10/26
 */
@Api(value = "消息接口", tags = "消息接口")
@ApiSort(50)
@Slf4j
@RestController
@RequestMapping("/messages")
public class MessageController extends BaseController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiOperationSort(10)
    @ApiOperation("条件查找消息分页结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "readStatus", value = "消息读取状态", dataTypeClass = ReadStatus.class),
            @ApiImplicitParam(name = "startTime", value = "开始日期", example = "2019-01-01T00:00:00+08:00"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", example = "2020-01-01T00:00:00+08:00"),
            @ApiImplicitParam(name = "page", value = "页数，从 0 开始", defaultValue = "0", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", defaultValue = "10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createdDate,desc", paramType = "query", allowMultiple = true)
    })
    @GetMapping
    public RestResult<Page<MessageVO>> find(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) Integer userId,
                                            @RequestParam(required = false) ReadStatus readStatus,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime,
                                            @ApiIgnore @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        String organizationPath = authHelper.checkUser().getOrganizationPath();
        MessageFilter messageFilter = MessageFilter.builder()
                .keyword(keyword)
                .organizationPath(organizationPath)
                .readStatus(readStatus)
                .userId(userId)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Page<Message> messages = messageService.findAll(messageFilter, pageable);
        Page<MessageVO> messageVOS = ConvertUtils.convert(messages, MessageVO.class);
        return RestResult.successData(messageVOS);
    }

    @ApiOperationSort(20)
    @ApiOperation("推送消息")
    @PostMapping
    @RequirePermissions(SYSTEM_MESSAGE)
    public RestResult send(@RequestBody @Valid MessageAddFormVO messageAddFormVO) {
        String content = messageAddFormVO.getContent();
        MessageType type = messageAddFormVO.getType();
        messageAddFormVO.getUserIds()
                .stream()
                .map(userId -> Message.builder()
                        .userId(userId)
                        .content(content)
                        .type(type)
                        .readStatus(ReadStatus.UNREAD)
                        .build()
                )
                .forEach(messageService::save);
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(30)
    @ApiOperation("修改消息的阅读状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "messageId", value = "消息id", paramType = "organizationPath", required = true),
    })
    @PatchMapping("/{messageId}/read")
    public RestResult readMessage(@PathVariable("messageId") Message message) {
        message.setReadStatus(ReadStatus.READ);
        messageService.save(message);
        log.info("消息: {} 的readStatus被修改为: {}", message.getId(), ReadStatus.READ);
        return RestResult.SUCCESS;
    }

    @ApiOperationSort(40)
    @ApiOperation("获取当前登录用户未读消息数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始日期", example = "2019-01-01T00:00:00+08:00"),
            @ApiImplicitParam(name = "endTime", value = "结束日期", example = "2020-01-01T00:00:00+08:00"),
    })
    @GetMapping("/unread-count")
    public RestResult getUnread(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {
        User currentUser = authHelper.checkUser();
        MessageFilter messageFilter = MessageFilter.builder()
                .readStatus(ReadStatus.UNREAD)
                .userId(currentUser.getId())
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Long messageCount = messageService.count(messageFilter);
        return RestResult.successData(messageCount);
    }

}

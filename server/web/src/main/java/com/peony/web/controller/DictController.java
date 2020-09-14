package com.peony.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSort;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.peony.common.entity.Dict;
import com.peony.common.entity.filter.DictFilter;
import com.peony.common.service.DictService;
import com.peony.common.util.ConvertUtils;
import com.peony.common.web.RestResult;
import com.peony.web.aop.Authenticated;
import com.peony.web.entity.DictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 电子围栏
 *
 * @author 杜云山
 * @date 2019/10/26
 */
@Api(value = "字典接口", tags = "字典接口")
@ApiSort(15)
@Slf4j
@RestController
@RequestMapping("dicts")
public class DictController extends BaseController {

    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @ApiOperationSort(10)
    @ApiOperation("条件查找字典结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "字典类型", example = "COLOR_TYPE"),
            @ApiImplicitParam(name = "key", value = "字典数据的key")
    })
    @GetMapping
    @Authenticated(false)
    public RestResult<List<DictVO>> find(@RequestParam(required = false) String type,
                                         @RequestParam(required = false) String key) {
        DictFilter dictFilter = DictFilter.builder()
                .type(type)
                .key(key)
                .build();
        List<Dict> dicts = dictService.findAll(dictFilter);
        List<DictVO> dictVOS = ConvertUtils.convert(dicts, DictVO.class);
        return RestResult.successData(dictVOS);
    }

}

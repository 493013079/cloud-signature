package com.peony.web.entity.option;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 辛毅
 * @date 2019/11/26
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionVO {

    @ApiModelProperty(value = "实际值")
    private Object value;

    @ApiModelProperty(value = "显示值")
    private String label;

    public static <T> List<OptionVO> buildList(List<T> objects,
                                               Function<T, String> labelMapper,
                                               Function<T, Object> valueMapper) {
        return objects.stream().map(object -> OptionVO.builder()
                .label(labelMapper.apply(object))
                .value(valueMapper.apply(object))
                .build()
        ).collect(Collectors.toList());
    }

}

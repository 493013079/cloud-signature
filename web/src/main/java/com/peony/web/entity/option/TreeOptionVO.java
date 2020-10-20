package com.peony.web.entity.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 辛毅
 * @date 2019/11/27
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeOptionVO {

    @ApiModelProperty(value = "实际值")
    private Object value;

    @ApiModelProperty(value = "显示值")
    private String label;

    @ApiModelProperty(value = "子节点")
    private List<TreeOptionVO> children;

    @JsonIgnore
    private Object parentValue;

    public static <T> List<TreeOptionVO> buildTree(List<T> objects,
                                                   Function<T, String> labelMapper,
                                                   Function<T, Object> valueMapper,
                                                   Function<T, Object> parentValueMapper) {
        List<TreeOptionVO> options = objects.stream().map(object -> TreeOptionVO.builder()
                .label(labelMapper.apply(object))
                .value(valueMapper.apply(object))
                .parentValue(parentValueMapper.apply(object))
                .build()
        ).collect(Collectors.toList());

        List<TreeOptionVO> rootOptions = Lists.newArrayList();

        Map<Object, TreeOptionVO> optionMap = options.stream()
                .collect(Collectors.toMap(TreeOptionVO::getValue, option -> option));

        for (TreeOptionVO option : options) {
            Object parentValue = option.getParentValue();

            if (parentValue == null) {
                rootOptions.add(option);
                continue;
            }

            TreeOptionVO parent = optionMap.get(parentValue);
            List<TreeOptionVO> brother = parent.getChildren();
            if (brother == null) {
                parent.setChildren(Lists.newArrayList(option));
            } else {
                brother.add(option);
            }
        }
        return rootOptions;
    }

}

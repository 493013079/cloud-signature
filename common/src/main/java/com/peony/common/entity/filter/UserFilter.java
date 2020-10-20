package com.peony.common.entity.filter;

import com.peony.common.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
public class UserFilter implements EntityFilter<User> {

    private String keyword;

    private String account;

    private Integer roleId;

    private Integer organizationId;

    private String organizationPath;

    private String phoneNumber;

    private String email;

}

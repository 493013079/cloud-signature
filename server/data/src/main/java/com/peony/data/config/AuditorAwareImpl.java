package com.peony.data.config;

import com.peony.common.web.helper.AuthHelper;
import com.peony.data.entity.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * @author dys
 * @date 2019/08/06
 * <p>
 * 配合数据实体PO中的 @CreatedBy  @LastModifiedBy 自动添加更新时使用
 */
@Slf4j
public class AuditorAwareImpl implements AuditorAware<UserPO> {

    private final AuthHelper authHelper;

    AuditorAwareImpl(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    /**
     * 返回操作人员信息
     * 如果返回Optional.empty, 则增加时数据库中的@CreatedBy字段为null, 修改时数据库中的@LastModifiedBy字段不会有变动
     *
     * @return 当前登陆用户
     */
    @Override
    @NonNull
    public Optional<UserPO> getCurrentAuditor() {
        return Optional.empty();
    }

}
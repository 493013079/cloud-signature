package com.peony.web.formatter;

import com.peony.common.entity.Role;
import com.peony.common.service.RoleService;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

/**
 * @author hk
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoleFormatter implements Formatter<Role> {

    private final RoleService RoleService;

    @Override
    @NonNull
    public Role parse(String id, @NonNull Locale locale) {
        if (id.isEmpty()) {
            throw new RestException(RestErrorCode.WRONG_PARAM);
        }
        return Optional.of(RoleService.findById(Integer.valueOf(id))).orElseThrow(() -> {
            log.error("角色不存在, id:{}", id);
            return new RestException(RestErrorCode.ROLE_NOT_EXIST);
        });
    }

    @Override
    @NonNull
    public String print(Role role, @NonNull Locale locale) {
        return role.getId().toString();
    }

}

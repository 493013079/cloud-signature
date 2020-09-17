package com.peony.web.formatter;

import com.peony.common.entity.Permission;
import com.peony.common.service.PermissionService;
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
public class PermissionFormatter implements Formatter<Permission> {

    private final PermissionService PermissionService;

    @Override
    @NonNull
    public Permission parse(String id, @NonNull Locale locale) {
        if (id.isEmpty()) {
            throw new RestException(RestErrorCode.WRONG_PARAM);
        }
        return Optional.of(PermissionService.findById(Integer.valueOf(id))).orElseThrow(() -> {
            log.error("权限不存在, id:{}", id);
            return new RestException(RestErrorCode.PERMISSION_NOT_EXIST);
        });
    }

    @Override
    @NonNull
    public String print(Permission permission, @NonNull Locale locale) {
        return permission.getId().toString();
    }

}

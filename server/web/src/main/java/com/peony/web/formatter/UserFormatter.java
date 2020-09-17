package com.peony.web.formatter;

import com.peony.common.entity.User;
import com.peony.common.service.UserService;
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
public class UserFormatter implements Formatter<User> {

    private final UserService userService;

    @Override
    @NonNull
    public User parse(String id, @NonNull Locale locale) {
        if (id.isEmpty()) {
            throw new RestException(RestErrorCode.WRONG_PARAM);
        }
        return Optional.of(userService.findById(Integer.valueOf(id))).orElseThrow(() -> {
            log.error("用户不存在,id:{}", id);
            return new RestException(RestErrorCode.USER_NOT_EXIST);
        });
    }

    @Override
    @NonNull
    public String print(User user, @NonNull Locale locale) {
        return user.getId().toString();
    }

}

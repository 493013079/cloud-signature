package com.peony.common.web.helper;

import com.peony.common.entity.User;
import com.peony.common.entity.field.PermissionKey;
import com.peony.common.entity.field.RoleType;
import com.peony.common.service.UserService;
import com.peony.common.util.StringUtils;
import com.peony.common.web.Logical;
import com.peony.common.web.RestException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 认证帮助类
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@ConditionalOnBean({StringRedisTemplate.class, UserService.class, UserHelper.class})
@Component
public class AuthHelper {

    private final static String TOKEN_KEY = "token";

    private final static String REDIS_TOKEN_PREFIX = "template:web:user:";

    private final StringRedisTemplate redisTemplate;

    private final UserService userService;

    private final UserHelper userHelper;

    public AuthHelper(StringRedisTemplate redisTemplate,
                      UserService userService,
                      UserHelper userHelper) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.userHelper = userHelper;
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @Nonnull
    public Optional<User> currentUser() {
        Optional<String> optionalCurrentToken = currentToken();
        if (!optionalCurrentToken.isPresent()) {
            return Optional.empty();
        }

        String currentToken = optionalCurrentToken.get();
        String userIdString = redisTemplate.opsForValue().get(REDIS_TOKEN_PREFIX + currentToken);
        if (StringUtils.isEmpty(userIdString)) {
            return Optional.empty();
        }

        Integer userId = Integer.valueOf(userIdString);
        User user = userService.findById(userId);
        Optional<User> optionalUser = Optional.of(user);
        refreshCurrentUser(optionalUser.orElse(null));
        return optionalUser;
    }

    /**
     * 检查用户信息
     *
     * @return 用户信息
     * @throws RestException 检查未通过
     */
    public User checkUser() {
        return currentUser().orElseThrow(() -> RestException.UNAUTHORIZED);
    }

    /**
     * 检查用户角色
     *
     * @param roleTypes 角色
     * @return 用户信息
     * @throws RestException 检查未通过
     */
    public User checkRole(RoleType[] roleTypes) {
        User currentUser = checkUser();
        boolean hasRole = userHelper.hasRole(currentUser, roleTypes);
        if (!hasRole) {
            throw RestException.FORBIDDEN;
        }
        return currentUser;
    }

    /**
     * 检查用户权限
     *
     * @param permissionKeys 权限
     * @param logical        与，或
     * @return 用户信息
     * @throws RestException 检查未通过
     */
    public User checkPermission(PermissionKey[] permissionKeys, Logical logical) {
        User currentUser = checkUser();
        boolean hasPermissions = userHelper.hasPermissions(currentUser, permissionKeys, logical);
        if (!hasPermissions) {
            throw RestException.FORBIDDEN;
        }
        return currentUser;
    }

    /**
     * 设置当前用户信息
     *
     * @param user 用户信息
     * @return token token
     */
    @Nullable
    public String setCurrentUser(@Nullable User user) {
        if (user == null) {
            currentToken().ifPresent(this::invalidToken);
            return null;
        }

        Integer userId = user.getId();
        String token = generateToken();
        redisTemplate.opsForValue().set(REDIS_TOKEN_PREFIX + token, String.valueOf(userId), 3, TimeUnit.HOURS);
        return token;
    }

    /**
     * 刷新当前用户Token有效期
     *
     * @param user 用户信息
     */
    public void refreshCurrentUser(@Nullable User user) {
        Optional<String> optionalCurrentToken = currentToken();
        if (!optionalCurrentToken.isPresent()) {
            return;
        }
        String currentToken = optionalCurrentToken.get();
        if (user == null) {
            this.invalidToken(currentToken);
            return;
        }

        Integer userId = user.getId();
        redisTemplate.opsForValue().set(REDIS_TOKEN_PREFIX + currentToken, String.valueOf(userId), 3, TimeUnit.HOURS);
    }

    /**
     * 获取当前Token
     *
     * @return token
     */
    @Nonnull
    public Optional<String> currentToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        return Optional.ofNullable(request.getHeader(TOKEN_KEY));
    }

    /**
     * 作废Token
     *
     * @param token token
     */
    private void invalidToken(String token) {
        redisTemplate.delete(REDIS_TOKEN_PREFIX + token);
    }

    /**
     * 生成新的Token
     *
     * @return token
     */
    @Nonnull
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

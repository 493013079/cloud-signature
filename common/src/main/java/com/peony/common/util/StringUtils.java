package com.peony.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import xyz.downgoon.snowflake.Snowflake;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author hk
 * @date 2019/10/31
 */
@UtilityClass
public class StringUtils {

    private static final Snowflake SNOWFLAKE_INSTANCE = new Snowflake(1L, 2L);

    public String md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] b = md5.digest();
            int i;
            StringBuilder buf = new StringBuilder();

            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

    public boolean isNotEmpty(@Nullable Object str) {
        return !isEmpty(str);
    }

    public String encodePassword(String password) {
//        String salt = "electric";
        return StringUtils.md5(password);
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateSnowflakeId() {
        return String.valueOf(SNOWFLAKE_INSTANCE.nextId());
    }

}

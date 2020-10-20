package com.peony.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.peony.common.util.DateUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 时间格式化类，配合@
 *
 * @author hk
 */
@Component
public class CustomLocalDateTimeSerialize extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        String formattedDate = DateUtils.format(date);
        gen.writeString(formattedDate);
    }

}
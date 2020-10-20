package com.peony.web.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.peony.common.entity.Organization;
import com.peony.common.util.StringUtils;
import com.peony.common.web.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.format.Formatter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author 辛毅
 * @date 2019/11/24
 */
@Slf4j
@JsonComponent
public class OrganizationDeserializer extends JsonDeserializer<Organization> {

    private final Formatter<Organization> formatter;

    public OrganizationDeserializer(Formatter<Organization> formatter) {
        this.formatter = formatter;
    }

    @Override
    public Organization deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException {
        String id = jsonParser.getValueAsString();
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        try {
            return formatter.parse(id, Locale.CHINA);
        } catch (ParseException e) {
            log.warn(e.getMessage(), e);
            throw RestException.NOT_FOUND;
        }
    }

}

package com.peony.common.util;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import xyz.downgoon.snowflake.Snowflake;

import java.io.Serializable;

public class CustomIDGenerator extends IdentityGenerator {

    private static final Snowflake SNOWFLAKE_INSTANCE = new Snowflake(1L, 2L);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
        return String.valueOf(SNOWFLAKE_INSTANCE.nextId());
    }

}
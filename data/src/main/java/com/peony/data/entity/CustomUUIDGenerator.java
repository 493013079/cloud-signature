//package com.template.data.entity;
//
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SessionImplementor;
//import org.hibernate.id.IdentityGenerator;
//import org.hibernate.id.UUIDGenerator;
//import sun.reflect.misc.FieldUtil;
//
//import java.io.Serializable;
//
//public class CustomUUIDGenerator extends IdentityGenerator {
//
//    @Override
//    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
//        Object id = FieldUtil.readField(object, "id");
//        if (id != null) {
//            return (Serializable) id;
//        }
//        return super.generate(session, object);
//    }
//
//}
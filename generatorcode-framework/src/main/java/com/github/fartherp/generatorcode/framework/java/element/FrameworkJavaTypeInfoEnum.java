/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.framework.core.dao.ConfigurableBaseSqlMapDao;
import com.github.fartherp.framework.core.dao.FieldAccessVo;
import com.github.fartherp.framework.core.dao.TableDataConvertable;
import com.github.fartherp.framework.core.dao.annotation.ColumnDescription;
import com.github.fartherp.framework.core.dao.annotation.Id;
import com.github.fartherp.framework.core.orm.mybatis.annotation.SqlMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/9/25
 */
public enum FrameworkJavaTypeInfoEnum {
    HASH_MAP(HashMap.class.getName()),
    SQL_MAPPER(SqlMapper.class.getName()),
    ID(Id.class.getName()),
    COLUMN_DESCRIPTION(ColumnDescription.class.getName()),
    RESOURCE(Resource.class.getName()),
    SQL_SESSION_FACTORY(SqlSessionFactory.class.getName()),
    REPOSITORY(Repository.class.getName()),
    AUTOWIRED(Autowired.class.getName()),
    CONFIGURABLE_BASE_SQL_MAP_DAO(ConfigurableBaseSqlMapDao.class.getName()),
    SERVICE(Service.class.getName()),
    FIELD_ACCESS_VO(FieldAccessVo.class.getName()),
    TABLE_DATA_CONVERTABLE(TableDataConvertable.class.getName()),
    LIST(List.class.getName()),
    STRING(String.class.getName()),
    DATE(Date.class.getName()),
    OBJECT("java.lang.Object"),
    BOOLEAN("boolean"),
    CRITERIA("Criteria"),
    GENERATED_CRITERIA("GeneratedCriteria"),
            ;

    private String name;

    private JavaTypeInfo javaTypeInfo;

    private FrameworkJavaTypeInfoEnum(String name) {
        this.name = name;
        this.javaTypeInfo = new JavaTypeInfo(name);
    }

    public JavaTypeInfo getJavaTypeInfo() {
        return javaTypeInfo;
    }

    public static final JavaTypeInfo getNewMapInstance() {
        return new JavaTypeInfo("java.util.Map");
    }

    public static final JavaTypeInfo getNewListInstance() {
        return new JavaTypeInfo("java.util.List");
    }

    public static final JavaTypeInfo getNewHashMapInstance() {
        return new JavaTypeInfo("java.util.HashMap");
    }

    public static final JavaTypeInfo getNewArrayListInstance() {
        return new JavaTypeInfo("java.util.ArrayList");
    }

    public static final JavaTypeInfo getNewIteratorInstance() {
        return new JavaTypeInfo("java.util.Iterator");
    }
}

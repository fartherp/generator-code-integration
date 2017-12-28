/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.JavaKeywords;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.Parameter;
import com.github.fartherp.javacode.TopLevelClass;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PosServiceImplGenerator extends AbstractJavaElementGenerator<PosAttributes> {
    public PosServiceImplGenerator(TableInfoWrapper<PosAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getServiceImpl();
        superClass = attributes.getGenericSqlMapServiceImpl();
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getService());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
        topLevelClass.addImportedType(JavaTypeInfoEnum.LIST.getJavaTypeInfo());

        JavaTypeInfo service = attributes.getService();
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Service(\""
                + JavaBeansUtils.getValidPropertyName(service.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.SERVICE.getJavaTypeInfo());

        /** 字段 */
        JavaTypeInfo dao = attributes.getDao();
        topLevelClass.addImportedType(dao);
        String shortName = JavaBeansUtils.getValidPropertyName(dao.getShortName());
        Field field = new Field(shortName, dao);
        String daoAnnotation = "@Resource(name = \"" + shortName +"\")";
        field.setJavaScope(JavaKeywords.PRIVATE);
        field.addAnnotation(daoAnnotation);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(JavaTypeInfoEnum.RESOURCE.getJavaTypeInfo());

        Method setSqlMapDao = new Method("setSqlMapDao");
        setSqlMapDao.setJavaScope(JavaKeywords.PUBLIC);
        setSqlMapDao.addBodyLine("setBaseDao(sqlMapDao);");
        JavaTypeInfo sqlMapDao = attributes.getSqlMapDao();
        Parameter parameter = new Parameter(sqlMapDao, "sqlMapDao");
        setSqlMapDao.addParameter(parameter);
        setSqlMapDao.addAnnotation(daoAnnotation);
        topLevelClass.addMethod(setSqlMapDao);
        topLevelClass.addImportedType(sqlMapDao);

        if (getPk()) {
            topLevelClass.addImportedType(JavaTypeInfoEnum.HASH_MAP.getJavaTypeInfo());
        }
    }
}

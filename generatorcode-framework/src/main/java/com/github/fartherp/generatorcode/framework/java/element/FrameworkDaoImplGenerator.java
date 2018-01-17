/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
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
public class FrameworkDaoImplGenerator extends AbstractJavaElementGenerator<FrameworkAttributes> {
    public FrameworkDaoImplGenerator(TableInfoWrapper<FrameworkAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getDaoImpl();
        superClass = attributes.getConfigurableBaseSqlMapDao();
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getDao());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("dao");

        topLevelClass.addImportedType(attributes.getBo());

        JavaTypeInfo dao = attributes.getDao();
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Repository(\""
                + JavaBeansUtils.getValidPropertyName(dao.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.REPOSITORY.getJavaTypeInfo());

        /** 字段@Autowired */
        JavaTypeInfo mapper = attributes.getMapper();
        Field field = new Field(JavaBeansUtils.getValidPropertyName(mapper.getShortName()), mapper);
        field.setJavaScope(JavaKeywords.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(JavaTypeInfoEnum.AUTOWIRED.getJavaTypeInfo());
        topLevelClass.addImportedType(mapper);

        JavaTypeInfo daoMapper = attributes.getDaoMapper();
        Method getDaoMapper = new Method("getDaoMapper");
        getDaoMapper.setJavaScope(JavaKeywords.PUBLIC);
        getDaoMapper.addBodyLine("return " + JavaBeansUtils.getValidPropertyName(mapper.getShortName()) + ";");
        getDaoMapper.setReturnType(daoMapper);
        topLevelClass.addMethod(getDaoMapper);
        topLevelClass.addImportedType(daoMapper);

        Method setSqlSessionFactory = new Method("setSqlSessionFactory");
        setSqlSessionFactory.setJavaScope(JavaKeywords.PUBLIC);
        setSqlSessionFactory.addBodyLine("setSqlSessionFactoryInternal(sqlSessionFactory);");
        String transactionName =
                context.getTransactionName() != null ? context.getTransactionName() : "sqlSessionFactory";
        setSqlSessionFactory.addAnnotation("@Resource(name = \"" + transactionName + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.RESOURCE.getJavaTypeInfo());
        setSqlSessionFactory.addParameter(new Parameter(JavaTypeInfoEnum.SQL_SESSION_FACTORY.getJavaTypeInfo(), "sqlSessionFactory"));
        topLevelClass.addMethod(setSqlSessionFactory);
        topLevelClass.addImportedType(JavaTypeInfoEnum.SQL_SESSION_FACTORY.getJavaTypeInfo());

        if (getPk()) {
            topLevelClass.addImportedType(JavaTypeInfoEnum.HASH_MAP.getJavaTypeInfo());
        }
    }
}

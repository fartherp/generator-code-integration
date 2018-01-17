/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.JavaKeywords;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.TopLevelClass;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class OsServiceImplGenerator extends AbstractJavaElementGenerator<OsAttributes> {
    public OsServiceImplGenerator(TableInfoWrapper<OsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getServiceImpl();
        superClass = new JavaTypeInfo("com.juzix.juice.developer.manager.impl.GenericManagerImpl" + attributes.getPk());
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getService());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("manager");
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);

        JavaTypeInfo service = attributes.getService();
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Service(\""
                + JavaBeansUtils.getValidPropertyName(service.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.SERVICE.getJavaTypeInfo());

        JavaTypeInfo mapper = attributes.getDao();
        Field field = new Field(JavaBeansUtils.getValidPropertyName(mapper.getShortName()), mapper);
        field.setJavaScope(JavaKeywords.PRIVATE);
        field.addAnnotation("@Resource");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(JavaTypeInfoEnum.RESOURCE.getJavaTypeInfo());
        topLevelClass.addImportedType(mapper);

        JavaTypeInfo baseMapper = new JavaTypeInfo("com.juzix.juice.developer.dao.DaoMapper" + attributes.getPk());
        topLevelClass.addImportedType(baseMapper);

        Method getDaoMapper = new Method("getDao");
        getDaoMapper.setJavaScope(JavaKeywords.PUBLIC);
        getDaoMapper.addBodyLine("return " + JavaBeansUtils.getValidPropertyName(mapper.getShortName()) + ";");
        getDaoMapper.setReturnType(baseMapper);
        topLevelClass.addMethod(getDaoMapper);
        topLevelClass.addImportedType(mapper);
    }
}

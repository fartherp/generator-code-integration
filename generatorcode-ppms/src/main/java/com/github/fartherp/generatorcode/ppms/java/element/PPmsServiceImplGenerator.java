/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.Field;
import com.github.fartherp.codegenerator.java.JavaKeywords;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.Method;
import com.github.fartherp.codegenerator.java.Parameter;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;

import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PPmsServiceImplGenerator extends AbstractJavaElementGenerator<PPmsAttributes> {
    public PPmsServiceImplGenerator(TableInfoWrapper<PPmsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getServiceImpl();
        JavaTypeInfo bo = attributes.getBo();
        superClass = new JavaTypeInfo("com.zrj.pay.core.service.BaseTransactionServiceImpl<" + bo.getShortName() + ">");
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getService());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
        topLevelClass.addImportedType(JavaTypeInfoEnum.LIST.getJavaTypeInfo());
        // dao field
        JavaTypeInfo dao = attributes.getDao();
        String shortName = JavaBeansUtils.getValidPropertyName(dao.getShortName());
        Field field = new Field(shortName, dao);
        field.setJavaScope(JavaKeywords.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(dao);

        JavaTypeInfo params = new JavaTypeInfo(List.class.getName() + "<" + bo.getShortName() + ">");
        Parameter parameter = new Parameter(params, "list");
        Method method = new Method("$insertBatch");
        method.setJavaScope(JavaKeywords.PUBLIC);
        method.addParameter(parameter);
        StringBuilder sb = new StringBuilder();
        // method
        sb.append(shortName);
        sb.append(".insertBatch(list);");
        Method tmpMethod = new Method(method);
        tmpMethod.addBodyLine(sb.toString());
        topLevelClass.addMethod(tmpMethod);

        tmpMethod = new Method(JavaBeansUtils.getSetterMethodName(shortName));
        tmpMethod.setJavaScope(JavaKeywords.PUBLIC);
        tmpMethod.addParameter(new Parameter(dao, shortName));
        sb.setLength(0);
        sb.append("this.");
        sb.append(shortName);
        sb.append(" = ");
        sb.append(shortName);
        sb.append(';');
        tmpMethod.addBodyLine(sb.toString());
        topLevelClass.addMethod(tmpMethod);
    }
}

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaKeywords;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.Method;
import com.github.fartherp.codegenerator.java.Parameter;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PPmsDaoGenerator extends AbstractJavaElementGenerator<PPmsAttributes> {
    public PPmsDaoGenerator(TableInfoWrapper<PPmsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getDao();
        superClass = new JavaTypeInfo("com.zrj.pay.core.dao.BaseDao");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Repository(\""
                + JavaBeansUtils.getValidPropertyName(javaTypeInfo.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.REPOSITORY.getJavaTypeInfo());

        topLevelClass.addImportedType(JavaTypeInfoEnum.LIST.getJavaTypeInfo());
        JavaTypeInfo javaTypeInfo = new JavaTypeInfo("List<" + bo.getShortName() + ">");
        Parameter parameter = new Parameter(javaTypeInfo, "list");
        Method method = new Method("insertBatch");
        method.setJavaScope(JavaKeywords.PUBLIC);
        method.addParameter(parameter);
        topLevelClass.addMethod(method);
    }
}

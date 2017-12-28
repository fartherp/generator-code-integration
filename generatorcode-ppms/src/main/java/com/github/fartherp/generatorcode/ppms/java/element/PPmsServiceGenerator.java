/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.javacode.JavaKeywords;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.Parameter;
import com.github.fartherp.javacode.TopLevelClass;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PPmsServiceGenerator extends AbstractJavaElementGenerator<PPmsAttributes> {
    public PPmsServiceGenerator(TableInfoWrapper<PPmsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getService();
        JavaTypeInfo bo = attributes.getBo();
        superClass = new JavaTypeInfo("com.zrj.pay.core.service.BaseTransactionService<" + bo.getShortName() + ">");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);

        topLevelClass.addImportedType(JavaTypeInfoEnum.LIST.getJavaTypeInfo());
        JavaTypeInfo params = new JavaTypeInfo(List.class.getName() + "<" + bo.getShortName() + ">");
        Parameter parameter = new Parameter(params, "list");
        Method method = new Method("$insertBatch");
        method.setJavaScope(JavaKeywords.PUBLIC);
        method.addParameter(parameter);
        topLevelClass.addMethod(method);
    }
}

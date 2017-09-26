/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.Field;
import com.github.fartherp.codegenerator.java.JavaKeywords;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.Method;
import com.github.fartherp.codegenerator.java.Parameter;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PPmsActionGenerator extends AbstractJavaElementGenerator<PPmsAttributes> {
    public PPmsActionGenerator(TableInfoWrapper<PPmsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getController();
        superClass = new JavaTypeInfo("com.zrj.pay.core.action.BaseController");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        // service field
        JavaTypeInfo service = attributes.getService();
        String shortName = JavaBeansUtils.getValidPropertyName(service.getShortName());
        Field field = new Field(shortName, service);
        field.setJavaScope(JavaKeywords.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(service);

        // method
        Method method = new Method("execute");
        JavaTypeInfo typeInfo = new JavaTypeInfo("org.springframework.web.servlet.ModelAndView");
        method.setReturnType(typeInfo);
        topLevelClass.addImportedType(typeInfo);
        method.setJavaScope(JavaKeywords.PUBLIC);
        JavaTypeInfo params = new JavaTypeInfo("javax.servlet.http.HttpServletRequest");
        Parameter parameter = new Parameter(params, "request");
        method.addParameter(parameter);
        topLevelClass.addImportedType(params);

        params = new JavaTypeInfo("javax.servlet.http.HttpServletResponse");
        parameter = new Parameter(params, "response");
        method.addParameter(parameter);
        topLevelClass.addImportedType(params);

        StringBuilder sb = new StringBuilder();
        sb.append("ModelAndViewObject mavo = new ModelAndViewObject(\"\");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        sb.append("return mavo.getMAV();");
        method.addBodyLine(sb.toString());
        topLevelClass.addMethod(method);
        typeInfo = new JavaTypeInfo("com.zrj.pay.core.model.ModelAndViewObject");
        topLevelClass.addImportedType(typeInfo);

        method = new Method(JavaBeansUtils.getSetterMethodName(shortName));
        method.setJavaScope(JavaKeywords.PUBLIC);
        method.addParameter(new Parameter(service, shortName));
        sb.setLength(0);
        sb.append("this.");
        sb.append(shortName);
        sb.append(" = ");
        sb.append(shortName);
        sb.append(';');
        method.addBodyLine(sb.toString());
        topLevelClass.addMethod(method);

        // log service field
        JavaTypeInfo log = new JavaTypeInfo("com.zrj.pay.ppms.tx.ppms.PpmsOpLogTransactionService");
        shortName = JavaBeansUtils.getValidPropertyName(log.getShortName());
        field = new Field(shortName, log);
        field.setJavaScope(JavaKeywords.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(log);

        method = new Method(JavaBeansUtils.getSetterMethodName(shortName));
        method.setJavaScope(JavaKeywords.PUBLIC);
        method.addParameter(new Parameter(log, shortName));
        sb.setLength(0);
        sb.append("this.");
        sb.append(shortName);
        sb.append(" = ");
        sb.append(shortName);
        sb.append(';');
        method.addBodyLine(sb.toString());
        topLevelClass.addMethod(method);
    }
}

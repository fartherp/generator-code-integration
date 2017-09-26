/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.java.element;

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
import com.github.fartherp.generatorcode.pos.db.PosAttributes;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PosActionGenerator extends AbstractJavaElementGenerator<PosAttributes> {
    public PosActionGenerator(TableInfoWrapper<PosAttributes> tableInfoWrapper) {
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
        field.addAnnotation("@Resource(name = \"" + shortName +"\")");
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
        sb.append(attributes.getPageVo().getShortName());
        sb.append(" page = RequestParamsUtil.convertPage(request, mavo, ");
        sb.append(attributes.getPageVo().getShortName());
        sb.append(".class);");
        method.addBodyLine(sb.toString());
        topLevelClass.addImportedType(attributes.getPageVo());
        topLevelClass.addImportedType(attributes.getBo());

        sb.setLength(0);
        sb.append("List<");
        sb.append(attributes.getBo().getShortName());
        sb.append("> list = ");
        sb.append(shortName);
        sb.append(".findPage(page);");
        method.addBodyLine(sb.toString());
        topLevelClass.addImportedType(attributes.getBo());
        topLevelClass.addImportedType(JavaTypeInfoEnum.LIST.getJavaTypeInfo());

        method.addBodyLine("mavo.setList(list);");
        sb.setLength(0);
        sb.append("return mavo.getMAV();");
        method.addBodyLine(sb.toString());
        topLevelClass.addMethod(method);
        typeInfo = new JavaTypeInfo("com.zrj.pay.core.model.ModelAndViewObject");
        topLevelClass.addImportedType(typeInfo);

        // log service field
        JavaTypeInfo log = new JavaTypeInfo("com.zrj.pay.pos.ppms.tx.log.PpmsOpLogTransactionService");
        shortName = JavaBeansUtils.getValidPropertyName(log.getShortName());
        field = new Field(shortName, log);
        field.setJavaScope(JavaKeywords.PRIVATE);
        field.addAnnotation("@Resource(name = \"" + shortName +"\")");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(log);
        topLevelClass.addImportedType(JavaTypeInfoEnum.RESOURCE.getJavaTypeInfo());
        topLevelClass.addImportedType(new JavaTypeInfo("com.zrj.pay.pos.ppms.util.RequestParamsUtil"));
    }
}

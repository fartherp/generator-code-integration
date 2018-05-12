/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.InnerClass;
import com.github.fartherp.javacode.JavaKeywords;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.Parameter;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PltDemoUtilsGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltDemoUtilsGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = new JavaTypeInfo(context.getTargetPackage() + ".demo.utils.ServantInvoke<T, Req, Resp>");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("demo");
        topLevelClass.setAbstract(true);

        Field field = new Field("logger", new JavaTypeInfo("com.judsf.framework.logger.helper.JULogger"));
        field.setFinal(true);
        field.setJavaScope(JavaKeywords.PRIVATE);
        topLevelClass.addField(field);
        Method con = new Method("ServantInvoke");
        con.setConstructor(true);
        con.setJavaScope(JavaKeywords.PUBLIC);
        con.addParameter(new Parameter(new JavaTypeInfo("Object"), "o"));
        con.addBodyLine("logger = LoggerFactory.getLogger(o.getClass());");
        topLevelClass.addMethod(con);

        Method invoke = new Method("invoke");
        invoke.setJavaScope(JavaKeywords.PUBLIC);
        invoke.addParameter(new Parameter(new JavaTypeInfo("T"), "__cb"));
        invoke.addParameter(new Parameter(new JavaTypeInfo("Ice.Current"), "__current"));
        invoke.addParameter(new Parameter(new JavaTypeInfo("String"), "label"));
        invoke.addParameter(new Parameter(new JavaTypeInfo("Req"), "req"));

        invoke.addBodyLine("JUContext context = ServantHelp.startJUContextAndPrintReq(label, JSON.toJSONString(req), logger, __current);");
        invoke.addBodyLine("try {");
        invoke.addBodyLine("Resp resp = invokeReq(__cb, __current, req);");
        invoke.addBodyLine("context.end();");
        invoke.addBodyLine("invokeResp(__cb, __current, resp);");
        invoke.addBodyLine("logger.info(\"{} out param:{}\", label, resp == null ? \"void\" : JSON.toJSONString(resp));");
        invoke.addBodyLine("} catch (Exception e) {");
        invoke.addBodyLine("ServantHelp.onJUContextFail(label, context, logger, e);");
        invoke.addBodyLine("}");
        topLevelClass.addMethod(invoke);

        Method invokeReq = new Method("invokeReq");
        invokeReq.setJavaScope(JavaKeywords.PUBLIC);
        invokeReq.addParameter(new Parameter(new JavaTypeInfo("T"), "__cb"));
        invokeReq.addParameter(new Parameter(new JavaTypeInfo("Ice.Current"), "__current"));
        invokeReq.addParameter(new Parameter(new JavaTypeInfo("Req"), "req"));
        invokeReq.setReturnType(new JavaTypeInfo("Resp"));
        topLevelClass.addMethod(invokeReq);

        Method invokeResp = new Method("invokeResp");
        invokeResp.setJavaScope(JavaKeywords.PUBLIC);
        invokeResp.addParameter(new Parameter(new JavaTypeInfo("T"), "__cb"));
        invokeResp.addParameter(new Parameter(new JavaTypeInfo("Ice.Current"), "__current"));
        invokeResp.addParameter(new Parameter(new JavaTypeInfo("Resp"), "resp"));
        topLevelClass.addMethod(invokeResp);

        InnerClass innerClass = new InnerClass(new JavaTypeInfo(context.getTargetPackage() + ".demo.utils.ServantInvoke.ServantHelp"));
        innerClass.setJavaScope(JavaKeywords.PUBLIC);
        innerClass.setStatic(true);
        Method startJUContextAndPrintReq = new Method("startJUContextAndPrintReq");
        startJUContextAndPrintReq.setJavaScope(JavaKeywords.PUBLIC);
        startJUContextAndPrintReq.setStatic(true);
        startJUContextAndPrintReq.addParameter(new Parameter(new JavaTypeInfo("String"), "label"));
        startJUContextAndPrintReq.addParameter(new Parameter(new JavaTypeInfo("String"), "reqJson"));
        startJUContextAndPrintReq.addParameter(new Parameter(new JavaTypeInfo("com.judsf.framework.logger.helper.JULogger"), "logger"));
        startJUContextAndPrintReq.addParameter(new Parameter(new JavaTypeInfo("Ice.Current"), "__current"));
        startJUContextAndPrintReq.addBodyLine("logger.info(label + \" in param:{}\", reqJson);");
        startJUContextAndPrintReq.addBodyLine("JUContext context = new JUContext(__current, label);");
        startJUContextAndPrintReq.addBodyLine("context.start();");
        startJUContextAndPrintReq.addBodyLine("return context;");
        startJUContextAndPrintReq.setReturnType(new JavaTypeInfo("com.judsf.framework.common.ice.JUContext"));
        innerClass.addMethod(startJUContextAndPrintReq);

        Method onJUContextFail = new Method("onJUContextFail");
        onJUContextFail.setJavaScope(JavaKeywords.PUBLIC);
        onJUContextFail.setStatic(true);
        onJUContextFail.addParameter(new Parameter(new JavaTypeInfo("String"), "label"));
        onJUContextFail.addParameter(new Parameter(new JavaTypeInfo("JUContext"), "context"));
        onJUContextFail.addParameter(new Parameter(new JavaTypeInfo("com.judsf.framework.logger.helper.JULogger"), "logger"));
        onJUContextFail.addParameter(new Parameter(new JavaTypeInfo("java.lang.Exception"), "e"));
        onJUContextFail.addBodyLine("context.failed(label + \" Exception\", e);");
        onJUContextFail.addBodyLine("logger.error(label + \" Exception\", e);");
        onJUContextFail.addBodyLine("if (e instanceof BizException) {");
        onJUContextFail.addBodyLine("logger.error(label + \" BizException\", ((BizException) e).errorMsg);");
        onJUContextFail.addBodyLine("throw (BizException) e;");
        onJUContextFail.addBodyLine("} else {");
        onJUContextFail.addBodyLine("throw new BizException(ErrorEnum.SystemDefaultError.value(), \"需要修改自己的异常\");");
        onJUContextFail.addBodyLine("}");
        innerClass.addMethod(onJUContextFail);
        topLevelClass.addInnerClass(innerClass);

        topLevelClass.addImportedType("Ice.Current");
        topLevelClass.addImportedType("com.alibaba.fastjson.JSON");
        topLevelClass.addImportedType("com.judsf.framework.common.ice.JUContext");
        topLevelClass.addImportedType("com.judsf.framework.logger.helper.JULogger");
        topLevelClass.addImportedType("com.judsf.framework.logger.helper.LoggerFactory");
        topLevelClass.addImportedType(context.getClasspath() + ".slice.common.BizException");
        topLevelClass.addImportedType(context.getClasspath() + ".slice.common.ErrorEnum");
    }
}

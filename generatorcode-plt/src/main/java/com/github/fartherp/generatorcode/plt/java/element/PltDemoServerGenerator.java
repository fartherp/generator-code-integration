/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
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
public class PltDemoServerGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltDemoServerGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = new JavaTypeInfo(context.getTargetPackage() + ".demo.server.DemoServer");
        superInterfaces.add(new JavaTypeInfo("Ice.Application"));
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("demo");

        Method runMethod = new Method("run");
        runMethod.setJavaScope(JavaKeywords.PUBLIC);
        runMethod.addParameter(new Parameter(new JavaTypeInfo("String[]"), "args"));
        runMethod.setReturnType(new JavaTypeInfo("int"));
        runMethod.addBodyLine("JULogger logger = LoggerFactory.getLogger(" + javaTypeInfo.getShortName() + ".class);");
        runMethod.addBodyLine("SpringUtil.init(\"spring-" + context.getProjectName() + "-demo.xml\");");
        runMethod.addBodyLine("Ice.Properties prop = communicator().getProperties();");
        String projectName = context.getProjectName();
        String adapterName = projectName.substring(0, 1).toUpperCase() + projectName.substring(1, projectName.length()) +  javaTypeInfo.getShortName() + "Adapter";
        runMethod.addBodyLine("Ice.ObjectAdapter adapter = communicator().createObjectAdapter(\"" + adapterName + "\");");
        runMethod.addBodyLine("adapter.add(new DemoServant(), communicator().stringToIdentity(prop.getProperty(ProxyEnum.DEMO_PROXY.getCode())));");
        runMethod.addBodyLine("adapter.activate();");
        runMethod.addBodyLine("logger.info(" + adapterName + " start...);");
        runMethod.addBodyLine("communicator().waitForShutdown();");
        runMethod.addBodyLine("JUObjectProxyHelper.getInstance().destroy();");
        runMethod.addBodyLine("return 0;");
        topLevelClass.addMethod(runMethod);

        Method mainMethod = new Method("main");
        mainMethod.setJavaScope(JavaKeywords.PUBLIC);
        mainMethod.addParameter(new Parameter(new JavaTypeInfo("String[]"), "args"));
        mainMethod.addBodyLine(javaTypeInfo.getShortName() + " server = new " + javaTypeInfo.getShortName() + "()");
        mainMethod.addBodyLine("int status = server.main(server.getClass().getSimpleName(), args);");
        mainMethod.addBodyLine("System.exit(status);");
        topLevelClass.addMethod(mainMethod);

        topLevelClass.addImportedType("com.judsf.framework.common.ice.JUObjectProxyHelper");
        topLevelClass.addImportedType("com.judsf.framework.logger.helper.JULogger");
        topLevelClass.addImportedType("com.judsf.framework.logger.helper.LoggerFactory");
        topLevelClass.addImportedType(context.getTargetPackage() + ".demo.servant.DemoServant");
    }
}

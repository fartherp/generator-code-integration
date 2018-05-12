/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PltDemoServiceImplGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltDemoServiceImplGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = new JavaTypeInfo(context.getTargetPackage() + ".demo.service.impl.DemoServiceImpl");
        superInterfaces.add(new JavaTypeInfo(context.getTargetPackage() + ".demo.service.DemoService"));
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("demo");
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Service(\""
                + JavaBeansUtils.getValidPropertyName(javaTypeInfo.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.SERVICE.getJavaTypeInfo());

    }
}

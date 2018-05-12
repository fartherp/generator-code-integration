/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PltDemoServiceGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltDemoServiceGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = new JavaTypeInfo(context.getTargetPackage() + ".demo.service.DemoService");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("demo");
        topLevelClass.setInterface(true);
    }
}

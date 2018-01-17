/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class FrameworkExtendsBoGenerator extends AbstractJavaElementGenerator<FrameworkAttributes> {
    public FrameworkExtendsBoGenerator(TableInfoWrapper<FrameworkAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getBo();
        superClass = attributes.getBaseBo();
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("bean");
    }
}

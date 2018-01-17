/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class FrameworkServiceGenerator extends AbstractJavaElementGenerator<FrameworkAttributes> {
    public FrameworkServiceGenerator(TableInfoWrapper<FrameworkAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getService();
        superClass = attributes.getGenericService();
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("service");

        topLevelClass.setInterface(true);
        topLevelClass.addImportedType(attributes.getBo());

        if (getPk()) {
            topLevelClass.addImportedType(JavaTypeInfoEnum.HASH_MAP.getJavaTypeInfo());
        }
    }
}

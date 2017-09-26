/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class FrameworkMapperGenerator extends AbstractJavaElementGenerator<FrameworkAttributes> {
    public FrameworkMapperGenerator(TableInfoWrapper<FrameworkAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getMapper();
        superClass = attributes.getDaoMapper();
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        topLevelClass.addImportedType(attributes.getBo());
        topLevelClass.addAnnotation("@SqlMapper");

        topLevelClass.addImportedType(FrameworkJavaTypeInfoEnum.SQL_MAPPER.getJavaTypeInfo());
        if (getPk()) {
            topLevelClass.addImportedType(JavaTypeInfoEnum.HASH_MAP.getJavaTypeInfo());
        }
    }
}

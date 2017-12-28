/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.op.db.OpAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class OpMapperGenerator extends AbstractJavaElementGenerator<OpAttributes> {
    public OpMapperGenerator(TableInfoWrapper<OpAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getDao();
        superClass = new JavaTypeInfo("jz.com.asset.dao.BaseMapper" + attributes.getPk());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
        topLevelClass.addAnnotation("@Mapper");
        topLevelClass.addImportedType(new JavaTypeInfo("org.apache.ibatis.annotations.Mapper"));
    }
}

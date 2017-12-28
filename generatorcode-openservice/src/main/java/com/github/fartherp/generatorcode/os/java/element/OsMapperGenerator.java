/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class OsMapperGenerator extends AbstractJavaElementGenerator<OsAttributes> {
    public OsMapperGenerator(TableInfoWrapper<OsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getDao();
        superClass = new JavaTypeInfo("com.juzix.juice.developer.dao.DaoMapper" + attributes.getPk());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
    }
}

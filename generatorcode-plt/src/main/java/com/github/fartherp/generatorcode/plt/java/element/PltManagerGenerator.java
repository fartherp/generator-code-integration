/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PltManagerGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltManagerGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getService();
        superClass = new JavaTypeInfo("com.juzix.plt.manager.BaseManager" + attributes.getPk());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setInterface(true);
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);
    }
}

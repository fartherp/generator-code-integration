/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.TopLevelClass;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/9/1
 */
public class PosPageVoGenerator extends AbstractJavaElementGenerator<PosAttributes> {

    public PosPageVoGenerator(TableInfoWrapper<PosAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getPageVo();
        superClass = new JavaTypeInfo("com.zrj.pay.core.model.PageObject");
    }

    public void dealElement(TopLevelClass topLevelClass) {

    }
}

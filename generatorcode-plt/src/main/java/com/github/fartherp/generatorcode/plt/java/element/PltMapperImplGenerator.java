/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PltMapperImplGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltMapperImplGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getDaoImpl();
        superClass = new JavaTypeInfo("com.juzix.plt.dao.mapper.impl.BaseMapperImpl" + attributes.getPk());
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getDao());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        JavaTypeInfo bo = attributes.getBo();
        JavaTypeInfo dao = attributes.getDao();
        topLevelClass.addImportedType(bo);
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Repository(\""
                + JavaBeansUtils.getValidPropertyName(dao.getShortName())  + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.REPOSITORY.getJavaTypeInfo());
    }
}

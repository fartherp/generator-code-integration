/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.Field;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.Method;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;

import java.util.List;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class PPmsBaseBoGenerator extends AbstractJavaElementGenerator<PPmsAttributes> {
    public PPmsBaseBoGenerator(TableInfoWrapper<PPmsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getBo();
        superClass = new JavaTypeInfo("com.zrj.pay.core.model.BaseObject");
    }

    public void dealElement(TopLevelClass topLevelClass) {
        List<ColumnInfo> columnInfos = getColumnsInThisClass();

        for (ColumnInfo columnInfo : columnInfos) {
            /** 字段属性 */
            Field field = JavaBeansUtils.getJavaBeansField(columnInfo, tableInfoWrapper);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());

            /** get方法 */
            Method method = JavaBeansUtils.getJavaBeansGetter(columnInfo, tableInfoWrapper);
            topLevelClass.addMethod(method);

            /** set方法 */
            method = JavaBeansUtils.getJavaBeansSetter(columnInfo, context, tableInfoWrapper);
            topLevelClass.addMethod(method);
        }

    }
}

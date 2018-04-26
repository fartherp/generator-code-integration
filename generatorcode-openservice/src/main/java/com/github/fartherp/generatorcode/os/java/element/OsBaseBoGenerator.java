/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.java.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.framework.database.dao.FieldAccessVo;
import com.github.fartherp.framework.database.dao.annotation.Id;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.TopLevelClass;

import java.util.List;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class OsBaseBoGenerator extends AbstractJavaElementGenerator<OsAttributes> {
    public OsBaseBoGenerator(TableInfoWrapper<OsAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getBaseBo();
        superClass = new JavaTypeInfo(FieldAccessVo.class.getName());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("pojo");
        topLevelClass.addImportedType(Id.class.getName());

        List<ColumnInfo> columnInfos = getColumnsInThisClass();
        List<ColumnInfo> columnInfoList = tableInfoWrapper.getPrimaryKeyColumns();

        for (ColumnInfo columnInfo : columnInfos) {
            /** 字段属性 */
            Field field = JavaBeansUtils.getJavaBeansField(columnInfo, tableInfoWrapper);
            topLevelClass.addField(field);
            topLevelClass.addImportedType(field.getType());

            /** 主键@Id注解 */
            for (ColumnInfo c : columnInfoList) {
                if (columnInfo.getActualColumnName().equals(c.getActualColumnName())) {
                    field.addAnnotation("@Id");
                    break;
                }
            }

            /** get方法 */
            Method method = JavaBeansUtils.getJavaBeansGetter(columnInfo, tableInfoWrapper);
            topLevelClass.addMethod(method);

            /** set方法 */
            method = JavaBeansUtils.getJavaBeansSetter(columnInfo, context, tableInfoWrapper);
            topLevelClass.addMethod(method);
        }

    }
}

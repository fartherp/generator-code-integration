/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.framework.core.dao.annotation.ColumnDescription;
import com.github.fartherp.framework.core.dao.annotation.Id;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.TopLevelClass;

import java.util.HashSet;
import java.util.List;

/**
 * 基础类
 * Author: CK
 * Date: 2015/6/13
 */
public class FrameworkBaseBoGenerator extends AbstractJavaElementGenerator<FrameworkAttributes> {
    public FrameworkBaseBoGenerator(TableInfoWrapper<FrameworkAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getBaseBo();
        superClass = FrameworkJavaTypeInfoEnum.FIELD_ACCESS_VO.getJavaTypeInfo();
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(FrameworkJavaTypeInfoEnum.TABLE_DATA_CONVERTABLE.getJavaTypeInfo());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        topLevelClass.setModule("bean");

        topLevelClass.setAbstract(true);

        List<ColumnInfo> columnInfos = getColumnsInThisClass();
        List<ColumnInfo> columnInfoList = tableInfoWrapper.getPrimaryKeyColumns();

        for (ColumnInfo columnInfo : columnInfos) {
            /** 字段常量 */
            Field fieldConstant = JavaBeansUtils.getJavaBeansFieldConstant(columnInfo, tableInfoWrapper);
            topLevelClass.addField(fieldConstant);

            /** 字段描述常量 */
            Field fieldRemark = JavaBeansUtils.getJavaBeansFieldRemark(columnInfo, tableInfoWrapper);
            topLevelClass.addField(fieldRemark);

            /** 字段属性 */
            Field field = JavaBeansUtils.getJavaBeansField(columnInfo, tableInfoWrapper);
            /** 字段@ColumnDescription(desc = "")注解 */
            field.addAnnotation("@" + ColumnDescription.class.getSimpleName()
                    + "(desc = " + fieldRemark.getName() + ")");
            topLevelClass.addImportedType(FrameworkJavaTypeInfoEnum.COLUMN_DESCRIPTION.getJavaTypeInfo());
            /** 主键@Id注解 */
            for (ColumnInfo c : columnInfoList) {
                if (columnInfo.getActualColumnName().equals(c.getActualColumnName())) {
                    field.addAnnotation("@" + Id.class.getSimpleName());
                    topLevelClass.addImportedType(FrameworkJavaTypeInfoEnum.ID.getJavaTypeInfo());
                    break;
                }
            }
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

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.XmlElement;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 基础resultMap（非BLOB字段）
 * Author: CK
 * Date: 2015/6/7
 */
public class PltResultMapWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator<PltAttributes> {

    private boolean isSimple;

    public PltResultMapWithoutBLOBsElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    public void prepareXmlElement() {
        name = "resultMap";
        id = attributes.getDao().getShortName() + "_" + attributes.getBaseResultMap();
        type = "java.util.Map";
    }

    public void dealElements() {
        if (tableInfoWrapper.isConstructorBased()) {
            addResultMapConstructorElements(answer);
        } else {
            addResultMapElements(answer);
        }
    }

    private void addResultMapElements(XmlElement answer) {
        for (ColumnInfo introspectedColumn : tableInfoWrapper.getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("id");

            resultElement.addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtils.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty()));
            resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));

            if (StringUtils.isNotBlank(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn.getTypeHandler()));
            }

            answer.addElement(resultElement);
        }

        List<ColumnInfo> columns;
        if (isSimple) {
            columns = tableInfoWrapper.getNonPrimaryKeyColumns();
        } else {
            columns = tableInfoWrapper.getBaseColumns();
        }
        for (ColumnInfo introspectedColumn : columns) {
            XmlElement resultElement = new XmlElement("result");

            resultElement.addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtils.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty()));
            resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));

            if (StringUtils.isNotBlank(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn.getTypeHandler()));
            }

            answer.addElement(resultElement);
        }
    }

    private void addResultMapConstructorElements(XmlElement answer) {
        XmlElement constructor = new XmlElement("constructor");

        for (ColumnInfo c : tableInfoWrapper.getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("idArg");
            resultElement.addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtils.getRenamedColumnNameForResultMap(c)));
            resultElement.addAttribute(new Attribute("jdbcType", c.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType", c.getJavaTypeInfo().getFullyQualifiedName()));

            if (StringUtils.isNotBlank(c.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", c.getTypeHandler()));
            }

            constructor.addElement(resultElement);
        }

        List<ColumnInfo> columns;
        if (isSimple) {
            columns = tableInfoWrapper.getNonPrimaryKeyColumns();
        } else {
            columns = tableInfoWrapper.getBaseColumns();
        }
        for (ColumnInfo c : columns) {
            XmlElement resultElement = new XmlElement("arg");

            resultElement.addAttribute(new Attribute(
                    "column", MyBatis3FormattingUtils.getRenamedColumnNameForResultMap(c)));
            resultElement.addAttribute(new Attribute("jdbcType",
                    c.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType", c.getJavaTypeInfo().getFullyQualifiedName()));

            if (StringUtils.isNotBlank(c.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", c.getTypeHandler()));
            }

            constructor.addElement(resultElement);
        }

        answer.addElement(constructor);
    }
}

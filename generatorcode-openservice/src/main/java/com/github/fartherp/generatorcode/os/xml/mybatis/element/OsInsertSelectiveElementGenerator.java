/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.TextElement;
import com.github.fartherp.javaxml.XmlElement;

import java.util.List;

/**
 * 添加部分字段,if-test
 * Author: CK
 * Date: 2015/6/7
 */
public class OsInsertSelectiveElementGenerator extends AbstractXmlElementGenerator<OsAttributes> {

    public void prepareXmlElement() {
        name = "insert";
        id = attributes.getInsertSelective();
        parameterType = attributes.getBo().getFullyQualifiedName();
    }

    public void dealElements() {
        List<ColumnInfo> columnInfos = tableInfoWrapper.getPrimaryKeyColumns();
        StringBuilder keyProperty = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            keyProperty.append(columnInfo.getJavaProperty());
            keyProperty.append(',');
        }
        answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
        answer.addAttribute(new Attribute("keyProperty", keyProperty.deleteCharAt(keyProperty.length() - 1).toString()));

        StringBuilder sb = new StringBuilder();

        sb.append("insert into ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement insertTrimElement = new XmlElement("trim");
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(insertTrimElement);

        XmlElement valuesTrimElement = new XmlElement("trim");
        valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
        valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(valuesTrimElement);

        for (ColumnInfo c : tableInfoWrapper.getAllColumns()) {
            // 自增不设值
            if (c.isIdentity()) {
                continue;
            }

            if (c.isSequenceColumn() || c.getJavaTypeInfo().isPrimitive()) {
                // 序列字段必须的
                // MyBatis3是在查询前先解析sql语句
                sb.setLength(0);
                sb.append(MyBatis3FormattingUtils.getEscapedColumnName(c));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtils.getParameterClause(c));
                sb.append(',');
                valuesTrimElement.addElement(new TextElement(sb.toString()));

                continue;
            }

            XmlElement insertNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(c.getJavaProperty());
            sb.append(" != null");
            insertNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(c));
            sb.append(',');
            insertNotNullElement.addElement(new TextElement(sb.toString()));
            insertTrimElement.addElement(insertNotNullElement);

            XmlElement valuesNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(c.getJavaProperty());
            sb.append(" != null");
            valuesNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtils.getParameterClause(c));
            sb.append(',');
            valuesNotNullElement.addElement(new TextElement(sb.toString()));
            valuesTrimElement.addElement(valuesNotNullElement);
        }
    }
}

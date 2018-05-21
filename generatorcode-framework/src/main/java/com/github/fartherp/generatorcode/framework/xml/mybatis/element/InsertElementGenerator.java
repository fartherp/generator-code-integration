/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.framework.common.util.OutputUtils;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.TextElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加全部字段语句
 * Author: CK
 * Date: 2015/6/7
 */
public class InsertElementGenerator extends AbstractXmlElementGenerator<FrameworkAttributes> {

    public void prepareXmlElement() {
        name = "insert";
        id = attributes.getInsert();
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
        answer.addElement(new TextElement("<![CDATA["));

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into ");
        insertClause.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        valuesClause.append("values (");

        List<String> valuesClauses = new ArrayList<String>();
        List<ColumnInfo> columns = tableInfoWrapper.getAllColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo column = columns.get(i);
            if (column.isIdentity()) {
                // 自增不设值
                continue;
            }

            insertClause.append(MyBatis3FormattingUtils.getEscapedColumnName(column));
            valuesClause.append(MyBatis3FormattingUtils.getParameterClause(column));
            if (i + 1 < columns.size()) {
                if (!columns.get(i + 1).isIdentity()) {
                    insertClause.append(", ");
                    valuesClause.append(", ");
                }
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtils.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtils.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }
        answer.addElement(new TextElement("]]>"));
    }
}

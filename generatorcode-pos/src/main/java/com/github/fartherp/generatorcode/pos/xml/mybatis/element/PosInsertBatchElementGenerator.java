/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.framework.common.util.OutputUtils;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.TextElement;
import com.github.fartherp.javaxml.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量添加全部字段语句
 * Author: CK
 * Date: 2015/6/7
 */
public class PosInsertBatchElementGenerator extends AbstractXmlElementGenerator<PosAttributes> {

    public void prepareXmlElement() {
        name = "insert";
        id = attributes.getInsertBatch();
        parameterType = "java.util.List";
    }

    public void dealElements() {
        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into ");
        insertClause.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        valuesClause.append(" (");
        XmlElement middleForEachElement = new XmlElement("foreach");
        middleForEachElement.addAttribute(new Attribute("collection", "list"));
        middleForEachElement.addAttribute(new Attribute("item", "item"));
        middleForEachElement.addAttribute(new Attribute("index", "index"));
        middleForEachElement.addAttribute(new Attribute("separator", ","));

        List<String> valuesClauses = new ArrayList<String>();
        List<ColumnInfo> columns = tableInfoWrapper.getAllColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo column = columns.get(i);
            if (column.isIdentity()) {
                // 自增不设值
                continue;
            }

            insertClause.append(MyBatis3FormattingUtils.getEscapedColumnName(column));
            valuesClause.append(MyBatis3FormattingUtils.getParameterClauseByAlias(column, null, "item"));
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
        answer.addElement(new TextElement(" values "));
        answer.addElement(middleForEachElement);

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            middleForEachElement.addElement(new TextElement(clause));
        }
    }
}

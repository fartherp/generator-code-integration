/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.framework.common.util.OutputUtils;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;

import java.util.Iterator;

/**
 * 生成更新语句（对象所有属性）
 * Author: CK
 * Date: 2015/6/7
 */
public class PosUpdateByPrimaryKeyElementGenerator extends AbstractXmlElementGenerator<PosAttributes> {

    public void prepareXmlElement() {
        name = "update";
        id = attributes.getUpdateByPrimaryKey();
        parameterType = attributes.getBo().getFullyQualifiedName();
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("set ");

        Iterator<ColumnInfo> iter = tableInfoWrapper.getBaseColumns().iterator();
        while (iter.hasNext()) {
            ColumnInfo introspectedColumn = iter.next();

            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtils.getParameterClause(introspectedColumn));

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtils.xmlIndent(sb, 1);
            }
        }

        boolean and = false;
        for (ColumnInfo c : tableInfoWrapper.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(c));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtils.getParameterClause(c));
            answer.addElement(new TextElement(sb.toString()));
        }
    }
}

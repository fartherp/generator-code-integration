/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.framework.common.util.OutputUtils;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

import java.util.Iterator;

/**
 * 生成更新语句（对象的BLOB属性）
 * Author: CK
 * Date: 2015/6/7
 */
public class UpdateByPrimaryKeyWithBLOBsElementGenerator extends AbstractXmlElementGenerator<FrameworkAttributes> {

    public void prepareXmlElement() {
        name = "update";
        id = attributes.getUpdateByPrimaryKeySelectiveWithBLOBs();
        String tmpParameterType;
        if (rules.generateRecordWithBLOBsClass()) {
            tmpParameterType = attributes.getRecordWithBLOBsType();
        } else {
            tmpParameterType = attributes.getBo().getFullyQualifiedName();
        }
        parameterType = tmpParameterType;
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();

        sb.append("update ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("set ");

        Iterator<ColumnInfo> iter = tableInfoWrapper.getBLOBColumns().iterator();
        while (iter.hasNext()) {
            ColumnInfo c = iter.next();

            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(c));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtils.getParameterClause(c));

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

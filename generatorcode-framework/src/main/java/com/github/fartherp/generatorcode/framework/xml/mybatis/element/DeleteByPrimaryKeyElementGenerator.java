/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

/**
 * 删除语句（主键）
 * Author: CK
 * Date: 2015/6/7
 */
public class DeleteByPrimaryKeyElementGenerator extends AbstractXmlElementGenerator<FrameworkAttributes> {

    public void prepareXmlElement() {
        name = "delete";
        id = attributes.getDeleteByPrimaryKey();
        String parameterClass;
        // 主键及外键
        if (tableInfoWrapper.getPrimaryKeyColumns().size() > 1) {
            parameterClass = "hashmap";
        } else {
            parameterClass = tableInfoWrapper.getPrimaryKeyColumns().get(0).getJavaTypeInfo().toString();
        }
        parameterType = parameterClass;
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (ColumnInfo introspectedColumn : tableInfoWrapper.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtils.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
    }
}

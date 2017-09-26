/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;

/**
 * 删除语句（主键）
 * Author: CK
 * Date: 2015/6/7
 */
public class PltDeleteElementGenerator extends AbstractXmlElementGenerator<PltAttributes> {

    public void prepareXmlElement() {
        name = "delete";
        id = attributes.getDao().getShortName() + "_deleteByPrimaryKey";
        parameterType = "java.util.Map";
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

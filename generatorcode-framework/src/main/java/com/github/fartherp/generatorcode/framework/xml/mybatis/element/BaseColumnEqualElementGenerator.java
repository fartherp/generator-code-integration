/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.Attribute;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.XmlElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

/**
 * Where精确匹配字段
 * Author: CK
 * Date: 2015/7/9.
 */
public class BaseColumnEqualElementGenerator extends AbstractXmlElementGenerator<FrameworkAttributes> {

    public void prepareXmlElement() {
        name = "sql";
        id = attributes.getBaseColumnEqual();
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo c : tableInfoWrapper.getAllColumns()) {
            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(c.getJavaProperty());
            sb.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            answer.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append("AND ");
            sb.append(MyBatis3FormattingUtils.getEscapedColumnName(c));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtils.getParameterClause(c));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
    }
}

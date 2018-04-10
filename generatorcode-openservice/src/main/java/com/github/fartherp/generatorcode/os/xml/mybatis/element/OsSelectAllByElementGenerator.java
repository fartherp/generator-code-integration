/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.xml.mybatis.element;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.javaxml.TextElement;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/7
 */
public class OsSelectAllByElementGenerator extends AbstractXmlElementGenerator<OsAttributes> {

    public void prepareXmlElement() {
        name = "select";
        id = attributes.getSelectAll();
        if (rules.generateResultMapWithBLOBs()) {
            resultMap = attributes.getResultMapWithBLOBs();
        } else {
            resultMap = attributes.getBaseResultMap();
        }
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        if (tableInfoWrapper.hasBLOBColumns()) {
            answer.addElement(new TextElement(","));
            answer.addElement(getBlobColumnListElement());
        }

        sb.setLength(0);
        sb.append("from ");
        sb.append(tableInfoWrapper.getTableInfo().getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
    }
}

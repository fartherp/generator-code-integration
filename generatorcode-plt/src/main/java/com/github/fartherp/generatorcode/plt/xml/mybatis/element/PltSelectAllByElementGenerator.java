/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.xml.mybatis.element;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.TextElement;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/7
 */
public class PltSelectAllByElementGenerator extends AbstractXmlElementGenerator<PltAttributes> {

    public void prepareXmlElement() {
        name = "select";
        id = attributes.getDao().getShortName() + "_" + attributes.getSelectAll();
        if (rules.generateResultMapWithBLOBs()) {
            resultMap = attributes.getResultMapWithBLOBs();
        } else {
            resultMap = attributes.getBaseResultMap();
        }
        parameterType = "java.util.Map";
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

    protected XmlElement getBaseColumnListElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", attributes.getDao().getShortName() + "_" + tableInfoWrapper.getAttributes().getBaseColumnList()));
        return answer;
    }
}

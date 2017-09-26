/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.element;

import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

/**
 * 查询总数量
 * Author: CK
 * Date: 2015/6/7
 */
public class CountByElementGenerator extends AbstractXmlElementGenerator<FrameworkAttributes> {

    public void prepareXmlElement() {
        name = "select";
        id = attributes.getCount();
        resultType = "java.lang.Integer";
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ");
        sb.append(tableInfoWrapper.getTableInfo().getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
    }
}

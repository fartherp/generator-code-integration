/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.op.db.OpAttributes;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpDeleteElementGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpInsertElementGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpInsertSelectiveElementGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpSelectByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpUpdateByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.element.OpUpdateByPrimaryKeySelectiveElementGenerator;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class OpBaseXMLMapperGenerator extends AbstractXmlMapperGenerator<OpAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {
        this.addResultMapWithoutBLOBsElement(answer);

        this.addBaseColumnListElement(answer);

        addOpInsertElement(answer);

        addOpInsertSelectiveElement(answer);

        addOpUpdateElement(answer);

        addOpUpdateSelectiveElement(answer);

        addOpDeleteElement(answer);

        addOpSelectByPrimaryKeyElement(answer);
    }

    protected void addOpInsertElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOpInsertSelectiveElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpInsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOpUpdateElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOpUpdateSelectiveElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpUpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOpDeleteElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpDeleteElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOpSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<OpAttributes> elementGenerator = new OpSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

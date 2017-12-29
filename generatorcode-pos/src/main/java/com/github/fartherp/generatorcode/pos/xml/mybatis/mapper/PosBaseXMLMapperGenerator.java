/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosDeleteByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosFindPageElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosInsertBatchElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosInsertSelectiveElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosSaveElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosSelectByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosUpdateByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.element.PosUpdateSelectiveElementGenerator;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PosBaseXMLMapperGenerator extends AbstractXmlMapperGenerator<PosAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {

        addResultMapWithoutBLOBsElement(answer);

        addBaseColumnListElement(answer);

        addSelectElement(answer);

        addInsertElement(answer);

        addInsertSelective(answer);

        addInsertBatchElement(answer);

        addUpdateElement(answer);

        addUpdateSelectiveElement(answer);

        addDeleteElement(answer);

        addPpmsFindPageElement(answer);
    }

    protected void addSelectElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosSaveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertSelective(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosInsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertBatchElement(XmlElement parentElement) {
        if (rules.generateInsertBatch()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosInsertBatchElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateSelectiveElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosUpdateSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addDeleteElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosDeleteByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsFindPageElement(XmlElement parentElement) {
        if (rules.generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator<PosAttributes> elementGenerator = new PosFindPageElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

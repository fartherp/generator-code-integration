/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsDeleteElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsFindPageElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsGetElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsGetListElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsInsertBatchElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsInsertElementGenerator;
import com.github.fartherp.generatorcode.ppms.xml.mybatis.element.PpmsUpdateElementGenerator;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PpmsBaseXMLMapperGenerator extends AbstractXmlMapperGenerator<PPmsAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {
        addResultMapWithoutBLOBsElement(answer);

        addBaseColumnListElement(answer);

        addPpmsInsertElement(answer);

        addPpmsInsertBatchElement(answer);

        addPpmsUpdateElement(answer);

        addPpmsDeleteElement(answer);

        addPpmsGetElement(answer);

        addPpmsGetListElement(answer);

        addPpmsFindPageElement(answer);
    }

    protected void addPpmsInsertElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsUpdateElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsUpdateElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsDeleteElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsDeleteElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsGetElement(XmlElement parentElement) {
        if (rules.generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsGetElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsGetListElement(XmlElement parentElement) {
        if (rules.generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsGetListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsFindPageElement(XmlElement parentElement) {
        if (rules.generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsFindPageElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPpmsInsertBatchElement(XmlElement parentElement) {
        if (rules.generateInsertBatch()) {
            AbstractXmlElementGenerator<PPmsAttributes> elementGenerator = new PpmsInsertBatchElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

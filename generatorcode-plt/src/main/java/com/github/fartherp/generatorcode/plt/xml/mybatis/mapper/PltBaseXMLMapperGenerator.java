/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltInsertBatchElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltBaseColumnListElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltDeleteElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltInsertElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltInsertSelectiveElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltResultMapWithoutBLOBsElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltSelectByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltUpdateByPrimaryKeySelectiveElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltSelectAllByElementGenerator;
import com.github.fartherp.generatorcode.plt.xml.mybatis.element.PltUpdateByPrimaryKeyElementGenerator;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PltBaseXMLMapperGenerator extends AbstractXmlMapperGenerator<PltAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {
        this.addResultMapWithoutBLOBsElement(answer);

        this.addBaseColumnListElement(answer);

        addPltInsertElement(answer);

        // insertBatch
        addInsertBatchElement(answer);

        addPltInsertSelectiveElement(answer);

        addPltUpdateElement(answer);

        addPltUpdateSelectiveElement(answer);

        addPltDeleteElement(answer);

        // selectAll
        addSelectAllByElement(answer);

        addPltSelectByPrimaryKeyElement(answer);
    }

    protected void addResultMapWithoutBLOBsElement(XmlElement parentElement) {
        if (rules.generateBaseResultMap()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltResultMapWithoutBLOBsElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addBaseColumnListElement(XmlElement parentElement) {
        if (rules.generateBaseColumnList()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltBaseColumnListElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltInsertElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltInsertSelectiveElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltInsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltUpdateElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltUpdateSelectiveElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltUpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltDeleteElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltDeleteElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addPltSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectAllByElement(XmlElement parentElement) {
        if (rules.generateSelectAllByExample()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltSelectAllByElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addInsertBatchElement(XmlElement parentElement) {
        if (rules.generateInsertBatch()) {
            AbstractXmlElementGenerator<PltAttributes> elementGenerator = new PltInsertBatchElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

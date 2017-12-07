/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.XmlElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsDeleteElementGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsInsertElementGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsInsertSelectiveElementGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsSelectByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsUpdateByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.element.OsUpdateByPrimaryKeySelectiveElementGenerator;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class OsBaseXMLMapperGenerator extends AbstractXmlMapperGenerator<OsAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {
        this.addResultMapWithoutBLOBsElement(answer);

        this.addBaseColumnListElement(answer);

        addOsInsertElement(answer);

        addOsInsertSelectiveElement(answer);

        addOsUpdateElement(answer);

        addOsUpdateSelectiveElement(answer);

        addOsDeleteElement(answer);

        addOsSelectByPrimaryKeyElement(answer);
    }

    protected void addOsInsertElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsInsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOsInsertSelectiveElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsInsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOsUpdateElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsUpdateByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOsUpdateSelectiveElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsUpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOsDeleteElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsDeleteElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addOsSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<OsAttributes> elementGenerator = new OsSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

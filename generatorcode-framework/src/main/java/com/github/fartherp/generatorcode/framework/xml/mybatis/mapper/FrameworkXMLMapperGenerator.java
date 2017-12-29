/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.xml.mybatis.mapper;

import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.CountByElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.DeleteByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.InsertBatchElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.InsertElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.InsertSelectiveElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.SelectAllByElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.SelectByPrimaryKeyElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.UpdateByPrimaryKeySelectiveElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.UpdateByPrimaryKeyWithBLOBsElementGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.element.UpdateByPrimaryKeyWithoutBLOBsElementGenerator;
import com.github.fartherp.javaxml.XmlElement;

/**
 * Base基础XML的MAPPER类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class FrameworkXMLMapperGenerator extends AbstractXmlMapperGenerator<FrameworkAttributes> {

    /**
     * 获取SQL MAP元素
     * @param answer XmlElement
     */
    public void getSqlMapElement(XmlElement answer) {
        // resultMap
        addResultMapWithoutBLOBsElement(answer);
        addResultMapWithBLOBsElement(answer);

        // Base_Column_List
        addBaseColumnListElement(answer);
        addBlobColumnListElement(answer);

        // insert
        addInsertElement(answer);
        // insertBatch
        addInsertBatchElement(answer);
        // insertSelective
        addInsertSelectiveElement(answer);

        // deleteByPrimaryKey
        addDeleteByPrimaryKeyElement(answer);

        // selectAll
        addSelectAllByElement(answer);
        // selectByPrimaryKey
        addSelectByPrimaryKeyElement(answer);

        // count
        addCountByElement(answer);

        // updateByPrimaryKeySelective
        addUpdateByPrimaryKeySelectiveElement(answer);
        // updateByPrimaryKey
        addUpdateByPrimaryKeyWithoutBLOBsElement(answer);
        addUpdateByPrimaryKeyWithBLOBsElement(answer);
    }

    /**
     * 主键查询
     * @param parentElement XmlElement
     */
    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (rules.generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new SelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addSelectAllByElement(XmlElement parentElement) {
        if (rules.generateSelectAllByExample()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new SelectAllByElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    /**
     * 主键删除
     * @param parentElement XmlElement
     */
    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (rules.generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new DeleteByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    /**
     * 添加全部字段语句
     * @param parentElement XmlElement
     */
    protected void addInsertElement(XmlElement parentElement) {
        if (rules.generateInsert()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new InsertElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    /**
     * 添加全部字段语句
     * @param parentElement XmlElement
     */
    protected void addInsertBatchElement(XmlElement parentElement) {
        if (rules.generateInsertBatch()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new InsertBatchElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    /**
     * 添加部分字段
     * @param parentElement XmlElement
     */
    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (rules.generateInsertSelective()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new InsertSelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addCountByElement(XmlElement parentElement) {
        if (rules.generateCount()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new CountByElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    /**
     * 根据主键更新
     * @param parentElement XmlElement
     */
    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new UpdateByPrimaryKeySelectiveElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeyWithBLOBsElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeyWithBLOBs()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new UpdateByPrimaryKeyWithBLOBsElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsElement(XmlElement parentElement) {
        if (rules.generateUpdateByPrimaryKeyWithoutBLOBs()) {
            AbstractXmlElementGenerator<FrameworkAttributes> elementGenerator = new UpdateByPrimaryKeyWithoutBLOBsElementGenerator(false);
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}

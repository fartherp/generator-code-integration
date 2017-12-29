/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos;

import com.github.fartherp.codegenerator.api.MyBatisGenerator;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.spring.document.AbstractSpringXMLDocument;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.generatorcode.pos.db.PosTableMyBatis3Impl;
import com.github.fartherp.generatorcode.pos.xml.spring.docment.PosSpringXMLDocument;
import com.github.fartherp.javaxml.Document;

import java.util.List;

/**
 * PPMS 扩展添加spring xml
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PosGenerator extends MyBatisGenerator<PosAttributes> {

    protected AbstractSpringXMLDocument<PosAttributes> springXmlMapperGenerator;

    /**
     * 自动生成文件
     */
    public void generateFiles() {
        super.generateFiles();
        this.springXmlMapperGenerator = new PosSpringXMLDocument(context, tableInfoWrappers);
        List<Document> springDocument = springXmlMapperGenerator.getDocument();
        for (Document d : springDocument) {
            GeneratedXmlFile gxf1 = new GeneratedXmlFile(d, d.getName(),
                    "", context.getTargetPackage(), context.getXmlFormatter());
            generatedXmlFiles.add(gxf1);
        }
    }

    public TableInfoWrapper createTableInfoWrapper(CodeGenContext context) {
        return new PosTableMyBatis3Impl(context);
    }
}

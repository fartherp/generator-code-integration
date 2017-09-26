/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.xml.spring.document;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.Document;
import com.github.fartherp.codegenerator.xml.spring.document.AbstractSpringXMLDocument;
import com.github.fartherp.codegenerator.xml.spring.element.AbstractSpringXMLElement;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.generatorcode.ppms.xml.spring.element.PPmsSpringActionXMLElement;
import com.github.fartherp.generatorcode.ppms.xml.spring.element.PPmsSpringServletXMLElement;
import com.github.fartherp.generatorcode.ppms.xml.spring.element.PPmsSpringTxXMLElement;

import java.util.ArrayList;
import java.util.List;

/**
 * PPMS Spring generator
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PPmsSpringXMLDocument extends AbstractSpringXMLDocument<PPmsAttributes> {

    public PPmsSpringXMLDocument(CodeGenContext context, List<TableInfoWrapper<PPmsAttributes>> tableInfoWrapper) {
        super(context, tableInfoWrapper);
    }

    public List<Document> getDocument() {
        List<Document> documents = new ArrayList<Document>();

        addSpringTxXML(documents);

        addSpringActionXml(documents);

        addSpringServletXML(documents);

        return documents;
    }

    protected void addSpringTxXML(List<Document> documents) {
        AbstractSpringXMLElement generator = new PPmsSpringTxXMLElement("applicationContext-ppms-tx.xml", context,  tableInfoWrapper);
        documents.add(generator.getDocument());
    }

    protected void addSpringActionXml(List<Document> documents) {
        AbstractSpringXMLElement generator = new PPmsSpringActionXMLElement("applicationContext-ppms-action.xml", context, tableInfoWrapper);
        documents.add(generator.getDocument());
    }

    protected void addSpringServletXML(List<Document> documents) {
        AbstractSpringXMLElement generator = new PPmsSpringServletXMLElement("applicationContext-ppms-servlet.xml", context, tableInfoWrapper);
        documents.add(generator.getDocument());
    }
}

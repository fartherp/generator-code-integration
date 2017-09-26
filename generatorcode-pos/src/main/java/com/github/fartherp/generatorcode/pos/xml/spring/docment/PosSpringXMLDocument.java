/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.xml.spring.docment;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.Document;
import com.github.fartherp.codegenerator.xml.spring.document.AbstractSpringXMLDocument;
import com.github.fartherp.codegenerator.xml.spring.element.AbstractSpringXMLElement;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.generatorcode.pos.xml.spring.element.PosSpringActionXMLElement;
import com.github.fartherp.generatorcode.pos.xml.spring.element.PosSpringServletXMLElement;

import java.util.ArrayList;
import java.util.List;

/**
 * PPMS Spring generator
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PosSpringXMLDocument extends AbstractSpringXMLDocument<PosAttributes> {

    public PosSpringXMLDocument(CodeGenContext context, List<TableInfoWrapper<PosAttributes>> tableInfoWrapper) {
        super(context, tableInfoWrapper);
    }

    public List<Document> getDocument() {
        List<Document> documents = new ArrayList<Document>();

        addSpringActionXml(documents);

        addSpringServletXML(documents);

        return documents;
    }

    protected void addSpringActionXml(List<Document> documents) {
        AbstractSpringXMLElement generator = new PosSpringActionXMLElement("applicationContext-pos-ppms-action.xml", context, tableInfoWrapper);
        documents.add(generator.getDocument());
    }

    protected void addSpringServletXML(List<Document> documents) {
        AbstractSpringXMLElement generator = new PosSpringServletXMLElement("applicationContext-pos-ppms-servlet.xml", context, tableInfoWrapper);
        documents.add(generator.getDocument());
    }
}

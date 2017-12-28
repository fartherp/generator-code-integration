/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.xml.spring.element;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.config.SpringXMLConstants;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.codegenerator.xml.Attribute;
import com.github.fartherp.codegenerator.xml.XmlElement;
import com.github.fartherp.codegenerator.xml.spring.element.AbstractSpringXMLElement;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;

import java.util.List;

/**
 * PPMS tx Spring generator
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PPmsSpringTxXMLElement extends AbstractSpringXMLElement<PPmsAttributes> {

    public PPmsSpringTxXMLElement(String name, CodeGenContext context, List<TableInfoWrapper<PPmsAttributes>> ts) {
        super(name, context, ts);
    }

    public void getElement(XmlElement answer) {
        answer.addAttribute(new Attribute("\n\t\t\t\t" + SpringXMLConstants.XMLNS_SCHEMA_LOCATION,
                SpringXMLConstants.SPRING_BEANS + "\n\t\t\t\t" + SpringXMLConstants.SPRING_BEANS_XSD));

        for (TableInfoWrapper<PPmsAttributes> t : this.tableInfoWrappers) {
            PPmsAttributes attributes = t.getAttributes();
            XmlElement service = new XmlElement("bean");
            JavaTypeInfo serviceId = attributes.getService();
            service.addAttribute(new Attribute("id", JavaBeansUtils.getValidPropertyName(serviceId.getShortName())));
            JavaTypeInfo serviceImpl = attributes.getServiceImpl();
            service.addAttribute(new Attribute("class", serviceImpl.getFullyQualifiedName()));
            service.addAttribute(new Attribute("parent", "baseTransactionService"));

            JavaTypeInfo ppmsDao = attributes.getDao();
            String shortName = JavaBeansUtils.getValidPropertyName(ppmsDao.getShortName());
            XmlElement baseDao = new XmlElement("property", true);
            baseDao.addAttribute(new Attribute("name", "baseDao"));
            baseDao.addAttribute(new Attribute("ref", shortName));
            service.addElement(baseDao);

            XmlElement dao = new XmlElement("property", true);
            dao.addAttribute(new Attribute("name", shortName));
            dao.addAttribute(new Attribute("ref", shortName));
            service.addElement(dao);

            answer.addElement(service);
        }
    }
}

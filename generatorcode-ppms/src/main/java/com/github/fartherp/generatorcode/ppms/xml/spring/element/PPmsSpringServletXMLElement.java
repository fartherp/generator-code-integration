/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.xml.spring.element;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.config.SpringXMLConstants;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.codegenerator.xml.spring.element.AbstractSpringXMLElement;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.TextElement;
import com.github.fartherp.javaxml.XmlElement;

import java.util.List;

/**
 * PPMS servlet Spring generator
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PPmsSpringServletXMLElement extends AbstractSpringXMLElement<PPmsAttributes> {

    public PPmsSpringServletXMLElement(String name, CodeGenContext context, List<TableInfoWrapper<PPmsAttributes>> ts) {
        super(name, context, ts);
    }

    public void getElement(XmlElement answer) {
        answer.addAttribute(new Attribute("\n\t\t\t\t" + SpringXMLConstants.XMLNS_SCHEMA_LOCATION,
                SpringXMLConstants.SPRING_BEANS + "\n\t\t\t\t" + SpringXMLConstants.SPRING_BEANS_XSD));

        XmlElement mapping = new XmlElement("bean");
        mapping.addAttribute(new Attribute("class", "org.springframework.web.servlet.handler.SimpleUrlHandlerMapping"));

        XmlElement property = new XmlElement("property");
        property.addAttribute(new Attribute("name", "mappings"));
        mapping.addElement(property);

        XmlElement props = new XmlElement("props");
        for (TableInfoWrapper<PPmsAttributes> t : this.tableInfoWrappers) {
            PPmsAttributes attributes = t.getAttributes();
            JavaTypeInfo javaTypeInfo = attributes.getBo();
            String bo = JavaBeansUtils.getValidPropertyName(javaTypeInfo.getShortName());
            XmlElement prop = new XmlElement("prop");
            prop.addAttribute(new Attribute("key", "/" + bo + "/index.htm"));
            JavaTypeInfo action = attributes.getController();
            prop.addElement(new TextElement(JavaBeansUtils.getValidPropertyName(action.getShortName())));
            props.addElement(prop);
        }
        property.addElement(props);
        answer.addElement(mapping);
    }
}

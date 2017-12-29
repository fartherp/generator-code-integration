/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.xml.spring.element;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.config.SpringXMLConstants;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.codegenerator.xml.spring.element.AbstractSpringXMLElement;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.javacode.JavaTypeInfo;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.XmlElement;

import java.util.List;

/**
 * PPMS action Spring generator
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PosSpringActionXMLElement extends AbstractSpringXMLElement<PosAttributes> {

    public PosSpringActionXMLElement(String name, CodeGenContext context, List<TableInfoWrapper<PosAttributes>> ts) {
        super(name, context, ts);
    }

    public void getElement(XmlElement answer) {
        answer.addAttribute(new Attribute("\n\t\t\t\t" + SpringXMLConstants.XMLNS_SCHEMA_LOCATION,
                SpringXMLConstants.SPRING_BEANS + "\n\t\t\t\t" + SpringXMLConstants.SPRING_BEANS_XSD));

        for (TableInfoWrapper<PosAttributes> t : tableInfoWrappers) {
            PosAttributes attributes = t.getAttributes();
            XmlElement action = new XmlElement("bean");
            JavaTypeInfo actionBean = attributes.getController();
            action.addAttribute(new Attribute("id", JavaBeansUtils.getValidPropertyName(actionBean.getShortName())));
            action.addAttribute(new Attribute("class", actionBean.getFullyQualifiedName()));
            action.addAttribute(new Attribute("parent", "baseController"));

            answer.addElement(action);
        }
    }
}

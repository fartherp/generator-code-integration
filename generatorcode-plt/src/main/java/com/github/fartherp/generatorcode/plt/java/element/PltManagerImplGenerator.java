/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.element;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.Field;
import com.github.fartherp.codegenerator.java.JavaKeywords;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.java.JavaTypeInfoEnum;
import com.github.fartherp.codegenerator.java.Method;
import com.github.fartherp.codegenerator.java.TopLevelClass;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/13
 */
public class PltManagerImplGenerator extends AbstractJavaElementGenerator<PltAttributes> {
    public PltManagerImplGenerator(TableInfoWrapper<PltAttributes> tableInfoWrapper) {
        super(tableInfoWrapper);
    }

    public void prepareElement() {
        javaTypeInfo = attributes.getServiceImpl();
        superClass = new JavaTypeInfo("com.juzix.plt.manager.impl.BaseManagerImpl" + attributes.getPk());
        superInterfaces = new HashSet<JavaTypeInfo>();
        superInterfaces.add(attributes.getService());
    }

    public void dealElement(TopLevelClass topLevelClass) {
        JavaTypeInfo bo = attributes.getBo();
        topLevelClass.addImportedType(bo);

        JavaTypeInfo service = attributes.getService();
        /** 类注解@Repository */
        topLevelClass.addAnnotation("@Service(\""
                + JavaBeansUtils.getValidPropertyName(service.getShortName()) + "\")");
        topLevelClass.addImportedType(JavaTypeInfoEnum.SERVICE.getJavaTypeInfo());

        JavaTypeInfo mapper = attributes.getDao();
        Field field = new Field(JavaBeansUtils.getValidPropertyName(mapper.getShortName()), mapper);
        field.setJavaScope(JavaKeywords.PRIVATE);
        field.addAnnotation("@Resource");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(JavaTypeInfoEnum.RESOURCE.getJavaTypeInfo());
        topLevelClass.addImportedType(mapper);

        JavaTypeInfo baseMapper = new JavaTypeInfo("com.juzix.plt.dao.mapper.BaseMapper" + attributes.getPk());
        topLevelClass.addImportedType(baseMapper);

        Method getDaoMapper = new Method("getMapper");
        getDaoMapper.setJavaScope(JavaKeywords.PUBLIC);
        getDaoMapper.addBodyLine("return " + JavaBeansUtils.getValidPropertyName(mapper.getShortName()) + ";");
        getDaoMapper.setReturnType(baseMapper);
        topLevelClass.addMethod(getDaoMapper);
        topLevelClass.addImportedType(mapper);
    }
}

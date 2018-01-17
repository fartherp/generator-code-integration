/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.db;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.EmptyXMLMapperGenerator;
import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.generatorcode.framework.comment.FrameWorkCommentGenerator;
import com.github.fartherp.generatorcode.framework.java.file.FrameworkJavaGenerator;
import com.github.fartherp.generatorcode.framework.xml.mybatis.mapper.FrameworkXMLMapperGenerator;
import com.github.fartherp.javacode.CompilationUnit;
import com.github.fartherp.javaxml.Document;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class FrameWorkTableMyBatis3Impl extends TableInfoWrapper<FrameworkAttributes> {
    public FrameWorkTableMyBatis3Impl(CodeGenContext context) {
        super(context);
        this.javaModelGenerators = new FrameworkJavaGenerator(this);
        this.xmlMapperGenerator = new EmptyXMLMapperGenerator<FrameworkAttributes>();
        this.xmlMapperGenerator.setTableInfoWrapper(this);
        this.baseXmlMapperGenerator = new FrameworkXMLMapperGenerator();
        this.baseXmlMapperGenerator.setTableInfoWrapper(this);
        this.attributes = new FrameworkAttributes();
        this.commentGenerator = new FrameWorkCommentGenerator();
    }

    /** base XML内容生成类 */
    protected AbstractXmlMapperGenerator baseXmlMapperGenerator;

    public void getGeneratedXmlFiles(List<GeneratedXmlFile> answer) {
        FrameworkAttributes frameworkAttributes = this.attributes;
        // bo扩展类
        Document document = xmlMapperGenerator.getDocument();
        document.setModule("dao");
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                frameworkAttributes.getMyBatis3XmlMapperFileName(),
                frameworkAttributes.getXMLMapperPackage(),
                context.getTargetPackage(), context.getXmlFormatter());
        answer.add(gxf);
        // bo基础类
        Document baseDocument = baseXmlMapperGenerator.getDocument();
        baseDocument.setModule("dao");
        GeneratedXmlFile base = new GeneratedXmlFile(baseDocument,
                frameworkAttributes.getMyBatis3XmlMapperBaseFileName(),
                frameworkAttributes.getXMLMapperPackage(),
                context.getTargetPackage(), context.getXmlFormatter());
        answer.add(base);
    }

    public void getGeneratedJavaFiles(List<GeneratedJavaFile> answer, List<CompilationUnit> compilationUnits) {
        for (CompilationUnit compilationUnit : compilationUnits) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                    context.getTargetPackage(), Constant.UTF_8, context.getJavaFormatter());
            answer.add(gjf);
        }
    }
}

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.db;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.Document;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.EmptyXMLMapperGenerator;
import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.generatorcode.pos.comment.PPmsCommentGenerator;
import com.github.fartherp.generatorcode.pos.java.file.PosJavaGenerator;
import com.github.fartherp.generatorcode.pos.xml.mybatis.mapper.PosBaseXMLMapperGenerator;
import com.github.fartherp.javacode.CompilationUnit;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class PosTableMyBatis3Impl extends TableInfoWrapper<PosAttributes> {
    public PosTableMyBatis3Impl(CodeGenContext context) {
        super(context);
        this.xmlMapperGenerator = new EmptyXMLMapperGenerator<PosAttributes>();
        this.xmlMapperGenerator.setTableInfoWrapper(this);
        this.baseXmlMapperGenerator = new PosBaseXMLMapperGenerator();
        this.baseXmlMapperGenerator.setTableInfoWrapper(this);
        this.javaModelGenerators = new PosJavaGenerator(this);
        this.commentGenerator = new PPmsCommentGenerator();
        this.attributes = new PosAttributes();
    }

    /** base XML内容生成类 */
    protected AbstractXmlMapperGenerator baseXmlMapperGenerator;

    public void getGeneratedXmlFiles(List<GeneratedXmlFile> answer) {
        PosAttributes attribute = this.attributes;
        // bo扩展类
        Document document = xmlMapperGenerator.getDocument();
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                attribute.getMyBatis3XmlMapperFileName(),
                attribute.getXMLMapperPackage(),
                context.getTargetPackage(), context.getXmlFormatter());
        answer.add(gxf);
        // bo基础类
        Document baseDocument = baseXmlMapperGenerator.getDocument();
        GeneratedXmlFile base = new GeneratedXmlFile(baseDocument,
                attribute.getMyBatis3XmlMapperBaseFileName(),
                attribute.getXMLMapperPackage(),
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

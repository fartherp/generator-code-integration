/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op.db;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.generatorcode.op.comment.OpCommentGenerator;
import com.github.fartherp.generatorcode.op.java.file.OpJavaGenerator;
import com.github.fartherp.generatorcode.op.xml.mybatis.mapper.OpBaseXMLMapperGenerator;
import com.github.fartherp.javacode.CompilationUnit;
import com.github.fartherp.javaxml.Document;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class OpTableMyBatis3Impl extends TableInfoWrapper<OpAttributes> {
    public OpTableMyBatis3Impl(CodeGenContext context) {
        super(context);
        this.xmlMapperGenerator = new OpBaseXMLMapperGenerator();
        this.xmlMapperGenerator.setTableInfoWrapper(this);
        this.javaModelGenerators = new OpJavaGenerator(this);
        this.commentGenerator = new OpCommentGenerator();
        this.attributes = new OpAttributes();
    }

    public void getGeneratedXmlFiles(List<GeneratedXmlFile> answer) {
        OpAttributes pmsAttributes = this.attributes;
        // bo扩展类
        Document document = xmlMapperGenerator.getDocument();
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                pmsAttributes.getMyBatis3XmlMapperFileName(),
                pmsAttributes.getXMLMapperPackage(),
                context.getTargetPackage(), context.getXmlFormatter());
        answer.add(gxf);
    }

    public void getGeneratedJavaFiles(List<GeneratedJavaFile> answer, List<CompilationUnit> compilationUnits) {
        for (CompilationUnit compilationUnit : compilationUnits) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                    context.getTargetPackage(), Constant.UTF_8, context.getJavaFormatter());
            answer.add(gjf);
        }
    }
}

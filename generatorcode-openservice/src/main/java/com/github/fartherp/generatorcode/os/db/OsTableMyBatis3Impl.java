/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.db;

import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.AbstractXmlMapperGenerator;
import com.github.fartherp.codegenerator.xml.mybatis.mapper.EmptyXMLMapperGenerator;
import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.generatorcode.os.comment.OsCommentGenerator;
import com.github.fartherp.generatorcode.os.java.file.OsJavaGenerator;
import com.github.fartherp.generatorcode.os.xml.mybatis.mapper.OsBaseXMLMapperGenerator;
import com.github.fartherp.javacode.CompilationUnit;
import com.github.fartherp.javaxml.Document;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class OsTableMyBatis3Impl extends TableInfoWrapper<OsAttributes> {

    /** base XML内容生成类 */
    protected AbstractXmlMapperGenerator baseXmlMapperGenerator;

    public OsTableMyBatis3Impl(CodeGenContext context) {
        super(context);
        this.xmlMapperGenerator = new EmptyXMLMapperGenerator<OsAttributes>();
        this.xmlMapperGenerator.setTableInfoWrapper(this);
        this.baseXmlMapperGenerator = new OsBaseXMLMapperGenerator();
        this.baseXmlMapperGenerator.setTableInfoWrapper(this);
        this.javaModelGenerators = new OsJavaGenerator(this);
        this.commentGenerator = new OsCommentGenerator();
        this.attributes = new OsAttributes();
    }

    public void getGeneratedXmlFiles(List<GeneratedXmlFile> answer) {
        OsAttributes pmsAttributes = this.attributes;
        // bo扩展类
        Document document = xmlMapperGenerator.getDocument();
        document.setModule("dao");
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                pmsAttributes.getMyBatis3XmlMapperFileName(),
                pmsAttributes.getXMLMapperPackage(),
                context.getTargetPackage(), context.getXmlFormatter());
        answer.add(gxf);

        // bo基础类
        Document baseDocument = baseXmlMapperGenerator.getDocument();
        baseDocument.setModule("dao");
        GeneratedXmlFile base = new GeneratedXmlFile(baseDocument,
                pmsAttributes.getMyBatis3XmlMapperBaseFileName(),
                pmsAttributes.getXMLMapperPackage(),
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

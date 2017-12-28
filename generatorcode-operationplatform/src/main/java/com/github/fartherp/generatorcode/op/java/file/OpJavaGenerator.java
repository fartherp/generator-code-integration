/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.op.db.OpAttributes;
import com.github.fartherp.generatorcode.op.java.element.OpBaseBoGenerator;
import com.github.fartherp.generatorcode.op.java.element.OpMapperGenerator;
import com.github.fartherp.generatorcode.op.java.element.OpServiceGenerator;
import com.github.fartherp.generatorcode.op.java.element.OpServiceImplGenerator;
import com.github.fartherp.javacode.CompilationUnit;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class OpJavaGenerator extends AbstractJavaGenerator<OpAttributes> {
    public OpJavaGenerator(TableInfoWrapper<OpAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<OpAttributes> extendBo = new OpBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<OpAttributes> mapper = new OpMapperGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(mapper, answers);

        AbstractJavaElementGenerator<OpAttributes> service = new OpServiceGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<OpAttributes> serverImpl = new OpServiceImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);
    }
}

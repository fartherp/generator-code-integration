/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.ppms.db.PPmsAttributes;
import com.github.fartherp.generatorcode.ppms.java.element.PPmsActionGenerator;
import com.github.fartherp.generatorcode.ppms.java.element.PPmsBaseBoGenerator;
import com.github.fartherp.generatorcode.ppms.java.element.PPmsDaoGenerator;
import com.github.fartherp.generatorcode.ppms.java.element.PPmsServiceGenerator;
import com.github.fartherp.generatorcode.ppms.java.element.PPmsServiceImplGenerator;
import com.github.fartherp.javacode.CompilationUnit;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class PPmsJavaGenerator extends AbstractJavaGenerator<PPmsAttributes> {
    public PPmsJavaGenerator(TableInfoWrapper<PPmsAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<PPmsAttributes> extendBo = new PPmsBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<PPmsAttributes> dao = new PPmsDaoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(dao, answers);

        AbstractJavaElementGenerator<PPmsAttributes> service = new PPmsServiceGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<PPmsAttributes> serverImpl = new PPmsServiceImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);

        AbstractJavaElementGenerator<PPmsAttributes> action = new PPmsActionGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(action, answers);
    }
}

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;
import com.github.fartherp.generatorcode.framework.java.element.FrameworkBaseBoGenerator;
import com.github.fartherp.generatorcode.framework.java.element.FrameworkExtendsBoGenerator;
import com.github.fartherp.generatorcode.framework.java.element.FrameworkMapperGenerator;
import com.github.fartherp.generatorcode.framework.java.element.FrameworkServiceGenerator;
import com.github.fartherp.generatorcode.framework.java.element.FrameworkServiceImplGenerator;
import com.github.fartherp.javacode.CompilationUnit;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class FrameworkJavaGenerator extends AbstractJavaGenerator<FrameworkAttributes> {
    public FrameworkJavaGenerator(TableInfoWrapper<FrameworkAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<FrameworkAttributes> baseBo = new FrameworkBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(baseBo, answers);

        AbstractJavaElementGenerator<FrameworkAttributes> extendBo = new FrameworkExtendsBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<FrameworkAttributes> mapper = new FrameworkMapperGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(mapper, answers);

        AbstractJavaElementGenerator<FrameworkAttributes> service = new FrameworkServiceGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<FrameworkAttributes> serverImpl = new FrameworkServiceImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);
    }
}

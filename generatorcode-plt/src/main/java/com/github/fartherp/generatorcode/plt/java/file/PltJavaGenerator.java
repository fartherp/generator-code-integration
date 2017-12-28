/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.generatorcode.plt.java.element.PltBaseBoGenerator;
import com.github.fartherp.generatorcode.plt.java.element.PltManagerGenerator;
import com.github.fartherp.generatorcode.plt.java.element.PltManagerImplGenerator;
import com.github.fartherp.generatorcode.plt.java.element.PltMapperGenerator;
import com.github.fartherp.generatorcode.plt.java.element.PltMapperImplGenerator;
import com.github.fartherp.javacode.CompilationUnit;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class PltJavaGenerator extends AbstractJavaGenerator<PltAttributes> {
    public PltJavaGenerator(TableInfoWrapper<PltAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<PltAttributes> extendBo = new PltBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<PltAttributes> mapper = new PltMapperGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(mapper, answers);

        AbstractJavaElementGenerator<PltAttributes> mapperImple = new PltMapperImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(mapperImple, answers);

        AbstractJavaElementGenerator<PltAttributes> service = new PltManagerGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<PltAttributes> serverImpl = new PltManagerImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);
    }
}

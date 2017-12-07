/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.CompilationUnit;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.os.db.OsAttributes;
import com.github.fartherp.generatorcode.os.java.element.OsBaseBoGenerator;
import com.github.fartherp.generatorcode.os.java.element.OsMapperGenerator;
import com.github.fartherp.generatorcode.os.java.element.OsServiceGenerator;
import com.github.fartherp.generatorcode.os.java.element.OsServiceImplGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class OsJavaGenerator extends AbstractJavaGenerator<OsAttributes> {
    public OsJavaGenerator(TableInfoWrapper<OsAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<OsAttributes> extendBo = new OsBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<OsAttributes> mapper = new OsMapperGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(mapper, answers);

        AbstractJavaElementGenerator<OsAttributes> service = new OsServiceGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<OsAttributes> serverImpl = new OsServiceImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);
    }
}

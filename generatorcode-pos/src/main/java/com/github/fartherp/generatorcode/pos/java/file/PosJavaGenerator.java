/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.java.file;

import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.codegenerator.java.CompilationUnit;
import com.github.fartherp.codegenerator.java.element.AbstractJavaElementGenerator;
import com.github.fartherp.codegenerator.java.file.AbstractJavaGenerator;
import com.github.fartherp.generatorcode.pos.db.PosAttributes;
import com.github.fartherp.generatorcode.pos.java.element.PosActionGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosBaseBoGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosDaoGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosPageVoGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosServiceGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosServiceImplGenerator;
import com.github.fartherp.generatorcode.pos.java.element.PosVoGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/10
 */
public class PosJavaGenerator extends AbstractJavaGenerator<PosAttributes> {
    public PosJavaGenerator(TableInfoWrapper<PosAttributes> t) {
        super(t);
    }

    public void getJavaFile(List<CompilationUnit> answers) {
        AbstractJavaElementGenerator<PosAttributes> extendBo = new PosBaseBoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(extendBo, answers);

        AbstractJavaElementGenerator<PosAttributes> pageVo = new PosPageVoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(pageVo, answers);

        AbstractJavaElementGenerator<PosAttributes> vo = new PosVoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(vo, answers);

        AbstractJavaElementGenerator<PosAttributes> dao = new PosDaoGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(dao, answers);

        AbstractJavaElementGenerator<PosAttributes> service = new PosServiceGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(service, answers);

        AbstractJavaElementGenerator<PosAttributes> serverImpl = new PosServiceImplGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(serverImpl, answers);

        AbstractJavaElementGenerator<PosAttributes> action = new PosActionGenerator(tableInfoWrapper);
        initializeAndExecuteGenerator(action, answers);
    }
}

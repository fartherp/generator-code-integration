/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.framework;

import com.github.fartherp.codegenerator.api.MyBatisGenerator;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.generatorcode.framework.db.FrameWorkTableMyBatis3Impl;
import com.github.fartherp.generatorcode.framework.db.FrameworkAttributes;

/**
 * MyBatis XML及java类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class FrameworkGenerator extends MyBatisGenerator<FrameworkAttributes> {

    public FrameworkGenerator() {
        super();
    }

    public TableInfoWrapper createTableInfoWrapper(CodeGenContext codeGenContext) {
        return new FrameWorkTableMyBatis3Impl(context);
    }
}

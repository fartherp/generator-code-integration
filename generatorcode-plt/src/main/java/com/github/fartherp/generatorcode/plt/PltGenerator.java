/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt;

import com.github.fartherp.codegenerator.api.MyBatisGenerator;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.generatorcode.plt.db.PltTableMyBatis3Impl;

/**
 * PLT
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PltGenerator extends MyBatisGenerator<PltAttributes> {

    /**
     * 自动生成文件
     */
    public void generateFiles() {
        super.generateFiles();
    }

    public TableInfoWrapper createTableInfoWrapper(CodeGenContext context) {
        return new PltTableMyBatis3Impl(context);
    }
}

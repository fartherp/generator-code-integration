/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op;

import com.github.fartherp.codegenerator.api.MyBatisGenerator;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.generatorcode.op.db.OpAttributes;
import com.github.fartherp.generatorcode.op.db.OpTableMyBatis3Impl;

/**
 * PLT
 * Author: CK.
 * Date: 2015/6/6.
 */
public class OpGenerator extends MyBatisGenerator<OpAttributes> {

    /**
     * 自动生成文件
     */
    public void generateFiles() {
        super.generateFiles();
    }

    public TableInfoWrapper createTableInfoWrapper(CodeGenContext context) {
        return new OpTableMyBatis3Impl(context);
    }

    @Override
    public String dealTableName(String tableName) {
        String[] array = tableName.replaceFirst("tb_", "")
                .replaceFirst("td_", "")
                .replaceFirst("t_", "")
                .split("_");
        StringBuilder s = new StringBuilder();
        for (String a : array) {
            s.append(a.substring(0, 1).toUpperCase() + a.substring(1, a.length()));
        }

        return s.toString();
    }
}

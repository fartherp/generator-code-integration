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

    public String dealTableName(String tableName) {
        if (tableName.indexOf("tb_") == 0) {
            tableName = tableName.replaceFirst("tb_", "");
        } else if (tableName.indexOf("td_") == 0) {
            tableName = tableName.replaceFirst("td_", "");
        } else if (tableName.indexOf("t_") == 0) {
            tableName = tableName.replaceFirst("t_", "");
        }
        String[] array = tableName.split("_");
        StringBuilder s = new StringBuilder();
        for (String a : array) {
            s.append(a.substring(0, 1).toUpperCase() + a.substring(1, a.length()));
        }

        return s.toString();
    }

    @Override
    public String dealColumnName(String columnName) {
        if (columnName.indexOf("n_") == 0) {
            columnName = columnName.replaceFirst("n_", "");
        } else if (columnName.indexOf("c_") == 0) {
            columnName = columnName.replaceFirst("c_", "");
        } else if (columnName.indexOf("t_") == 0) {
            columnName = columnName.replaceFirst("t_", "");
        }
        String[] array = columnName.split("_");
        StringBuilder s = new StringBuilder();
        s.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            s.append(array[i].substring(0, 1).toUpperCase() + array[i].substring(1, array[i].length()));
        }

        return s.toString();
    }
}

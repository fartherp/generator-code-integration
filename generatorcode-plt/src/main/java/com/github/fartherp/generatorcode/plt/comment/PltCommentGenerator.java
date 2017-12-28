/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.comment;

import com.github.fartherp.codegenerator.api.comment.DefaultCommentGenerator;
import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.framework.common.util.DateUtil;
import com.github.fartherp.javacode.CompilationUnit;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.InnerClass;
import com.github.fartherp.javacode.Method;
import com.github.fartherp.javacode.Parameter;

import java.util.Date;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/15
 */
public class PltCommentGenerator extends DefaultCommentGenerator {
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine(" * Copyright (C) " + DateUtil.year(new Date())
                + " juzix, Inc. All Rights Reserved.");
        compilationUnit.addFileCommentLine(" */");
        compilationUnit.addFileCommentLine("");
    }

    public void addFieldComment(Field field, TableInfoWrapper tableInfoWrapper, ColumnInfo columnInfo) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(columnInfo.getComment());
        field.addJavaDocLine(sb.toString());

        field.addJavaDocLine(" */");
    }

    public void addClassComment(InnerClass innerClass, TableInfoWrapper tableInfoWrapper) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        sb.append(tableInfoWrapper.getTableInfo().getRemarks());
        innerClass.addJavaDocLine(sb.toString());

        innerClass.addJavaDocLine(" */");
    }

    public void addGeneralMethodComment(Method method, TableInfoWrapper tableInfoWrapper) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");
        sb.append(" * This method corresponds to the database table ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    public void addGetterComment(Method method, TableInfoWrapper tableInfoWrapper, ColumnInfo columnInfo) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");
        sb.append(" * This method returns the value of the database column ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        sb.append('.');
        sb.append(columnInfo.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" *");

        sb.setLength(0);
        sb.append(" * @return the value of ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        sb.append('.');
        sb.append(columnInfo.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    public void addSetterComment(Method method, TableInfoWrapper tableInfoWrapper, ColumnInfo columnInfo) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");
        sb.append(" * This method sets the value of the database column ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        sb.append('.');
        sb.append(columnInfo.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" *");

        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" the value for ");
        sb.append(tableInfoWrapper.getTableInfo().getFullyQualifiedTableNameAtRuntime());
        sb.append('.');
        sb.append(columnInfo.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }
}

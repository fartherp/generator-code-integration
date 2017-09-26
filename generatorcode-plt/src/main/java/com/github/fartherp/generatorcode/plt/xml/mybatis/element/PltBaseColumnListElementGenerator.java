/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt.xml.mybatis.element;

import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.MyBatis3FormattingUtils;
import com.github.fartherp.codegenerator.xml.TextElement;
import com.github.fartherp.codegenerator.xml.mybatis.element.AbstractXmlElementGenerator;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;

import java.util.Iterator;

/**
 * 基础列元素
 * Author: CK.
 * Date: 2015/6/6.
 */
public class PltBaseColumnListElementGenerator extends AbstractXmlElementGenerator<PltAttributes> {

    public void prepareXmlElement() {
        name = "sql";
        id = attributes.getDao().getShortName() + "_" + attributes.getBaseColumnList();
    }

    public void dealElements() {
        StringBuilder sb = new StringBuilder();
        Iterator<ColumnInfo> iter = tableInfoWrapper.getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtils.getSelectListPhrase(iter.next()));

            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 120) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }
    }
}

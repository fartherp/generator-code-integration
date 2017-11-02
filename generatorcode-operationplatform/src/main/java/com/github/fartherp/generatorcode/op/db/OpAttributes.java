/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.op.db;

import com.github.fartherp.codegenerator.db.AbstractAttributes;
import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.java.Field;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/20
 */
public class OpAttributes extends AbstractAttributes {

    private JavaTypeInfo bo;

    private JavaTypeInfo dao;

    private JavaTypeInfo daoImpl;

    private JavaTypeInfo service;

    private JavaTypeInfo serviceImpl;

    private String baseRecord;

    private String beanPackage;

    private String XMLMapperPackage;

    private String daoPackage;

    private String daoImplPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String XmlMapperFileName;

    private String pk;

    public void calculateModelAttributes() {
        String pakkage = this.calculateSqlMapPackage();

        setBaseRecord(pakkage);

        setXMLMapperPackage();

        setBeanPackage();
        setDaoPackage();
        setDaoImplPackage();
        setServicePackage();
        setServiceImplPackage();

        setBo();
        setPk();
        setDao();
        setDaoImpl();
        setService();
        setServiceImpl();
    }

    public String calculateSqlMapPackage() {
        return context.getTargetPackage();
    }

    /**
     * 计算XML属性
     */
    public void calculateXmlAttributes() {
        // 计算生成UserMapper.xml文件名
        String fileXMLMapperName = calculateMyBatis3XmlMapperFileName();
        setMyBatis3XmlMapperFileName(fileXMLMapperName);
    }

    public void setXMLMapperPackage() {
        this.XMLMapperPackage = "mapper." + tableInfo.getDomainObjectSubPackage();
    }

    public String getXMLMapperPackage() {
        return this.XMLMapperPackage;
    }

    public String getNamespace() {
        return baseRecord + ".dao." + tableInfo.getDomainObjectName() + "Mapper";
    }

    public void setBeanPackage() {
        this.beanPackage = baseRecord + ".domain." + tableInfo.getDomainObjectName();
    }

    public void setDaoPackage() {
        this.daoPackage = baseRecord + ".dao." + tableInfo.getDomainObjectName() + "Mapper";
    }

    public void setDaoImplPackage() {
        this.daoImplPackage = baseRecord + ".dao.impl." + tableInfo.getDomainObjectName() + "MapperImpl";
    }

    public void setServicePackage() {
        this.servicePackage = baseRecord + ".service." + tableInfo.getDomainObjectName() + "Service";
    }

    public void setServiceImplPackage() {
        this.serviceImplPackage = baseRecord + ".service.impl." + tableInfo.getDomainObjectName() + "ServiceImpl";
    }

    public void setBaseRecord(String baseRecord) {
        this.baseRecord = baseRecord;
    }

    public void setMyBatis3XmlMapperFileName(String mybatis3XmlMapperFileName) {
        this.XmlMapperFileName = mybatis3XmlMapperFileName;
    }

    public String getMyBatis3XmlMapperFileName() {
        return XmlMapperFileName;
    }

    public void setBo() {
        this.bo = new JavaTypeInfo(beanPackage);
    }

    public void setDao() {
        this.dao = new JavaTypeInfo(daoPackage);
    }

    public void setDaoImpl() {
        this.daoImpl = new JavaTypeInfo(daoImplPackage);
    }

    public void setService() {
        this.service = new JavaTypeInfo(servicePackage);
    }

    public void setServiceImpl() {
        this.serviceImpl = new JavaTypeInfo(serviceImplPackage);
    }

    public JavaTypeInfo getBo() {
        return bo;
    }

    public String getBaseRecord() {
        return baseRecord;
    }

    public String getRecordWithBLOBsType() {
        return null;
    }

    public JavaTypeInfo getDao() {
        return dao;
    }

    public JavaTypeInfo getDaoImpl() {
        return daoImpl;
    }

    public JavaTypeInfo getService() {
        return service;
    }

    public JavaTypeInfo getServiceImpl() {
        return serviceImpl;
    }

    public void setPk() {
        List<ColumnInfo> pkList = tableInfoWrapper.getPrimaryKeyColumns();
        StringBuilder sb = new StringBuilder();
        sb.append('<');
        sb.append(this.bo.getShortName());
        sb.append(", ");
        boolean pk = pkList.size() > 1;
        if (pk) {
            sb.append("HashMap");
        } else {
            for (ColumnInfo pkColumnInfo : pkList) {
                Field field = JavaBeansUtils.getJavaBeansField(pkColumnInfo, tableInfoWrapper);
                sb.append(field.getType().getShortName());
            }
        }
        sb.append('>');
        this.pk = sb.toString();
    }

    public String getPk() {
        return pk;
    }
}

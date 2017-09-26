/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.ppms.db;

import com.github.fartherp.codegenerator.db.AbstractAttributes;
import com.github.fartherp.codegenerator.java.JavaTypeInfo;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/20
 */
public class PPmsAttributes extends AbstractAttributes {

    private JavaTypeInfo bo;

    private JavaTypeInfo dao;

    private JavaTypeInfo service;

    private JavaTypeInfo serviceImpl;

    private JavaTypeInfo action;

    private String baseRecord;

    private String beanPackage;

    private String XMLMapperPackage;

    private String daoPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String actionPackage;

    private String XmlMapperFileName;

    public void calculateModelAttributes() {
        String pakkage = calculateSqlMapPackage();

        StringBuilder sb = new StringBuilder();
        sb.append(pakkage);
        setBaseRecord(sb.toString());

        setXMLMapperPackage();

        setBeanPackage();
        setDaoPackage();
        setServicePackage();
        setServiceImplPackage();
        setActionPackage();

        setBo();
        setDao();
        setService();
        setServiceImpl();
        setAction();
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
        return this.daoPackage;
    }

    public void setBeanPackage() {
        this.beanPackage = baseRecord + ".bo." + tableInfo.getDomainObjectName();
    }

    public void setDaoPackage() {
        this.daoPackage = baseRecord + ".dao." + tableInfo.getDomainObjectName() + "Dao";
    }

    public void setServicePackage() {
        this.servicePackage = baseRecord + ".service." + tableInfo.getDomainObjectName() + "Service";
    }

    public void setServiceImplPackage() {
        this.serviceImplPackage = baseRecord + ".service.impl." + tableInfo.getDomainObjectName() + "ServiceImpl";
    }

    public void setActionPackage() {
        this.actionPackage = baseRecord + ".action." + tableInfo.getDomainObjectName() + "Action";
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

    public void setService() {
        this.service = new JavaTypeInfo(servicePackage);
    }

    public void setServiceImpl() {
        this.serviceImpl = new JavaTypeInfo(serviceImplPackage);
    }

    public void setAction() {
        this.action = new JavaTypeInfo(actionPackage);
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

    public JavaTypeInfo getService() {
        return service;
    }

    public JavaTypeInfo getServiceImpl() {
        return serviceImpl;
    }

    public JavaTypeInfo getController() {
        return action;
    }
}

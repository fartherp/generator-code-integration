/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.os.db;

import com.github.fartherp.codegenerator.db.AbstractAttributes;
import com.github.fartherp.codegenerator.db.ColumnInfo;
import com.github.fartherp.codegenerator.util.JavaBeansUtils;
import com.github.fartherp.javacode.Field;
import com.github.fartherp.javacode.JavaTypeInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/20
 */
public class OsAttributes extends AbstractAttributes {

    private JavaTypeInfo baseBo;

    private JavaTypeInfo bo;

    private JavaTypeInfo dao;

    private JavaTypeInfo daoImpl;

    private JavaTypeInfo service;

    private JavaTypeInfo serviceImpl;

    private String baseRecord;

    private String baseBeanPackage;

    private String beanPackage;

    private String XMLMapperPackage;

    private String daoPackage;

    private String daoImplPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String XmlMapperFileName;

    private String XmlMapperBaseFileName;

    private String pk;

    public void calculateModelAttributes() {
        String pakkage = this.calculateSqlMapPackage();

        setBaseRecord(pakkage);

        setXMLMapperPackage();

        setBaseBeanPackage();
        setBeanPackage();
        setDaoPackage();
        setDaoImplPackage();
        setServicePackage();
        setServiceImplPackage();

        setBaseBo();
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
        // 计算生成BaseUserMapper.xml文件名
        setMyBatis3XmlMapperBaseFileName("Base" + fileXMLMapperName);
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

    public void setBaseBeanPackage() {
        this.baseBeanPackage = baseRecord + ".pojo." + tableInfo.getDomainObjectName() + "Base";
    }

    public void setBeanPackage() {
        this.beanPackage = baseRecord + ".pojo." + tableInfo.getDomainObjectName();
    }

    public void setDaoPackage() {
        this.daoPackage = baseRecord + ".dao." + tableInfo.getDomainObjectName() + "Mapper";
    }

    public void setDaoImplPackage() {
        this.daoImplPackage = baseRecord + ".dao.impl." + tableInfo.getDomainObjectName() + "MapperImpl";
    }

    public void setServicePackage() {
        this.servicePackage = baseRecord + ".manager." + tableInfo.getDomainObjectName() + "Manager";
    }

    public void setServiceImplPackage() {
        this.serviceImplPackage = baseRecord + ".manager.impl." + tableInfo.getDomainObjectName() + "ManagerImpl";
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

    public void setMyBatis3XmlMapperBaseFileName(String baseMybatis3XmlMapperFileName) {
        this.XmlMapperBaseFileName = baseMybatis3XmlMapperFileName;
    }

    public String getMyBatis3XmlMapperBaseFileName() {
        return this.XmlMapperBaseFileName;
    }

    public void setBaseBo() {
        this.baseBo = new JavaTypeInfo(baseBeanPackage);
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

    public JavaTypeInfo getBaseBo() {
        return baseBo;
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

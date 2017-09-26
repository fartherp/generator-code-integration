/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.pos.db;

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
public class PosAttributes extends AbstractAttributes {

    private JavaTypeInfo bo;

    private JavaTypeInfo vo;

    private JavaTypeInfo pageVo;

    private JavaTypeInfo dao;

    private JavaTypeInfo sqlMapDao;

    private JavaTypeInfo service;

    private JavaTypeInfo genericService;

    private JavaTypeInfo serviceImpl;

    private JavaTypeInfo genericSqlMapServiceImpl;

    private JavaTypeInfo action;

    private String baseRecord;

    private String beanPackage;

    private String voPagePackage;

    private String voPackage;

    private String XMLMapperPackage;

    private String daoPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String actionPackage;

    private String XmlMapperFileName;

    private String XmlMapperBaseFileName;

    private String pk;

    public void calculateModelAttributes() {
        String pakkage = calculateSqlMapPackage();

        setBaseRecord(pakkage);

        setXMLMapperPackage();

        setBeanPackage();
        setVoPagePackage();
        setVoPackage();
        setDaoPackage();
        setServicePackage();
        setServiceImplPackage();
        setActionPackage();

        setBo();
        setPk();
        setPageVo();
        setVo();
        setSqlMapDao();
        setDao();
        setGenericService();
        setService();
        setGenericSqlMapServiceImpl();
        setServiceImpl();
        setAction();
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
        return this.daoPackage;
    }

    public void setBeanPackage() {
        this.beanPackage = baseRecord + ".po." + tableInfo.getDomainObjectName();
    }

    public void setVoPagePackage() {
        this.voPagePackage = baseRecord + ".vo." + tableInfo.getDomainObjectName() + "PageVo";
    }

    public void setVoPackage() {
        this.voPackage = baseRecord + ".vo." + tableInfo.getDomainObjectName() + "Vo";
    }

    public void setDaoPackage() {
        this.daoPackage = baseRecord + ".dao." + tableInfo.getDomainObjectName() + "Dao";
    }

    public void setServicePackage() {
        this.servicePackage = baseRecord + ".service." + tableInfo.getDomainObjectName() + "TransactionService";
    }

    public void setServiceImplPackage() {
        this.serviceImplPackage = baseRecord + ".service.impl." + tableInfo.getDomainObjectName() + "TransactionServiceImpl";
    }

    public void setActionPackage() {
        this.actionPackage = baseRecord + ".action." + tableInfo.getDomainObjectName() + "Action";
    }

    public void setBaseRecord(String baseRecord) {
        this.baseRecord = baseRecord;
    }

    public void setMyBatis3XmlMapperBaseFileName(String baseMybatis3XmlMapperFileName) {
        this.XmlMapperBaseFileName = baseMybatis3XmlMapperFileName;
    }

    public String getMyBatis3XmlMapperBaseFileName() {
        return this.XmlMapperBaseFileName;
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

    public void setPageVo() {
        this.pageVo = new JavaTypeInfo(voPagePackage);
    }

    public void setVo() {
        this.vo = new JavaTypeInfo(voPackage);
    }

    public void setDao() {
        this.dao = new JavaTypeInfo(daoPackage);
    }

    public void setSqlMapDao() {
        this.sqlMapDao = new JavaTypeInfo("com.zrj.pay.pos.ppms.dao.SqlMapDao" + getPk());
    }

    public void setService() {
        this.service = new JavaTypeInfo(servicePackage);
    }

    public void setGenericService() {
        this.genericService = new JavaTypeInfo("com.zrj.pay.pos.ppms.tx.GenericService" + getPk());
    }

    public void setServiceImpl() {
        this.serviceImpl = new JavaTypeInfo(serviceImplPackage);
    }

    public void setGenericSqlMapServiceImpl() {
        this.genericSqlMapServiceImpl = new JavaTypeInfo("com.zrj.pay.pos.ppms.tx.GenericSqlMapServiceImpl" + getPk());
    }

    public void setAction() {
        this.action = new JavaTypeInfo(actionPackage);
    }

    public JavaTypeInfo getBo() {
        return bo;
    }

    public JavaTypeInfo getPageVo() {
        return pageVo;
    }

    public JavaTypeInfo getVo() {
        return vo;
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

    public JavaTypeInfo getSqlMapDao() {
        return sqlMapDao;
    }

    public JavaTypeInfo getService() {
        return service;
    }

    public JavaTypeInfo getGenericService() {
        return genericService;
    }

    public JavaTypeInfo getServiceImpl() {
        return serviceImpl;
    }

    public JavaTypeInfo getGenericSqlMapServiceImpl() {
        return genericSqlMapServiceImpl;
    }

    public JavaTypeInfo getController() {
        return action;
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

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.generatorcode.plt;

import com.github.fartherp.codegenerator.api.MyBatisGenerator;
import com.github.fartherp.codegenerator.api.file.GeneratedJavaFile;
import com.github.fartherp.codegenerator.api.file.GeneratedXmlFile;
import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.codegenerator.config.SpringXMLConstants;
import com.github.fartherp.codegenerator.config.XmlConstants;
import com.github.fartherp.codegenerator.db.TableInfoWrapper;
import com.github.fartherp.framework.common.util.FileUtilies;
import com.github.fartherp.generatorcode.plt.db.PltAttributes;
import com.github.fartherp.generatorcode.plt.db.PltTableMyBatis3Impl;
import com.github.fartherp.javaxml.Attribute;
import com.github.fartherp.javaxml.Document;
import com.github.fartherp.javaxml.TextElement;
import com.github.fartherp.javaxml.XmlElement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

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
        // 生成java，mybatis xml文件
        super.generateFiles();
    }

    protected void write() {
        for (GeneratedJavaFile gxf : generatedJavaFiles) {
            // 目标文件
            File targetFile = FileUtilies.getDirectory(gxf.getModule() + ".main.java." + gxf.getTargetPackage(), gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
        }

        for (GeneratedXmlFile gxf : generatedXmlFiles) {
            // 目标文件
            File targetFile = FileUtilies.getDirectory(gxf.getModule() + ".main.resources." + gxf.getTargetPackage(), gxf.getFileName(), context.getOut());
            // 目标内容
            String source = gxf.getFormattedContent();
            // 输出流写文件
            FileUtilies.writeFile(targetFile, source, "UTF-8");
        }

        if (1 == context.getIfGenProject()) {
            return;
        }

        String resources = this.getClass().getResource("/").getPath();
        try {
            final File root = new File(resources, "plt_project");
            FileUtils.copyDirectory(root, new File(context.getOut()), new FileFilter() {
                public boolean accept(File pathname) {
                    // 自身不复制
                    return !pathname.getAbsolutePath().equals(root.getAbsolutePath());
                }
            });
        } catch (IOException e) {
            // do nothing
            e.printStackTrace();
        }

        writeProfiles("client", "dao", "manager", "demo");
        writePackageInfo("client", "common");

        writeDemoResources("manager", "demo");

        writeSlice("common", "error_code");

        writeGradle();
    }

    public void writeGradle() {
        File settings = FileUtilies.getDirectory("", "settings.gradle", context.getOut());
        StringBuilder sb = new StringBuilder();
        sb.append("rootProject.name = '" + context.getProjectName() + "-ice-service' \n");
        sb.append("\n");
        sb.append("include ':slice_src' \n");
        sb.append("project(':slice_src').projectDir = new File('slice_src') \n");
        sb.append("\n");
        sb.append("include ':slice_gen' \n");
        sb.append("project(':slice_gen').projectDir = new File('slice_gen') \n");
        sb.append("\n");
        sb.append("include ':common' \n");
        sb.append("project(':common').projectDir = new File('common') \n");
        sb.append("\n");
        sb.append("include ':pojo' \n");
        sb.append("project(':pojo').projectDir = new File('pojo') \n");
        sb.append("\n");
        sb.append("include ':dao' \n");
        sb.append("project(':dao').projectDir = new File('dao') \n");
        sb.append("\n");
        sb.append("include ':manager' \n");
        sb.append("project(':manager').projectDir = new File('manager') \n");
        sb.append("\n");
        sb.append("include ':client' \n");
        sb.append("project(':client').projectDir = new File('client') \n");
        FileUtilies.writeFile(settings, sb.toString(), "UTF-8");
    }

    public void writeSlice(String... modules) {
        File commonIce = FileUtilies.getDirectory("slice_src.common", "common.ice", context.getOut());
        StringBuilder sb = new StringBuilder();
        sb.append("#include \"Glacier2/Session.ice\" \n");
        sb.append("[[\"java:package:" + context.getClasspath() + ".slice\"]] \n");
        sb.append("module common { \n");
        sb.append(" exception BizException { \n");
        sb.append("     int errorCode; \n");
        sb.append("     string errorMsg; \n");
        sb.append(" }; \n");
        sb.append("}; \n");
        FileUtilies.writeFile(commonIce, sb.toString(), "UTF-8");

        File errorIce = FileUtilies.getDirectory("slice_src.common", "error_code.ice", context.getOut());
        sb.setLength(0);
        sb.append("[[\"java:package:" + context.getClasspath() + ".slice\"]] \n");
        sb.append("module common { \n");
        sb.append(" enum ErrorEnum { \n");
        sb.append("     SystemDefaultError=1000,        //系统错误 \n");
        sb.append("     DataAgentServiceError=1001,     //调用DataAgen服务异常 \n");
        sb.append("     JuLoggerServiceError=1002,      //调用JuLogger服务异常 \n");
        sb.append("     RedisServiceError=1003,         //调用REDIS服务异常 \n");
        sb.append(" }; \n");
        sb.append("}; \n");
        FileUtilies.writeFile(errorIce, sb.toString(), "UTF-8");
    }

    public void writeDemoResources(String... modules) {
        // spring-project-module.xml
        String fileName = "spring-" + context.getProjectName() + "-demo.xml";
        File dev = FileUtilies.getDirectory("demo.main.resources.", fileName, context.getOut());
        Document demoSpringXml = new Document(fileName);
        XmlElement demoAnswer = new XmlElement("beans");
        demoAnswer.addAttribute(new Attribute(SpringXMLConstants.XMLNS, SpringXMLConstants.SPRING_BEANS));
        demoAnswer.addAttribute(new Attribute("\n\t\t\t\t" + SpringXMLConstants.XMLNS_XSI, SpringXMLConstants.SPRING_XML_SCHEMA_INSTANCE));
        demoAnswer.addAttribute(new Attribute("\n\t\t\t\txmlns:context", "http://www.springframework.org/schema/context"));
        demoAnswer.addAttribute(new Attribute("\n\t\t\t\txsi:schemaLocation", "http://www.springframework.org/schema/beans\n" +
                "   http://www.springframework.org/schema/beans/spring-beans.xsd\n" +
                "   http://www.springframework.org/schema/context\n" +
                "   http://www.springframework.org/schema/context/spring-context.xsd"));
        // BeanLocator
        XmlElement beanLocator = new XmlElement("bean");
        beanLocator.addAttribute(new Attribute("name", "beanLocator"));
        beanLocator.addAttribute(new Attribute("class", "com.juzix.dams.broker.common.util.BeanLocator"));
        demoAnswer.addElement(beanLocator);

        // propertyConfigurer
        XmlElement propertyConfigurer = new XmlElement("bean");
        propertyConfigurer.addAttribute(new Attribute("id", "propertyConfigurer"));
        propertyConfigurer.addAttribute(new Attribute("class", "com.juzix.dams.broker.common.util.PropertyConfigurer"));

        XmlElement locations = new XmlElement("property");
        XmlElement array = new XmlElement("array");
        for (String module : modules) {
            XmlElement value = new XmlElement("value");
            value.addElement(new TextElement("classpath:" + context.getProjectName() + "-" + module + ".properties"));
            array.addElement(value);
        }
        locations.addElement(array);
        propertyConfigurer.addElement(locations);
        demoAnswer.addElement(propertyConfigurer);


        // import manager
        XmlElement importManager = new XmlElement("import");
        importManager.addAttribute(new Attribute("resource", "spring-" + context.getProjectName() + "-manager.xml"));
        demoAnswer.addElement(importManager);

        // context:component-scan
        XmlElement componentScan = new XmlElement("context:component-scan");
        componentScan.addAttribute(new Attribute("base-package", context.getTargetPackage() + ".demo"));
        demoAnswer.addElement(componentScan);
        demoSpringXml.setRootElement(demoAnswer);
        FileUtilies.writeFile(dev, demoSpringXml.getFormattedContent(0), "UTF-8");


        String managerFileName = "spring-" + context.getProjectName() + "-manager.xml";
        File managerFileXML = FileUtilies.getDirectory("manager.main.resources.", managerFileName, context.getOut());
        Document managerSpringXml = new Document(managerFileName);
        XmlElement managerAnswer = new XmlElement("beans");
        managerAnswer.addAttribute(new Attribute(SpringXMLConstants.XMLNS, SpringXMLConstants.SPRING_BEANS));
        managerAnswer.addAttribute(new Attribute("\n\t\t\t\t" + SpringXMLConstants.XMLNS_XSI, SpringXMLConstants.SPRING_XML_SCHEMA_INSTANCE));
        managerAnswer.addAttribute(new Attribute("\n\t\t\t\txmlns:context", "http://www.springframework.org/schema/context"));
        managerAnswer.addAttribute(new Attribute("\n\t\t\t\txsi:schemaLocation", "http://www.springframework.org/schema/beans\n" +
                "   http://www.springframework.org/schema/beans/spring-beans.xsd\n" +
                "   http://www.springframework.org/schema/context\n" +
                "   http://www.springframework.org/schema/context/spring-context.xsd"));
        // context:component-scan
        XmlElement managerComponentScan = new XmlElement("context:component-scan");
        managerComponentScan.addAttribute(new Attribute("base-package", context.getTargetPackage() + ".manager"));
        managerAnswer.addElement(managerComponentScan);
        managerSpringXml.setRootElement(managerAnswer);
        FileUtilies.writeFile(managerFileXML, managerSpringXml.getFormattedContent(0), "UTF-8");
    }

    public void writeProfiles(String... modules) {
        for (String module : modules) {
            String fileName = context.getProjectName() + "-" + module + ".properties";
            StringBuilder sb = new StringBuilder();
            if ("dao".equals(module)) {
                // broker.sql
                File targetFile = FileUtilies.getDirectory("dao.main.dbscript.", context.getProjectName() + ".sql", context.getOut());
                FileUtilies.writeFile(targetFile, "", "UTF-8");
                // broker-init.sql
                targetFile = FileUtilies.getDirectory("dao.main.dbscript.", context.getProjectName() + "-init.sql", context.getOut());
                FileUtilies.writeFile(targetFile, "", "UTF-8");

                String mybatisConfigFileName = "mybatis-config.xml";
                File mybatisConfigFile = FileUtilies.getDirectory("dao.main.resources.", mybatisConfigFileName, context.getOut());
                Document daoMybatisConfig = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                        XmlConstants.MYBATIS3_MAPPER_CONFIG_SYSTEM_ID);
                XmlElement configuration = new XmlElement("configuration");
                XmlElement settings = new XmlElement("settings");

                XmlElement cacheEnabled = new XmlElement("setting");
                cacheEnabled.addAttribute(new Attribute("name", "cacheEnabled"));
                cacheEnabled.addAttribute(new Attribute("value", "true"));
                settings.addElement(cacheEnabled);

                XmlElement lazyLoadingEnabled = new XmlElement("setting");
                lazyLoadingEnabled.addAttribute(new Attribute("name", "lazyLoadingEnabled"));
                lazyLoadingEnabled.addAttribute(new Attribute("value", "true"));
                settings.addElement(lazyLoadingEnabled);

                XmlElement aggressiveLazyLoading = new XmlElement("setting");
                aggressiveLazyLoading.addAttribute(new Attribute("name", "aggressiveLazyLoading"));
                aggressiveLazyLoading.addAttribute(new Attribute("value", "false"));
                settings.addElement(aggressiveLazyLoading);

                XmlElement lazyLoadTriggerMethods = new XmlElement("setting");
                lazyLoadTriggerMethods.addAttribute(new Attribute("name", "lazyLoadTriggerMethods"));
                lazyLoadTriggerMethods.addAttribute(new Attribute("value", "equals,clone,hashCode,toString"));
                settings.addElement(lazyLoadTriggerMethods);

                XmlElement localCacheScope = new XmlElement("setting");
                localCacheScope.addAttribute(new Attribute("name", "localCacheScope"));
                localCacheScope.addAttribute(new Attribute("value", "SESSION"));
                settings.addElement(localCacheScope);

                XmlElement environments = new XmlElement("environments");
                environments.addAttribute(new Attribute("default", "development"));

                XmlElement environment = new XmlElement("environment");
                environment.addAttribute(new Attribute("id", "development"));

                XmlElement transactionManager = new XmlElement("transactionManager");
                transactionManager.addAttribute(new Attribute("type", "jdbc"));
                environment.addElement(transactionManager);

                XmlElement dataSource = new XmlElement("dataSource");
                dataSource.addAttribute(new Attribute("type", "POOLED"));

                XmlElement driver = new XmlElement("property");
                driver.addAttribute(new Attribute("name", "driver"));
                driver.addAttribute(new Attribute("value", "${jdbc.driver}"));
                dataSource.addElement(driver);

                XmlElement url = new XmlElement("property");
                url.addAttribute(new Attribute("name", "url"));
                url.addAttribute(new Attribute("value", "${jdbc.url}"));
                dataSource.addElement(url);

                XmlElement username = new XmlElement("property");
                username.addAttribute(new Attribute("name", "username"));
                username.addAttribute(new Attribute("value", "${jdbc.username}"));
                dataSource.addElement(username);

                XmlElement password = new XmlElement("property");
                password.addAttribute(new Attribute("name", "password"));
                password.addAttribute(new Attribute("value", "${jdbc.password}"));
                dataSource.addElement(password);

                XmlElement poolMaximumActiveConnections = new XmlElement("property");
                poolMaximumActiveConnections.addAttribute(new Attribute("name", "poolMaximumActiveConnections"));
                poolMaximumActiveConnections.addAttribute(new Attribute("value", "50"));
                dataSource.addElement(poolMaximumActiveConnections);

                XmlElement poolMaximumIdleConnections = new XmlElement("property");
                poolMaximumIdleConnections.addAttribute(new Attribute("name", "poolMaximumIdleConnections"));
                poolMaximumIdleConnections.addAttribute(new Attribute("value", "5"));
                dataSource.addElement(poolMaximumIdleConnections);

                XmlElement poolMaximumCheckoutTime = new XmlElement("property");
                poolMaximumCheckoutTime.addAttribute(new Attribute("name", "poolMaximumCheckoutTime"));
                poolMaximumCheckoutTime.addAttribute(new Attribute("value", "30000"));
                dataSource.addElement(poolMaximumCheckoutTime);

                XmlElement poolTimeToWait = new XmlElement("property");
                poolTimeToWait.addAttribute(new Attribute("name", "poolTimeToWait"));
                poolTimeToWait.addAttribute(new Attribute("value", "20000"));
                dataSource.addElement(poolTimeToWait);

                XmlElement poolPingQuery = new XmlElement("property");
                poolPingQuery.addAttribute(new Attribute("name", "poolPingQuery"));
                poolPingQuery.addAttribute(new Attribute("value", "select now()"));
                dataSource.addElement(poolPingQuery);

                XmlElement poolPingEnabled = new XmlElement("property");
                poolPingEnabled.addAttribute(new Attribute("name", "poolPingEnabled"));
                poolPingEnabled.addAttribute(new Attribute("value", "true"));
                dataSource.addElement(poolPingEnabled);

                XmlElement poolPingConnectionsNotUsedFor = new XmlElement("property");
                poolPingConnectionsNotUsedFor.addAttribute(new Attribute("name", "poolPingConnectionsNotUsedFor"));
                poolPingConnectionsNotUsedFor.addAttribute(new Attribute("value", "3600000"));
                dataSource.addElement(poolPingConnectionsNotUsedFor);

                XmlElement defaultTransactionIsolationLevel = new XmlElement("property");
                defaultTransactionIsolationLevel.addAttribute(new Attribute("name", "defaultTransactionIsolationLevel"));
                defaultTransactionIsolationLevel.addAttribute(new Attribute("value", "2"));
                dataSource.addElement(defaultTransactionIsolationLevel);

                environment.addElement(dataSource);

                environments.addElement(environment);

                settings.addElement(environments);

                XmlElement mappers = new XmlElement("mappers");
                for (GeneratedXmlFile gxf : generatedXmlFiles) {
                    XmlElement mapper = new XmlElement("mapper");
                    mapper.addAttribute(new Attribute("url", "file:../conf/mapper/" + gxf.getFileName()));
                    mappers.addElement(mapper);
                }
                settings.addElement(mappers);

                configuration.addElement(settings);
                daoMybatisConfig.setRootElement(configuration);
                FileUtilies.writeFile(mybatisConfigFile, daoMybatisConfig.getFormattedContent(0), "UTF-8");

                fileName = "jdbc-config.properties";
                sb.append("jdbc.driver=com.mysql.jdbc.Driver \n");
                sb.append("jdbc.url=");
                sb.append(context.getUrl());
                sb.append("?characterEncoding=utf-8&characterSetResults=utf-8&useUnicode=false&autoReconnect=true&rewriteBatchedStatements=true&allowMultiQueries=true \n");
                sb.append("jdbc.username=");
                sb.append(context.getUser());
                sb.append("\n");
                sb.append("jdbc.password=");
                sb.append(context.getPassword());
                sb.append("\n");
                sb.append("#定义初始连接数 \n");
                sb.append("initialSize=20 \n");
                sb.append("#定义最大连接池数量 \n");
                sb.append("maxActive=200 \n");
                sb.append("#定义最大空闲 \n");
                sb.append("maxIdle=10 \n");
                sb.append("#定义最小空闲 \n");
                sb.append("minIdle=10 \n");
                sb.append("#获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁 \n");
                sb.append("maxWait=5000 \n");
                sb.append("filters=stat \n");
                sb.append("timeBetweenEvictionRunsMillis=20000 \n");
                sb.append("minEvictableIdleTimeMillis=20000 \n");
                sb.append("validationQuery=SELECT 1 \n");
                sb.append("testWhileIdle=true \n");
                sb.append("#申请连接时执行validationQuery检测池里连接的可用性 \n");
                sb.append("testOnBorrow=true \n");
                sb.append("#归还连接时执行validationQuery检测池里连接的可用性 \n");
                sb.append("testOnReturn=true \n");
                sb.append("#对于建立时间超过removeAbandonedTimeout的连接强制关闭 \n");
                sb.append("removeAbandoned=true \n");
                sb.append("#指定连接建立多长时间就需要被强制关闭 \n");
                sb.append("removeAbandonedTimeout=180 \n");
                sb.append("#指定发生removeabandoned的时候，是否记录当前线程的堆栈信息到日志中 \n");
                sb.append("logAbandoned=true \n");
                sb.append("#是否缓存preparedStatement \n");
                sb.append("poolPreparedStatements=false \n");
                sb.append("maxOpenPreparedStatements=-1 \n");
                sb.append("maxPoolPreparedStatementPerConnectionSize=20 \n");
            }

            String content = sb.toString();
            // dev
            File dev = FileUtilies.getDirectory(module + ".main.profiles.dev.", fileName, context.getOut());
            FileUtilies.writeFile(dev, content, "UTF-8");

            // product
            File product = FileUtilies.getDirectory(module + ".main.profiles.product.", fileName, context.getOut());
            FileUtilies.writeFile(product, content, "UTF-8");

            // test
            File test = FileUtilies.getDirectory(module + ".main.profiles.test.", fileName, context.getOut());
            FileUtilies.writeFile(test, content, "UTF-8");

            // testsh
            File testsh = FileUtilies.getDirectory(module + ".main.profiles.testsh.", fileName, context.getOut());
            FileUtilies.writeFile(testsh, content, "UTF-8");
        }
    }

    public void writePackageInfo(String... modules) {
        for (String module : modules) {
            // package-info
            String targetPackage = context.getTargetPackage() + "." + module;
            File packageInfo = FileUtilies.getDirectory(module + ".main.java." + targetPackage, "package-info.java", context.getOut());
            FileUtilies.writeFile(packageInfo, "package " + targetPackage + ";", "UTF-8");
        }
    }

    public TableInfoWrapper createTableInfoWrapper(CodeGenContext context) {
        return new PltTableMyBatis3Impl(context);
    }

    public String dealTableName(String tableName) {
        tableName = tableName.toLowerCase();
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
        columnName = columnName.toLowerCase();
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

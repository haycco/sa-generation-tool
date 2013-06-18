/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-18 上午1:15:48   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @author lgc
 */
public abstract class SuCommand {

    protected SAXReader reader = new SAXReader();
    // 当前编辑的XML Document
    protected Document document = null;
    // 当前SU需要处理的service列表
    protected List<String> serviceList = null;
    // 需要生产的SA文件夹
    protected String targetServiceAssemblyDir = null;
   // 需要生产的SA文件夹
    protected String sourceServiceAssemblyDir = null;
    // 编号
    protected int num;
    // SU目标文件夹
    protected String targetDir = null;
    // SU源目录
    protected String sourceDir = null;
    // 默认编码格式
    protected final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取当前SU目标文件夹
     */
    protected abstract String getTargetDir();

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    /**
     * 获取当前SU模块源目录
     */
    protected abstract String getSourceDir() ;

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public File getTargetFile() {
        return new File(this.getTargetDir());
    }

    public File getSourceFile() {
        return new File(this.getSourceDir());
    }

    protected int getNum() {
        return num;
    }

    protected void setNum(int num) {
        this.num = num;
    }

    /**
     * 当前编辑的XML Document
     * 
     * @return
     */
    protected abstract Document getDocument();

    protected void setDocument(Document document) {
        this.document = document;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public String getTargetServiceAssemblyDir() {
        return targetServiceAssemblyDir;
    }

    public void setTargetServiceAssemblyDir(String targetServiceAssemblyDir) {
        this.targetServiceAssemblyDir = targetServiceAssemblyDir;
    }

    public String getSourceServiceAssemblyDir() {
        return sourceServiceAssemblyDir;
    }

    public void setSourceServiceAssemblyDir(String sourceServiceAssemblyDir) {
        this.sourceServiceAssemblyDir = sourceServiceAssemblyDir;
    }

    public abstract void execute();

}
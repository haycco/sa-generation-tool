/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                haycco                
 * Create Date：                         2013-5-18 上午1:15:48   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * @author haycco
 */
public abstract class SuCommand {

    protected SAXReader reader = new SAXReader();
    /**
     * 当前编辑的XML Document
     */
    private Document document = null;
    /**
     * 当前SU需要处理的service列表
     */
    private List<String> serviceList = null;
    /**
     * 需要生产的SA文件夹
     */
    protected String targetServiceAssemblyDir = null;
    /**
     * 需要生产的SA文件夹
     */
    protected String sourceServiceAssemblyDir = null;
    /**
     * 编号
     */
    protected int num;
    /**
     * SU目标文件夹
     */
    protected String targetDir = null;
    /**
     * SU源目录
     */
    protected String sourceDir = null;
    /**
     * 默认编码格式 UTF-8
     */
    protected final static String DEFAULT_CHARSET = "UTF-8";
    /**
     * 需要修改的文件
     */
    protected File modifyFile = null;
    
    /**
     * 获取需要修改的文件
     */
    public File getModifyFile() {
        return modifyFile;
    }
    
    public void setModifyFile(File modifyFile) {
        this.modifyFile = modifyFile;
    }

    /**
     * 获取当前SU目标文件夹
     */
    protected abstract String getTargetDir();

    /**
     * 获取当前SU模块源目录
     */
    protected abstract String getSourceDir() ;

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
    public Document getDocument(){
        try {
            if(document != null){
                return this.document;
            } else {
                this.document = reader.read(this.getModifyFile());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public void setDocument(Document document) {
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

    public void execute() throws IOException {
        // copy source dir
        FileUtils.copyDirectory(getSourceFile(), getTargetFile());
    }

}
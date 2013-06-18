/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-18 下午4:53:19   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.haycco.sagent.util.ServiceAssemblyXmlUtil;
import org.haycco.sagent.util.ServiceUnit;
import org.haycco.sagent.util.ZipUtil;

/**
 * @author lgc
 */
public class BeanCommand extends SuCommand {

    private final static String XBEAN_NAME = "xbean.xml";
    private File xbeanFile = null;
    
    @Override
    protected String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + ServiceUnit.BEAN.getName() + getNum();
    }

    @Override
    protected String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + ServiceUnit.BEAN.getName();
    }

    public File getXbeanFile() {
        return xbeanFile;
    }

    public void setXbeanFile(File xbeanFile) {
        this.xbeanFile = xbeanFile;
    }
    
    @Override
    protected Document getDocument() {
        try {
            document = null;
            document = reader.read(this.getXbeanFile());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    public void execute() {
        try {
            // copy source dir
            FileUtils.copyDirectory(getSourceFile(), getTargetFile());
        } catch (IOException e) {
            throw new RuntimeException("复制BEAN组件文件失败", e);
        }
        this.setXbeanFile(new File(getTargetDir() + File.separator + XBEAN_NAME));
        // modify bean component xbean.xml
        document = ServiceAssemblyXmlUtil.updateBeanComponentXBeanConfig(getDocument(), getNum(), serviceList);
        ServiceAssemblyXmlUtil.writeDoc(document, this.getXbeanFile(), DEFAULT_CHARSET);
        ZipUtil.zip(getTargetDir(), getTargetDir() + ZipUtil.ZIP_SUFFIX);
        try {
            FileUtils.deleteDirectory(getTargetFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
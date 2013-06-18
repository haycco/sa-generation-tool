/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-18 上午1:20:00   
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
public class HttpCommand extends SuCommand {

    private final static String XBEAN_NAME = "xbean.xml";
    private File xbeanFile = null;

    public String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + ServiceUnit.HTTP.getName() + getNum();
    }

    @Override
    public String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + ServiceUnit.HTTP.getName();
    }

    @Override
    public Document getDocument() {
        try {
            document = null;
            document = reader.read(this.getXbeanFile());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public File getXbeanFile() {
        return xbeanFile;
    }

    public void setXbeanFile(File xbeanFile) {
        this.xbeanFile = xbeanFile;
    }

    @Override
    public void execute() {
        try {
            // copy source dir
            FileUtils.copyDirectory(getSourceFile(), getTargetFile());
        } catch (IOException e) {
            throw new RuntimeException("复制Pubws文件失败", e);
        }
        this.setXbeanFile(new File(getTargetDir() + File.separator + XBEAN_NAME));
        // modify jsr xbean.xml
        document = ServiceAssemblyXmlUtil.updatePubwsXbeanConfig(getDocument(), getNum());
        ServiceAssemblyXmlUtil.writeDoc(document, this.getXbeanFile(), DEFAULT_CHARSET);
        ZipUtil.zip(getTargetDir(), getTargetDir() + ZipUtil.ZIP_SUFFIX);
        try {
            FileUtils.deleteDirectory(getTargetFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-18 上午1:20:19   
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
public class CamelCommand extends SuCommand {

    private final static String CAMEL_CONTEXT = "camel-context.xml";
    private File camelContextFile = null;

    public String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + ServiceUnit.CAMEL.getName() + getNum();
    }

    @Override
    public String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + ServiceUnit.CAMEL.getName();
    }

    @Override
    public Document getDocument() {
        try {
            document = null;
            document = reader.read(this.getCamelContextFile());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public File getCamelContextFile() {
        return camelContextFile;
    }

    public void setCamelContextFile(File camelContextFile) {
        this.camelContextFile = camelContextFile;
    }

    @Override
    public void execute() {
        try {
            // copy source dir
            FileUtils.copyDirectory(getSourceFile(), getTargetFile());
        } catch (IOException e) {
            throw new RuntimeException("复制Route文件失败", e);
        }
        this.setCamelContextFile(new File(getTargetDir() + File.separator + CAMEL_CONTEXT));
        // modify route camel-context.xml
        document = ServiceAssemblyXmlUtil.updateCamelContextConfig(getDocument(), getNum(), serviceList);
        ServiceAssemblyXmlUtil.writeDoc(document, this.getCamelContextFile(), DEFAULT_CHARSET);
        ZipUtil.zip(getTargetDir(), getTargetDir() + ZipUtil.ZIP_SUFFIX);
        try {
            FileUtils.deleteDirectory(getTargetFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-18 下午3:12:00   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.haycco.sagent.util.ServiceAssemblyXmlUtil;

/**
 * @author lgc
 */
public class JbiCommand extends SuCommand {

    private final static String JBI_NAME = "jbi.xml";
    private File jbiFile = null;

    @Override
    public String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + "META-INF";
    }

    @Override
    public String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + "META-INF";
    }

    @Override
    public Document getDocument() {
        try {
            document = null;
            document = reader.read(getJbiFile());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public File getJbiFile() {
        return jbiFile;
    }

    public void setJbiFile(File jbiFile) {
        this.jbiFile = jbiFile;
    }

    @Override
    public void execute() {
        try {
            // copy source dir
            FileUtils.copyDirectory(getSourceFile(), getTargetFile());
        } catch (IOException e) {
            throw new RuntimeException("复制META-INF文件失败", e);
        }
        this.setJbiFile(new File(getTargetDir() + File.separator + JBI_NAME));
        document = ServiceAssemblyXmlUtil.updateJbiConfig(getDocument(), getNum());
        ServiceAssemblyXmlUtil.writeDoc(document, getJbiFile(), DEFAULT_CHARSET);
    }

}
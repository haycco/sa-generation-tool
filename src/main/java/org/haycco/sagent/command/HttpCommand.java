/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                haycco                
 * Create Date：                         2013-5-18 上午1:20:00   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.haycco.sagent.util.ServiceAssemblyXmlUtil;
import org.haycco.sagent.util.ServiceUnit;
import org.haycco.sagent.util.ZipUtil;

/**
 * @author haycco
 */
public class HttpCommand extends SuCommand {

    /**
     * HTTP组件中需要更新的文件，默认为xbean.xml
     */
    private final static String XBEAN_NAME = "xbean.xml";

    public String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + ServiceUnit.HTTP.getName() + getNum();
    }

    @Override
    public String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + ServiceUnit.HTTP.getName();
    }

    @Override
    public void execute() throws IOException {
        super.execute();
        this.setModifyFile(new File(getTargetDir() + File.separator + XBEAN_NAME));
        // modify jsr xbean.xml
        Document doc = ServiceAssemblyXmlUtil.updatePubwsXbeanConfig(getDocument(), getNum());
        this.setDocument(doc);
        ServiceAssemblyXmlUtil.writeDoc(getDocument(), this.getModifyFile(), DEFAULT_CHARSET);
        ZipUtil.zip(getTargetDir(), getTargetDir() + ZipUtil.ZIP_SUFFIX);
        FileUtils.deleteDirectory(getTargetFile());
    }

}
/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                haycco                
 * Create Date：                         2013-5-18 下午3:12:00   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.haycco.sagent.util.ServiceAssemblyXmlUtil;

/**
 * @author haycco
 */
public class JbiCommand extends SuCommand {

    /**
     * JBI中需要更新的文件，默认为jbi.xml
     */
    private final static String JBI_NAME = "jbi.xml";

    @Override
    public String getTargetDir() {
        return targetDir = getTargetServiceAssemblyDir() + File.separator + "META-INF";
    }

    @Override
    public String getSourceDir() {
        return sourceDir = getSourceServiceAssemblyDir() + File.separator + "META-INF";
    }

    @Override
    public void execute() throws IOException {
        super.execute();
        this.setModifyFile(new File(getTargetDir() + File.separator + JBI_NAME));
        Document doc = ServiceAssemblyXmlUtil.updateJbiConfig(getDocument(), getNum());
        this.setDocument(doc);
        ServiceAssemblyXmlUtil.writeDoc(getDocument(), getModifyFile(), DEFAULT_CHARSET);
    }

}
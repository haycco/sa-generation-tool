/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                                             haycco                
 * Create Date：                                                2013-5-17 上午9:19:02   
 * Version:                       1.0
 */
package org.haycco.sagent.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.dom4j.DocumentException;
import org.haycco.sagent.util.GenServiceAssemblyUtil;
import org.haycco.sagent.util.URLUtil;

/**
 * <p>
 * 本程序现在只提供了最多含servicemix-jsr181、servicemix-http、servicemix-camel和servicemix-bean四个SU组件的SA压缩包批量生产，
 * 根据提供的SA发布的zip包，按相应编号进行复制。
 * </p>
 * <p>
 * PS：
 * <li>程序能自动扫描解析按规范SA中包含的SU组件及其类型。</li>
 * <li>如果SA还包含例如其他组件类型的SU，SU的配置文件修改( 可参考Jsr181Command/HttpCommand/CamelCommand等的具体处理方式)。</li>
 * <li>如果还包含其他类型的SU组件，可参照BeanCommand的实现方式进行处理。</li>
 * <li>由于Jbi不属于SU，虽然JbiCommand实现SuCommand，仅为方便统一SA内容修改步骤。</li>
 * </p>
 * 
 * @author haycco
 */
public class GenServiceAssembliesApp {

    // 是否自动打开输出目录
    private final static boolean ISOPEN = true;
    
    private final static Logger log = Logger.getAnonymousLogger();

    /**
     * @param args
     * @throws DocumentException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws DocumentException, InterruptedException, IOException {

        long start = System.currentTimeMillis();
        // 清理
        GenServiceAssemblyUtil.cleanTempDirectory();
        String resourceURL = URLUtil.getClassPath(URLUtil.class) + "\\template\\";
        // MDM
        String mdmZipFile = resourceURL + "guowang-mdm-service.zip";
        GenUsefulServiceAssembliesUtil.genMDMServiceAssembly(mdmZipFile, 1, 51);
        
        // 基建报表
        String sgccZipFile = resourceURL + "sgcc-wbs-report.zip";
        GenUsefulServiceAssembliesUtil.genSgccServiceAssembly(sgccZipFile, 1, 77);
        
        // 中电投吉电报表集成
        String uniqInfoReportZipFile = resourceURL + "jidian-uniqInfoReportIndex-access.zip";
        GenUsefulServiceAssembliesUtil.genUniqeInfoReportServiceAssembly(uniqInfoReportZipFile, 1, 1);

        // 员工报销
        String staffPayZipFile = resourceURL + "guowang-staff-pay.zip";
        GenUsefulServiceAssembliesUtil.genStaffPayServiceAssembly(staffPayZipFile, 2, 21);

        long end = System.currentTimeMillis();
        log.info("批量生成的SA存放路径：" + GenServiceAssemblyUtil.TEMP_DIRECTORY);
        log.info("共耗时:" + (end - start) / 1000.0 + "'s");

        File produceDir = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        // 如果目录正确生成则打开该目录(仅在Window下)
        if (ISOPEN && produceDir.exists()) {
            String command = "explorer \"" + GenServiceAssemblyUtil.TEMP_DIRECTORY + "\"";
            // System.out.println("execute window command: " + command);
            Runtime.getRuntime().exec(command);
        }
    }

}
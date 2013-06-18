/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-17 下午8:33:05   
 * Version:                                 1.0
 */
package org.haycco.sagent.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.haycco.sagent.command.BeanCommand;
import org.haycco.sagent.command.CamelCommand;
import org.haycco.sagent.command.GenServiceUnitInvoker;
import org.haycco.sagent.command.HttpCommand;
import org.haycco.sagent.command.JbiCommand;
import org.haycco.sagent.command.Jsr181Command;
import org.haycco.sagent.command.SuCommand;

/**
 * <h1>根据SA打包后的zip批量生产SA</h1>
 * <p>
 * Step 1. SA重命名
 * </p>
 * <p>
 * Step 2. 修改jbi.xml文件所涉及到su及sa名字的
 * </p>
 * <p>
 * Step 3.
 * <li>(1)修改jsr181组件中的xbean.xml配置文件，所有service名后面加上生产编号；</li>
 * <li>(2)修改http组件中的xbean.xml配置文件，修改每个http:soap-consumer 节点属性 service 和 locationURI；</li>
 * <li>(3)修改camel组件中的camel-context.xml配置文件</li>
 * <ul>
 * <li>①修改jbi相关的组件camel:from camel:to</li>
 * <li>②修改jms相关的组件 camel:from camel:to</li>
 * <li>③修改vm的名称 camel:from</li>
 * </ul>
 * </p>
 * <p>
 * Step 4. 重新打包
 * </p>
 * 
 * @author lgc
 */
public class GenServiceAssemblyUtil {

    /**
     * 程序运行时临时处理和输出文件夹
     */
    public final static String TEMP_DIRECTORY = "D:\\sa\\output";

    /**
     * 清空以前的临时文件
     * 
     * @throws IOException
     */
    public static void cleanTempDirectory() throws IOException {
        File tempDir = new File(TEMP_DIRECTORY);
        if (tempDir.exists()) {
            System.out.println("清空以前的临时文件...");
            FileUtils.cleanDirectory(tempDir);
        }
    }

    /**
     * 还原SU默认名称设置
     */
    public static void resetSUDefaultValue() {
        ServiceUnit.JSR181.setName("servicemix-jsr181");
        ServiceUnit.HTTP.setName("servicemix-http");
        ServiceUnit.CAMEL.setName("servicemix-camel");
        ServiceUnit.BEAN.setName("servicemix-bean");
    }

    /**
     * 刷新SA中包含的SU名称列表
     * 
     * @param suNameList
     */
    public static void refreshServiceUnit(Map<String, String> suNameMap) {
        if(suNameMap.containsKey(ServiceUnit.JSR181.getName())) {
           // 设置JSR组件单元名称
            ServiceUnit.JSR181.setName(suNameMap.get(ServiceUnit.JSR181.getName()));
        }
        if (suNameMap.containsKey(ServiceUnit.HTTP.getName())) {
            // 设置HTTP组件单元名称
            ServiceUnit.HTTP.setName(suNameMap.get(ServiceUnit.HTTP.getName()));
        }
        if (suNameMap.containsKey(ServiceUnit.CAMEL.getName())) {
            // 设置CAMEL组件单元名称
            ServiceUnit.CAMEL.setName(suNameMap.get(ServiceUnit.CAMEL.getName()));
        }
        if (suNameMap.containsKey(ServiceUnit.BEAN.getName())) {
            // 设置BEAN组件单元名称
            ServiceUnit.BEAN.setName(suNameMap.get(ServiceUnit.BEAN.getName()));
        }
    }

    /**
     * 解压SA及其内的所有SU组件以及刷新ServiceUnit枚举的SU命名并返回SU名称列表
     * 
     * @param saZipFile
     *            需要解压的SA压缩包文件
     * @param destinationDir
     *            解压后存放的目录
     */
    private static Map<String, String> getSUNameListWithUnZipAll(String saZipFile, String destinationDir) {
        Map<String, String> suNameMap = ZipUtil.getServiceUnitComponentNameMap(saZipFile);
        if(suNameMap.size()<=0) {
            throw new RuntimeException("该压缩包不是正确的服务组合件(SA)，请检查！");
        }
        resetSUDefaultValue();
        refreshServiceUnit(suNameMap);
        
        // 解压目标SA
        ZipUtil.unZip(saZipFile, destinationDir);
        // 循环处理SU的压缩包
        for (String suName : suNameMap.values()) {
            String sourceSuDir = destinationDir + File.separator + suName;
            ZipUtil.unZip(sourceSuDir + ZipUtil.ZIP_SUFFIX, sourceSuDir);
        }
        return suNameMap;
    }

    /**
     * 根据SU名称列表初始化相应类型的SU的修改命令
     * 
     * @param suNameList
     * @return
     */
    private static Map<String, SuCommand> prepareSuCommand(Map<String, String> suNameMap) {
        //通过有序的LinkedHashMap控制处理的先后顺序
        Map<String, SuCommand> cmdMap = new LinkedHashMap<String, SuCommand>();
        if (suNameMap.containsValue(ServiceUnit.BEAN.getName())) {
            cmdMap.put(ServiceUnit.BEAN.getName(), new BeanCommand());
        }
        if (suNameMap.containsValue(ServiceUnit.JSR181.getName())) {
            cmdMap.put(ServiceUnit.JSR181.getName(), new Jsr181Command());
        }
        if (suNameMap.containsValue(ServiceUnit.HTTP.getName())) {
            cmdMap.put(ServiceUnit.HTTP.getName(), new HttpCommand());
        }
        if (suNameMap.containsValue(ServiceUnit.CAMEL.getName())) {
            cmdMap.put(ServiceUnit.CAMEL.getName(), new CamelCommand());
        }
        return cmdMap;
    }

    /**
     * 
     * @param saZipFile
     *            需要批量复制的源SA
     * @param count
     *            需要生成SA的个数
     * @param startNum
     *            开始编号
     * @throws IOException
     * @throws DocumentException
     */
    public static void genServiceAssembly(String saZipFile, int count, int startNum) throws IOException,
            DocumentException {
        File file = new File(saZipFile);
        String saFileName = file.getName();
        String serviceAssemblyName = saFileName.substring(0, saFileName.indexOf("."));
        System.out.println("需要批量生产的压缩包文件名为：" + saFileName + "\n共批量生成" + count + "个，起始编号为：" + startNum);
        // 解压后的SA临时文件夹
        String destinationDir = TEMP_DIRECTORY + File.separator + serviceAssemblyName;
        // 解压SA及其内的所有SU组件并获取SU名称列表
        Map<String, String> suNameMap = getSUNameListWithUnZipAll(saZipFile, destinationDir);
        
        // init command list
        Map<String, SuCommand> cmdMap = prepareSuCommand(suNameMap);

        GenServiceUnitInvoker genServiceUnitInvoker = new GenServiceUnitInvoker();
        // 设置该SA源模版目录
        genServiceUnitInvoker.setSourceServiceAssemblyDir(destinationDir);
        int lenght = (startNum + count);
        // copy file
        for (int i = startNum; i < lenght; i++) {
            long start = System.currentTimeMillis();
            System.out.println("正在处理编号为" + i + "的SA...");
            // 设置生产批号
            genServiceUnitInvoker.setNum(i);
            // 新复制SA的名字
            String targetServiceAssemblyName = serviceAssemblyName + i;
            // 新SA路径
            String targetServiceAssemblyDir = TEMP_DIRECTORY + File.separator + targetServiceAssemblyName;
            File genSADir = new File(targetServiceAssemblyDir);
            if (!genSADir.exists()) {
                genSADir.mkdir();// 创建新复制的SA文件夹
            }
            // 设置新复制的SA文件夹
            genServiceUnitInvoker.setTargetServiceAssemblyDir(targetServiceAssemblyDir);
            // modify jbi.xml
            genServiceUnitInvoker.setCommand(new JbiCommand());
            genServiceUnitInvoker.action();
            // 循环处理SU
            for (SuCommand command : cmdMap.values()) {
                genServiceUnitInvoker.setCommand(command);
                genServiceUnitInvoker.action();
            }

            // compress genator service assembly and clean temp files
            ZipUtil.zip(targetServiceAssemblyDir, targetServiceAssemblyDir + ZipUtil.ZIP_SUFFIX);
            FileUtils.deleteDirectory(new File(targetServiceAssemblyDir));

            long end = System.currentTimeMillis();
            System.out.println("生产编号为" + i + "的SA共耗时:" + (end - start) / 1000.0 + "'s");
        }

        // the end clean all temp files
        FileUtils.deleteDirectory(new File(destinationDir));
    }
}
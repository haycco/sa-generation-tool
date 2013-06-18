/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                lgc                
 * Create Date：                         2013-5-17 下午7:16:03   
 * Version:                                 1.0
 */
package org.haycco.sagent.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.dom4j.DocumentException;

/**
 * Zip工具类，调用Ant的zip和unzip的API进行解压和压缩
 * 
 * @author lgc
 */
public class ZipUtil {
    
    /**
     * ZIP压缩文件的后缀(.zip)
     */
    public final static String ZIP_SUFFIX = ".zip";

    /**
     * 解压缩
     * 
     * @param sourceZip
     *            源zip文件 c:/upload.zip
     * @param destDir
     *            生成的目标目录下 c:/a 结果则是 将upload.zip文件解压缩到c:/a目录下
     */
    public static void unZip(String sourceZip, String destDir) {
        try {
            Project project = new Project();
            Expand expand = new Expand();
            expand.setProject(project);
            File sourceZipFile = new File(sourceZip);
            expand.setSrc(sourceZipFile);
            expand.setOverwrite(true);// 是否覆盖
            File f = new File(destDir);
            expand.setDest(f);
            System.out.println("execute unzip (" +sourceZipFile.getName() + ") command...");
            expand.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩
     * 
     * @param sourceFile
     *            压缩的源文件 如: c:/upload
     * @param targetZip
     *            生成的目标文件 如：c:/upload.zip
     */
    public static void zip(String sourceFile, String targetZip) {
        Project project = new Project();
        Zip zip = new Zip();
        zip.setProject(project);
        File targetZipFile = new File(targetZip);
        zip.setDestFile(targetZipFile);// 设置生成的目标zip文件File对象
        System.out.println("execute zip (" + targetZipFile.getName() + ") command...");
        FileSet fileSet = new FileSet();
        fileSet.setProject(project);
        fileSet.setDir(new File(sourceFile));// 设置将要进行压缩的源文件File对象
        // fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件
        // fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件
        zip.addFileset(fileSet);
        zip.execute();
    }
    
    /**
     * 根据SA打包文件获取所包含SU名称的映射表
     * 
     * @param zipFilePath
     *            zip文件
     * @throws DocumentException
     */
    public static Map<String, String> getServiceUnitComponentNameMap(String zipFilePath) {
        InputStream inputStream = null;
        File zipFile = new File(zipFilePath);
        Map<String, String> suNameMap = new HashMap<String, String>();
        try {
            ZipFile sazipFile = new ZipFile(zipFile);
            inputStream = new FileInputStream(zipFile);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry = null;
            while((zipEntry = zipInputStream.getNextEntry())!=null) {
                String name = zipEntry.getName();
                int index = name.indexOf(ZIP_SUFFIX);
                if(index>=0) {
                    ZipInputStream suZipInputStream = new ZipInputStream(sazipFile.getInputStream(zipEntry));
                    ZipEntry zuZipEntry = null;
                    String componentType = "";
                    while((zuZipEntry = suZipInputStream.getNextEntry())!=null) {
                        if(zuZipEntry.getName().contains("su.properties")) {
                            //识别SU组件类型
                            componentType = IOUtils.toString(suZipInputStream).replace("component=", "");
                            String suName = name.substring(0, index);
                            //过滤获取SU名称放进列表
                            suNameMap.put(componentType, suName);
                        }
                    }                    
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(zipFilePath + " (系统找不到指定的文件。)", e);
        } catch (IOException e) {
            throw new RuntimeException(zipFilePath + " (系统文件读取出错。)", e);
        }  finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("文件流关闭出错。", e);
            }
        }
        
        return suNameMap;
    }
    
}
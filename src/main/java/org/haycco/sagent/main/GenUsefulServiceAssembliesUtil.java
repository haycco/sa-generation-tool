/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                                             lgc                
 * Create Date：                                                2013-5-20 上午10:16:53   
 * Version:                       1.0
 */
package org.haycco.sagent.main;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.haycco.sagent.util.GenServiceAssemblyUtil;

/**
 * 提供可用的SA批量生成的调用定义
 * 
 * @author lgc
 */
public class GenUsefulServiceAssembliesUtil {

    /**
     * 批量生产MDM主数据SA
     * 
     * @param saZipFile
     *            mdm模版Zip文件URI
     * @throws IOException
     * @throws DocumentException
     */
    public static void genMDMServiceAssembly(String saZipFile, int count, int startNum) throws IOException,
            DocumentException {
        long start = System.currentTimeMillis();
        GenServiceAssemblyUtil.genServiceAssembly(saZipFile, count, startNum);
        long end = System.currentTimeMillis();
        System.out.println("生成MDM共耗时:" + (end - start) / 1000.0 + "'s");
    }

    /**
     * 批量生产基建报表SA
     * 
     * @param saZipFile
     *            基建报表模版ZIP文件URI
     * @param count
     *            生产总数
     * @param startNum
     *            起始编号
     * @throws IOException
     * @throws DocumentException
     */
    public static void genSgccServiceAssembly(String saZipFile, int count, int startNum)
            throws IOException, DocumentException {
        long start = System.currentTimeMillis();
        GenServiceAssemblyUtil.genServiceAssembly(saZipFile, count, startNum);
        long end = System.currentTimeMillis();
        System.out.println("生成基建报表共耗时:" + (end - start) / 1000.0 + "'s");
    }

    /**
     * 批量生产中电投吉电报表集成SA
     * 
     * @param saZipFile 中电投吉电报表ZIP文件URI
     * @param count 生产总数
     * @param startNum 起始编号
     * @throws IOException
     * @throws DocumentException
     */
    public static void genUniqeInfoReportServiceAssembly(String saZipFile, int count, int startNum)
            throws IOException, DocumentException {
        long start = System.currentTimeMillis();
        GenServiceAssemblyUtil.genServiceAssembly(saZipFile, count, startNum);
        long end = System.currentTimeMillis();
        System.out.println("生成中电投吉电报表集成共耗时:" + (end - start) / 1000.0 + "'s");
    }
    
    /**
     * 批量生产员工报销SA
     * 
     * @param saZipFile 员工报销ZIP文件URI
     * @param count 生产总数
     * @param startNum 起始编号
     * @throws IOException
     * @throws DocumentException
     */
    public static void genStaffPayServiceAssembly(String saZipFile, int count, int startNum)
            throws IOException, DocumentException {
        long start = System.currentTimeMillis();
        GenServiceAssemblyUtil.genServiceAssembly(saZipFile, count, startNum);
        long end = System.currentTimeMillis();
        System.out.println("生成员工报销共耗时:" + (end - start) / 1000.0 + "'s");
    }

}
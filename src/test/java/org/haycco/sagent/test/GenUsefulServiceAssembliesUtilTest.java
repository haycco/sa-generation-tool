package org.haycco.sagent.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.dom4j.DocumentException;
import org.haycco.sagent.main.GenUsefulServiceAssembliesUtil;
import org.haycco.sagent.util.GenServiceAssemblyUtil;
import org.haycco.sagent.util.URLUtil;
import org.junit.Test;

public class GenUsefulServiceAssembliesUtilTest extends TestCase {

    private final static String RES_URL = URLUtil.getClassPath(GenUsefulServiceAssembliesUtilTest.class) + "\\template\\";
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        GenServiceAssemblyUtil.cleanTempDirectory();
    }

    @Test
    public void testGenMDMServiceAssembly() throws IOException, DocumentException {
        String mdmZipFile = RES_URL + "guowang-mdm-service.zip";
        GenUsefulServiceAssembliesUtil.genMDMServiceAssembly(mdmZipFile, 2, 1);
        File file = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        assertEquals(2, file.listFiles().length);
    }
    
    @Test
    public void testGenStaffPayServiceAssembly() throws IOException, DocumentException {
        String staffPayZipFile = RES_URL + "guowang-staff-pay.zip";
        GenUsefulServiceAssembliesUtil.genStaffPayServiceAssembly(staffPayZipFile, 1, 1);
        File file = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        assertEquals(1, file.listFiles().length);
    }

}

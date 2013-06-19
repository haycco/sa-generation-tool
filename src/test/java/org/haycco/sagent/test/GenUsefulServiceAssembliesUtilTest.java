package org.haycco.sagent.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;
import org.haycco.sagent.main.GenUsefulServiceAssembliesUtil;
import org.haycco.sagent.util.GenServiceAssemblyUtil;
import org.haycco.sagent.util.URLUtil;
import org.junit.Before;
import org.junit.Test;

public class GenUsefulServiceAssembliesUtilTest {

    private final static String RES_URL = URLUtil.getClassPath(GenUsefulServiceAssembliesUtilTest.class)
            + "\\testcase\\";

    @Before
    public void setUp() throws Exception {
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
    public void testGenSgccServiceAssembly() throws IOException, DocumentException {
        String sgccZipFile = RES_URL + "sgcc-wbs-report.zip";
        GenUsefulServiceAssembliesUtil.genSgccServiceAssembly(sgccZipFile, 3, 1);
        File file = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        assertEquals(3, file.listFiles().length);
    }

    @Test
    public void testGenUniqeInfoReportServiceAssembly() throws IOException, DocumentException {
        String uniqInfoReportZipFile = RES_URL + "jidian-uniqInfoReportIndex-access.zip";
        GenUsefulServiceAssembliesUtil.genUniqeInfoReportServiceAssembly(uniqInfoReportZipFile, 1, 1);
        File file = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        assertEquals(1, file.listFiles().length);
    }

    @Test
    public void testGenStaffPayServiceAssembly() throws IOException, DocumentException {
        String staffPayZipFile = RES_URL + "guowang-staff-pay.zip";
        GenUsefulServiceAssembliesUtil.genStaffPayServiceAssembly(staffPayZipFile, 1, 1);
        File file = new File(GenServiceAssemblyUtil.TEMP_DIRECTORY);
        assertEquals(1, file.listFiles().length);
    }

}

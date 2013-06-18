package org.haycco.sagent.test;

import java.util.Map;

import junit.framework.TestCase;

import org.haycco.sagent.util.URLUtil;
import org.haycco.sagent.util.ZipUtil;
import org.junit.Test;

public class ZipUtilTest extends TestCase {

    private final static String RES_URL = URLUtil.getClassPath(ZipUtilTest.class)+ "\\template\\";

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testGetServiceUnitComponentNameMap() {
        String staffPayZipFile = RES_URL + "guowang-staff-pay.zip";
        Map<String, String> staffPaySuNameMap = ZipUtil.getServiceUnitComponentNameMap(staffPayZipFile);
        assertNotNull(staffPaySuNameMap);
        assertEquals(4, staffPaySuNameMap.size());
        assertTrue(staffPaySuNameMap.keySet().contains("servicemix-bean"));
        assertTrue(staffPaySuNameMap.keySet().contains("servicemix-jsr181"));
        assertTrue(staffPaySuNameMap.keySet().contains("servicemix-http"));
        assertTrue(staffPaySuNameMap.keySet().contains("servicemix-camel"));
        
        String mdmZipFile = RES_URL + "guowang-mdm-service.zip";
        Map<String, String> mdmSuNameMap = ZipUtil.getServiceUnitComponentNameMap(mdmZipFile);
        assertNotNull(mdmSuNameMap);
        assertEquals(3, mdmSuNameMap.size());
        assertTrue(mdmSuNameMap.keySet().contains("servicemix-jsr181"));
        assertTrue(mdmSuNameMap.keySet().contains("servicemix-http"));
        assertTrue(mdmSuNameMap.keySet().contains("servicemix-camel"));
        assertFalse(mdmSuNameMap.keySet().contains("servicemix-bean"));
    }

}

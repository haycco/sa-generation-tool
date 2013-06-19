package org.haycco.sagent.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.haycco.sagent.util.URLUtil;
import org.haycco.sagent.util.ZipUtil;
import org.junit.Test;

public class ZipUtilTest {

    private final static String RES_URL = URLUtil.getClassPath(ZipUtilTest.class)+ "\\template\\";

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
    
    @Test(expected=RuntimeException.class)
    public void testGetServiceUnitComponentNameMapFail() {
        String mdmZipFile = RES_URL + "fail.zip";
        Map<String, String> mdmSuNameMap = ZipUtil.getServiceUnitComponentNameMap(mdmZipFile);
        assertNull(mdmSuNameMap);
    }

}

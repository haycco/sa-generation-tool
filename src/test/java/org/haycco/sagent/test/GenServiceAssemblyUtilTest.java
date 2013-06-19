package org.haycco.sagent.test;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.haycco.sagent.util.GenServiceAssemblyUtil;
import org.haycco.sagent.util.URLUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenServiceAssemblyUtilTest {
    
    private final static String RES_URL = URLUtil.getClassPath(GenServiceAssemblyUtilTest.class)
            + "\\testcase\\";

    @Before
    public void setUp() throws Exception {
        GenServiceAssemblyUtil.cleanTempDirectory();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected=RuntimeException.class)
    public void testGenServiceAssemblyWrongSAWithFail() throws IOException, DocumentException {
        String saZipFile = RES_URL + "wrong-sa.zip";
        GenServiceAssemblyUtil.genServiceAssembly(saZipFile, 1, 1);
    }

}

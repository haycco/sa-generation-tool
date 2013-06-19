package org.haycco.sagent.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Logger;

import org.haycco.sagent.util.URLUtil;
import org.junit.Test;

public class URLUtilTest {

    private final static Logger log = Logger.getAnonymousLogger();

    @Test
    public void testGetClassFile() {
        File file = URLUtil.getClassFile(URLUtilTest.class);
        assertNotNull(file);
        assertEquals("URLUtilTest.class", file.getName());
    }

    @Test
    public void testGetClassFilePath() {
        String filePath = URLUtil.getClassFilePath(URLUtilTest.class);
        assertNotNull(filePath);
        assertNotSame("", filePath);
        boolean flag = filePath.indexOf("target\\test-classes") >= 0;
        assertTrue(flag);
        log.info(filePath);
    }

    @Test
    public void testGetClassPathFile() {
        File file = URLUtil.getClassPathFile(URLUtilTest.class);
        assertNotNull(file);
        assertEquals("test-classes", file.getName());
    }

    @Test
    public void testGetClassPath() {
        String classPath = URLUtil.getClassPath(URLUtilTest.class);
        assertNotNull(classPath);
        assertNotSame("", classPath);
        boolean flag = classPath.indexOf("target\\test-classes") >= 0;
        assertTrue(flag);
        log.info(classPath);
    }

}

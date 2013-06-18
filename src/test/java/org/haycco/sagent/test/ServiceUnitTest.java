package org.haycco.sagent.test;


import org.haycco.sagent.util.ServiceUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ServiceUnitTest extends TestCase {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGetName() {
        assertEquals("JSR181", ServiceUnit.JSR181.name());
        assertEquals("servicemix-jsr181", ServiceUnit.JSR181.getName());
        ServiceUnit.JSR181.setName("jsr");
        assertEquals("JSR181", ServiceUnit.JSR181.name());
        assertEquals("jsr", ServiceUnit.JSR181.getName());
    }

}

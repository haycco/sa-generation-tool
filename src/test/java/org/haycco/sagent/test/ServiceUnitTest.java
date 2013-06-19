package org.haycco.sagent.test;

import static org.junit.Assert.assertEquals;

import org.haycco.sagent.util.ServiceUnit;
import org.junit.Before;
import org.junit.Test;

public class ServiceUnitTest {

    @Before
    public void setUp() throws Exception {
        ServiceUnit.resetSUDefaultValue();
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

package com.ltsllc.miranda.util;

import com.ltsllc.miranda.property.MirandaProperties;
import com.ltsllc.miranda.servlet.property.Property;
import com.ltsllc.miranda.test.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Clark on 3/25/2017.
 */
public class TestPropertiesUtils extends TestCase{
    public class LocalLogger {
        public void info (String message) {

        }
    }
    private PropertiesUtils propertiesUtils;

    public PropertiesUtils getPropertiesUtils() {
        return propertiesUtils;
    }

    public void reset () {
        super.reset();

        propertiesUtils = null;
    }

    @Before
    public void setup () {
        reset();

        super.setup();

        propertiesUtils = new PropertiesUtils();
    }

    @Test
    public void testBuildFrom () {
        Properties properties = getPropertiesUtils().buildFrom(MirandaProperties.DEFAULT_PROPERTIES);

        assert (properties.getProperty(MirandaProperties.PROPERTY_CLUSTER_TIMEOUT).equals(MirandaProperties.DEFAULT_CLUSTER_TIMEOUT));
    }

    @Test
    public void testLog () {
        setuplog4j();

        Properties properties = PropertiesUtils.buildFrom(MirandaProperties.DEFAULT_PROPERTIES);

        getPropertiesUtils().log(properties);
    }

    @After
    public void cleanup () {
        deleteFile(TEST_FILE_NAME);
    }

    public static final String TEST_FILE_NAME = "test.properties";
    public static final String[] TEST_FILE_CONTENTS = {
            MirandaProperties.PROPERTY_CLUSTER_TIMEOUT + " = 10",
            MirandaProperties.PROPERTY_DELIVERY_DIRECTORY + " = whatever"
    };

    @Test
    public void testLoad () throws IOException {
        createFile(TEST_FILE_NAME, TEST_FILE_CONTENTS);
        Properties properties = getPropertiesUtils().load(TEST_FILE_NAME);

        assert (properties.getProperty(MirandaProperties.PROPERTY_CLUSTER_TIMEOUT).equals("10"));
        assert (properties.getProperty(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY).equals("whatever"));
    }

    public static final String[][] FIRST_SET = {
            {MirandaProperties.PROPERTY_DELIVERY_DIRECTORY, MirandaProperties.DEFAULT_DELIVERY_DIRECTORY},
            {MirandaProperties.PROPERTY_CLUSTER_TIMEOUT, MirandaProperties.DEFAULT_CLUSTER_TIMEOUT}
    };

    public static final String[][] SECOND_SET = {
            {MirandaProperties.PROPERTY_CERTIFICATE_ALIAS, MirandaProperties.DEFAULT_CERTIFICATE_ALIAS},
            {MirandaProperties.PROPERTY_CLUSTER_TIMEOUT, "10"}
    };

    @Test
    public void testMerge () {
        Properties p1 = getPropertiesUtils().buildFrom(FIRST_SET);
        Properties p2 = getPropertiesUtils().buildFrom(SECOND_SET);

        Properties pMerged = getPropertiesUtils().merge(p1, p2);

        assert (pMerged.getProperty(MirandaProperties.PROPERTY_CLUSTER_TIMEOUT).equals(MirandaProperties.DEFAULT_CLUSTER_TIMEOUT));
        assert (pMerged.getProperty(MirandaProperties.PROPERTY_CERTIFICATE_ALIAS).equals(MirandaProperties.DEFAULT_CERTIFICATE_ALIAS));
    }

    @Test
    public void testOverwrite () {
        Properties p1 = getPropertiesUtils().buildFrom(FIRST_SET);
        Properties p2 = getPropertiesUtils().buildFrom(SECOND_SET);

        Properties pOverwrite = getPropertiesUtils().overwrite(p1, p2);

        assert (pOverwrite.getProperty(MirandaProperties.PROPERTY_CLUSTER_TIMEOUT).equals("10"));
        assert (pOverwrite.getProperty(MirandaProperties.PROPERTY_CERTIFICATE_ALIAS).equals(MirandaProperties.DEFAULT_CERTIFICATE_ALIAS));
    }

    @Test
    public void testCopy () {
        Properties p1 = getPropertiesUtils().buildFrom(FIRST_SET);
        Properties pCopy = getPropertiesUtils().copy(p1);

        pCopy.setProperty(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY, "whatever");

        assert (p1.getProperty(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY).equals(MirandaProperties.DEFAULT_DELIVERY_DIRECTORY));
        assert (pCopy.getProperty(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY).equals("whatever"));
    }

    @Test
    public void testDifference () {
        Properties p1 = getPropertiesUtils().buildFrom(FIRST_SET);
        Properties p2 = getPropertiesUtils().buildFrom(SECOND_SET);

        Properties pDifference = getPropertiesUtils().difference(p1, p2);

        assert (pDifference.size() == 2);
        assert (pDifference.getProperty(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY).equals(MirandaProperties.DEFAULT_DELIVERY_DIRECTORY));
        assert (pDifference.getProperty(MirandaProperties.PROPERTY_CERTIFICATE_ALIAS).equals(MirandaProperties.DEFAULT_CERTIFICATE_ALIAS));
    }

    public boolean contains (Object o, List l) {
        for (Object member : l)
            if (member.equals(o))
                return true;

        return false;
    }

    @Test
    public void testToPropertyList () {
        Properties properties = getPropertiesUtils().buildFrom(FIRST_SET);

        Property property1 = new Property(MirandaProperties.PROPERTY_DELIVERY_DIRECTORY, MirandaProperties.DEFAULT_DELIVERY_DIRECTORY);
        Property property2 = new Property(MirandaProperties.PROPERTY_CLUSTER_TIMEOUT, MirandaProperties.DEFAULT_CLUSTER_TIMEOUT);

        List<Property> list = getPropertiesUtils().toPropertyList(properties);

        assert (list.size() == 2);
        assert (contains(property1, list));
        assert (contains(property2, list));
    }
}

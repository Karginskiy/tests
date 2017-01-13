import exceptions.InvalidIpAddressException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

public class IpAddressTest {
    private final ArrayList<String> validIps = new ArrayList<>();
    private final ArrayList<String> invalidIps = new ArrayList<>();
    private Logger logger = Logger.getLogger(IpAddressTest.class.getName());


    @Before
    public void fillValidIps() {
        validIps.add("127.0.0.1");
        validIps.add("255.255.255.255");
        validIps.add("192.168.0.1");
        validIps.add("242.121.0.128");
        validIps.add("1.0.127.12");
    }

    @Before
    public void fillInvalidIps() {
        invalidIps.add("256.255.255.1");
        invalidIps.add("127.0.0.256");
        invalidIps.add("12552.124.22.1");
        invalidIps.add("-1.2.5.-5");
        invalidIps.add("fdasd.asdasf.as.fdsf");
        invalidIps.add("1l2.lll.l1l.1ll");
        invalidIps.add("aas.sss.fff.sas");
        invalidIps.add("....");
    }

    @After
    public void clearLists() {
        validIps.clear();
        invalidIps.clear();
    }

    @Test
    public void testValidateIp() throws ReflectiveOperationException {

        Method method = IpAddress.class.getDeclaredMethod("validateIp", String.class);
        method.setAccessible(true);

        for (String string : validIps) {
            logger.info(String.format("Testing valid ip: %s", string));
            assertEquals(method.invoke(IpAddress.class.newInstance(), string), true);
        }

        for (String string : invalidIps) {
            logger.info(String.format("Testing invalid ip: %s", string));
            assertEquals(method.invoke(IpAddress.class.newInstance(), string), false);
        }

        logger.info("IpAddress.validateId() is ok!");
    }

    @Test
    public void parseStringValueTest() throws InvalidIpAddressException, ReflectiveOperationException {
        Method method = IpAddress.class.getDeclaredMethod("parseStringValue", String.class);
        method.setAccessible(true);
        assertArrayEquals((int[]) method.invoke(IpAddress.class.newInstance(), "127.0.0.1"),
                new int[] {127, 0, 0, 1});

        assertArrayEquals((int[]) method.invoke(IpAddress.class.newInstance(), "255.255.255.255"),
                new int[] {255, 255, 255, 255});
    }

}

import exceptions.InvalidIpAddressException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.logging.Logger;

public class IpAddressRangeIteratorTest {

    private Iterator<IpAddress> iterator;
    private Logger logger = Logger.getLogger(IpAddressRangeIteratorTest.class.getName());

    @Before
    public void initIterator() {
        try {
            IpAddress firstAddress = new IpAddress("127.0.0.1");
            IpAddress secondAddress = new IpAddress("255.255.255.255");
            iterator = firstAddress.rangeIterator(secondAddress);
        } catch (InvalidIpAddressException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void clearIterator() {
        iterator = null;
    }

    @Test
    public void testNext() {
        try {
            assertEquals(iterator.next(), new IpAddress("127.0.0.2"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.3"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.4"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.5"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.6"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.7"));
            assertEquals(iterator.next(), new IpAddress("127.0.0.8"));
            logger.info("IpAddressRangeIterator.next() is ok!");
        } catch (InvalidIpAddressException e) {
            throw new RuntimeException(e);
        }
    }

}

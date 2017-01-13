import exceptions.InvalidIpAddressException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddress {

    private String strValue;
    private int[] intValues = new int[4];
    private final String ipRegexPattern = "^(2[0-4]\\d|[01]?\\d\\d?|25[0-5])" +
            "\\.(2[0-4]\\d|[01]?\\d\\d?|25[0-5])" +
            "\\.(2[0-4]\\d|[01]?\\d\\d?|25[0-5])" +
            "\\.(2[0-4]\\d|[01]?\\d\\d?|25[0-5])";

    public IpAddress(String ip) throws InvalidIpAddressException {
        if (!validateIp(ip)) throw new InvalidIpAddressException();
        this.strValue = ip;
        parseStringValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IpAddress address = (IpAddress) o;

        return strValue != null ? strValue.equals(address.strValue) : address.strValue == null;
    }

    @Override
    public int hashCode() {
        return strValue != null ? strValue.hashCode() : 0;
    }

    /*
            Default constructor for reflective instantiation in test classes.
         */
    public IpAddress() {}

    public void setStrValue(String strValue) throws InvalidIpAddressException {
        if (!validateIp(strValue)) throw new InvalidIpAddressException();
        this.strValue = strValue;
        parseStringValue();
    }

    public String getStrValue() {
        return strValue;
    }

    private int[] getIntValues() {
        return intValues;
    }

    private void parseStringValue() {
        Pattern pattern = Pattern.compile(ipRegexPattern);
        Matcher matcher = pattern.matcher(this.strValue);
        while (matcher.find()) {
            intValues[0] = Integer.parseInt(matcher.group(1));
            intValues[1] = Integer.parseInt(matcher.group(2));
            intValues[2] = Integer.parseInt(matcher.group(3));
            intValues[3] = Integer.parseInt(matcher.group(4));
        }
    }

    private boolean validateIp(String ip) {
        return ip.matches(ipRegexPattern);
    }

    @Override
    public String toString() {
        return strValue;
    }

    public IpAddressRangeIterator rangeIterator(IpAddress toIp) {
        return new IpAddressRangeIterator(toIp);
    }

    private class IpAddressRangeIterator implements Iterator<IpAddress> {

        private IpAddress secondValue;

        private IpAddressRangeIterator(IpAddress ipAddress) {
            this.secondValue = ipAddress;
        }

        public boolean hasNext() {
            return !(intValues[0] == secondValue.getIntValues()[0] &&
                     intValues[1] == secondValue.getIntValues()[1] &&
                     intValues[2] == secondValue.getIntValues()[2] &&
                     intValues[3] + 1 == secondValue.getIntValues()[3]);
        }

        public IpAddress next() {
            if (hasNext()) {
                if (intValues[3] == 255) {
                    intValues[3] = 0;
                    intValues[2]++;
                } else if (intValues[2] == 255) {
                    intValues[2] = 0;
                    intValues[1]++;
                } else if (intValues[1] == 255) {
                    intValues[1] = 0;
                    intValues[0]++;
                } else {
                    intValues[3]++;
                }
                strValue = String.format("%d.%d.%d.%d",
                        intValues[0],
                        intValues[1],
                        intValues[2],
                        intValues[3]);

                return IpAddress.this;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

}

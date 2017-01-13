import java.util.*;
import java.util.logging.Logger;

public class PhoneBook implements Iterable<Map.Entry<String, ArrayList<String>>> {

    private Logger logger = Logger.getLogger(PhoneBook.class.getName());
    private HashMap<String, ArrayList<String>> phonesByNames = new HashMap<>();

    public void addPerson(String name, String... phones) {
        for (String phone : phones) {
            if (!validateNumber(phone)) {
                logger.warning(phone + " is not valid phone number");
            }
        }
        phonesByNames.put(name, new ArrayList<>(Arrays.asList(phones)));
    }

    public ArrayList<String> getPhonesByName(String name) {
        return phonesByNames.get(name);
    }

    private boolean validateNumber(String number) {
        return number.matches("^\\+(\\d{1,3})(\\s|\\-)+" +
                "((\\d{1,4})(\\s|\\-)+)" +
                "(\\d|\\s|\\-){1,12}");
    }

    @Override
    public Iterator<Map.Entry<String, ArrayList<String>>> iterator() {
        return phonesByNames.entrySet().iterator();
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class PhoneBookTest {

    private Logger logger = Logger.getLogger(PhoneBookTest.class.getName());
    private ArrayList<String> validNumbers;
    private ArrayList<String> invalidNumbers;
    private PhoneBook phoneBook;

    @Before
    public void initTestData() {
        validNumbers = new ArrayList<>();
        validNumbers.add("+7 988 2550860");
        validNumbers.add("+38 988 2550860");
        validNumbers.add("+26 9248 2250860");
        validNumbers.add("+3 988 2550860");
        validNumbers.add("+12 988 2550860");
        validNumbers.add("+12-988 55860");
        validNumbers.add("+12-922-882550860");
        validNumbers.add("+12-988-222-860");

        invalidNumbers = new ArrayList<>();
        invalidNumbers.add("+7 asfasf asfasfasf");
        invalidNumbers.add("----------------");
        invalidNumbers.add("125532523523523523");
        invalidNumbers.add("+7 asfasf asfasfasf");

        phoneBook = new PhoneBook();
        phoneBook.addPerson("Иванов И.И.", "+7 988 255 08 60", "+92 2441 235 23 23");
        phoneBook.addPerson("Петров И.И.", "+92 2441 235 23 23");
        phoneBook.addPerson("Соколов И.И.", "+7 988 234 08 60", "+92 2441 235 23 23", "+12 424 542 231 43");
    }

    @After
    public void clearTestData() {
        validNumbers.clear();
        invalidNumbers.clear();
        phoneBook = null;
    }

    @Test
    public void validateNumberTest() throws ReflectiveOperationException {
        Method validateNumberMethod = PhoneBook.class.getDeclaredMethod("validateNumber", String.class);
        validateNumberMethod.setAccessible(true);
        for (String number : validNumbers) {
            logger.info(String.format("Testing valid number: %s", number));
            assertEquals(validateNumberMethod.invoke(PhoneBook.class.newInstance(), number), true);
        }
        for (String number : invalidNumbers) {
            logger.info(String.format("Testing invalid number: %s", number));
            assertEquals(validateNumberMethod.invoke(PhoneBook.class.newInstance(), number), false);
        }
    }

    @Test
    public void getPhonesByNameTest() {
        assertEquals(phoneBook.getPhonesByName("Иванов И.И."), Arrays.asList("+7 988 255 08 60", "+92 2441 235 23 23"));
        assertEquals(phoneBook.getPhonesByName("Петров И.И."), Collections.singletonList("+92 2441 235 23 23"));
        assertEquals(phoneBook.getPhonesByName("Соколов И.И."), Arrays.asList("+7 988 234 08 60", "+92 2441 235 23 23", "+12 424 542 231 43"));
    }

}

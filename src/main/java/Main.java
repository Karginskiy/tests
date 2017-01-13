import exceptions.InvalidIpAddressException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Write two ip-addresses: ");
        /*
            Charset forName is for working with Windows Console
            By default can be used the default value.
         */
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("cp866")))){
            IpAddress firstIp = new IpAddress(reader.readLine());
            IpAddress secondIp = new IpAddress(reader.readLine());
            Iterator<IpAddress> iterator = firstIp.rangeIterator(secondIp);
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            System.out.println(System.lineSeparator());

            PhoneBook phoneBook = new PhoneBook();
            phoneBook.addPerson("Иванов И.И.", "+7 988 255 08 60", "+92 2441 235 23 23");
            phoneBook.addPerson("Петров И.И.", "+92 2441 235 23 23");
            phoneBook.addPerson("Соколов И.И.", "+7 988 234 08 60", "+92 2441 235 23 23", "+12 424 542 231 43");
            System.out.println("Welcome to the PhoneBook. Write the contact's name: ");
            String name = reader.readLine();
            ArrayList<String> numbers = phoneBook.getPhonesByName(name);
            if (numbers == null) {
                System.out.println("No contact with that name.");
            } else {
                numbers.stream().forEach(System.out::println);
            }
        } catch (IOException |InvalidIpAddressException e) {
            throw new RuntimeException(e);
        }
    }
}

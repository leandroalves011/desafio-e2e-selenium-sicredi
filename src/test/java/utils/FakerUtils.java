
package utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    public static String zipCode() {
        return faker.address().zipCode();
    }
}

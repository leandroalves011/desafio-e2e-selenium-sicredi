package data;

import utils.FakerUtils;

public class TestDataFactory {

    public static String firstName() {
        return FakerUtils.firstName();
    }

    public static String lastName() {
        return FakerUtils.lastName();
    }

    public static String zipCode() {
        return FakerUtils.zipCode();
    }

}
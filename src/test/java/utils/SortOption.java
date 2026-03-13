package utils;

public enum SortOption {

    NAME_ASC("Name (A to Z)"),
    NAME_DESC("Name (Z to A)"),
    PRICE_LOW_HIGH("Price (low to high)"),
    PRICE_HIGH_LOW("Price (high to low)");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

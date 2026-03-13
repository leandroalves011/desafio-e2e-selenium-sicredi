package utils;

public enum UserType {

    STANDARD("standard_user"),
    PROBLEM("problem_user");

    private final String username;

    UserType(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

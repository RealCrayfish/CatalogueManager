package org.cataloguemanager.models;

public class User {
    private String name;
    private String userId;
    private String email;
    private String phoneNumber; // Phone number is a string due to them being 11 digit character sequences rather than an actual number.

    /**
     * User default constructor. Not recommended for use. Prefer using getters and setters
     * @param name
     * @param userId
     * @param email
     * @param phoneNumber
     */
    public User(String name, String userId, String email, String phoneNumber) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { // NOTE: Not implementing name validation as it would bbe too complex for the assignment.
        this.name = name;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String id) { // TODO: Implement id validation using regex and a defined ID format. Ask professor for guidance.
        this.userId = id;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) { // TODO: Implement email validation using regex.
        this.email = email;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String number) { // TODO: Implement phone number validation using google's libphonenumber library
        this.phoneNumber = number;
    }
}

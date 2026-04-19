package org.cataloguemanager.models;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class Student extends User {
    public Student(String name, String memberId, String email, PhoneNumber phoneNumber) {
        super(name, memberId, email, phoneNumber);
    }

    public Student() {}
}

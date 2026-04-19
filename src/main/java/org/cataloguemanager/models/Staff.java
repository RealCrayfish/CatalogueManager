package org.cataloguemanager.models;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class Staff extends User {
    public Staff(String name, String memberId, String email, PhoneNumber phoneNumber) {
        super(name, memberId, email, phoneNumber);
    }

    public Staff() {}
}

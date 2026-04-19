package org.cataloguemanager.models;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.cataloguemanager.App;
import org.cataloguemanager.utils.TerminalUtils;

public class User {
    private String name;
    private String userId;
    private String email;
    private PhoneNumber phoneNumber;

    public User(String name, String userId, String email, PhoneNumber phoneNumber) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    } // Validated at input via the prompt methods.


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    } // Validated at input via the prompt methods.


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    } // Validated at input via the prompt methods.


    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPhoneNumberString() {
        return App.phoneUtil.format(this.phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

    public void setPhoneNumber(PhoneNumber number) {
        this.phoneNumber = number;
    } // Validated at input via the prompt methods.


    /**
     * Returns the user as an array of Strings. This should only be used alongside tableBuilder.
     * @return The user as an array of Strings.
     */
    public String[] toArray() {
        String[] array = new String[4];
        array[0] = this.getName();
        array[1] = this.getUserId();
        array[2] = this.getEmail();
        array[3] = this.getPhoneNumberString();
        return array;
    }


    public static User fromJSON(JsonObject jsonObject) {
        User user;
        if (jsonObject.get("userType").equals("student")) { user = new Student(); }
        else if (jsonObject.get("userType").equals("staff")) { user = new Staff(); }
        else { user = new User(); }
        user.setUserId((String)  jsonObject.get("userId"));
        user.setName((String)  jsonObject.get("name"));
        user.setEmail((String)  jsonObject.get("email"));
        try {
            user.setPhoneNumber(App.phoneUtil.parse(((String)jsonObject.get("phoneNumber")), "GB"));
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public JsonObject toJSON() {
        JsonObject jsonObject = new JsonObject();
        if (this instanceof Student) { jsonObject.put("userType", "student"); }
        else if (this instanceof Staff) { jsonObject.put("userType", "staff"); }
        else { jsonObject.put("userType", "user"); }
        jsonObject.put("userId", getUserId());
        jsonObject.put("name", getName());
        jsonObject.put("email", getEmail());
        jsonObject.put("phoneNumber", App.phoneUtil.format(getPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        return jsonObject;
    }
}

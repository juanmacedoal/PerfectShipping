package Contact;

/**
 * Created by juanmacedo on 1/9/17.
 */

public class ContactsData{

    String name, phone, email, state, city, street, code;

    public ContactsData(String name, String phone, String email, String state, String city, String street, String code) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.city = city;
        this.street = street;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

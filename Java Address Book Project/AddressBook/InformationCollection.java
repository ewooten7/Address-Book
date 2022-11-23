package AddressBook;

//Class of infromation to be used in address book. This will be useful for the opening Array List, etc.

public class InformationCollection {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public InformationCollection(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @java.lang.Override
    public java.lang.String toString() {
        return "Information{" +
                "\n\tfirstName='" + firstName +
                "\n\tlastName='" + lastName +
                "\n\tPhone='" + phone +
                "\n\temail='" + email +
                '}';
    }

    public String getPhoneNumber() {
        return null;
    }
}
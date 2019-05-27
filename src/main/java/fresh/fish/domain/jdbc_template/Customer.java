package fresh.fish.domain.jdbc_template;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class Customer {

    private Long customerId;
    private Long userId;
    private String name;
    private String surname;
    private Timestamp birthDate;
    private Enum gender;


    private String email;
    private String phone_number;
    private String adress;

    private Timestamp date_created;
    private Timestamp date_modificate;

    public Customer() {
    }

    public Customer(Long customerId, Long userId, String name, String surname, Timestamp birthDate, Enum gender, String email, String phone_number, String adress, Timestamp date_created, Timestamp date_modificate) {
        this.customerId = customerId;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.phone_number = phone_number;
        this.adress = adress;
        this.date_created = date_created;
        this.date_modificate = date_modificate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public Timestamp getDate_modificate() {
        return date_modificate;
    }

    public void setDate_modificate(Timestamp date_modificate) {
        this.date_modificate = date_modificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(userId, customer.userId) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(birthDate, customer.birthDate) &&
                Objects.equals(gender, customer.gender) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone_number, customer.phone_number) &&
                Objects.equals(adress, customer.adress) &&
                Objects.equals(date_created, customer.date_created) &&
                Objects.equals(date_modificate, customer.date_modificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, userId, name, surname, birthDate, gender, email, phone_number, adress, date_created, date_modificate);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}



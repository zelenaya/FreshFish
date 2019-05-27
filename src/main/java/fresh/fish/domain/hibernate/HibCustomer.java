package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class HibCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long customerId;

    @Column(name = "cust_user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    @Column(name = "gender")
    private Enum gender;

    @Column(name = "e-mail")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "adress")
    private String adress;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "date_modificate")
    private Timestamp date_modificate;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<HibOrder> orders = Collections.emptySet();

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "orderProduct")
//    private Set<HibWaitingList> hibWaitingLists = Collections.emptySet();

    public HibCustomer() {
    }
//, Set<HibWaitingList> hibWaitingLists
    public HibCustomer(Long userId, String name, String surname, Timestamp birthDate, Enum gender, String email, String phone_number, String adress, Timestamp date_created, Timestamp date_modificate, Set<HibOrder> orders) {
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
        this.orders = orders;
      //  this.hibWaitingLists = hibWaitingLists;
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

    public Set<HibOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<HibOrder> orders) {
        this.orders = orders;
    }

    //    public Set<HibWaitingList> getHibWaitingLists() {
//        return hibWaitingLists;
//    }
//
//    public void setHibWaitingLists(Set<HibWaitingList> hibWaitingLists) {
//        this.hibWaitingLists = hibWaitingLists;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibCustomer that = (HibCustomer) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone_number, that.phone_number) &&
                Objects.equals(adress, that.adress) &&
                Objects.equals(date_created, that.date_created) &&
                Objects.equals(date_modificate, that.date_modificate) &&
                Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, userId, name, surname, birthDate, gender, email, phone_number, adress, date_created, date_modificate, orders);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}



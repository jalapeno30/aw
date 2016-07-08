package com.ilts.anywhere.authentication.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilts.anywhere.betting.model.Bet;
import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@JsonDeserialize
@Entity
@Table(name = "users", catalog = "lottery", schema = "")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
//    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
//    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName"),
//    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
//    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
//    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
//    @NamedQuery(name = "Users.findByBirthDate", query = "SELECT u FROM Users u WHERE u.birthDate = :birthDate"),
//    @NamedQuery(name = "Users.findByGender", query = "SELECT u FROM Users u WHERE u.gender = :gender"),
//    @NamedQuery(name = "Users.findByEmailId", query = "SELECT u FROM Users u WHERE u.emailId = :emailId"),
//    @NamedQuery(name = "Users.findByPhoneNumber", query = "SELECT u FROM Users u WHERE u.phoneNumber = :phoneNumber"),
//    @NamedQuery(name = "Users.findByAccountNumber", query = "SELECT u FROM Users u WHERE u.accountNumber = :accountNumber"),
//    @NamedQuery(name = "Users.findByAddress1", query = "SELECT u FROM Users u WHERE u.address1 = :address1"),
//    @NamedQuery(name = "Users.findByAddress2", query = "SELECT u FROM Users u WHERE u.address2 = :address2"),
//    @NamedQuery(name = "Users.findByCity", query = "SELECT u FROM Users u WHERE u.city = :city"),
//    @NamedQuery(name = "Users.findByState", query = "SELECT u FROM Users u WHERE u.state = :state"),
//    @NamedQuery(name = "Users.findByZipcode", query = "SELECT u FROM Users u WHERE u.zipcode = :zipcode"),
//    @NamedQuery(name = "Users.findByCountry", query = "SELECT u FROM Users u WHERE u.country = :country")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
//       @Id
//    @Column(name = "user_id", unique = true)
//    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "before_insert_users_id")
//    @GenericGenerator( 
//    name="before_insert_users_id", strategy="org.hibernate.id.SelectGenerator",
//    parameters = {
//        @org.hibernate.annotations.Parameter( name="keys", value="userName" )
//    }
//)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.GUIDGenerator")
    @Column(name = "user_id", unique = true)
    private String userId;
//    @NaturalId
    @JsonProperty("user_name")
    @Basic(optional = false)
    @Column(name = "user_name", unique = true)
    private String userName;
    @JsonProperty("password")
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @JsonProperty("first_name")
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @JsonProperty("last_name")
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @JsonProperty("birth_date")
    @Column(name = "birth_date")
    private String birthDate;
    @JsonProperty("gender")
    @Column(name = "gender")
    private String gender;
    @JsonProperty("email_id")
    @Column(name = "email_id")
    private String emailId;
    @JsonProperty("phone_number")
    @Column(name = "phone_number")
    private String phoneNumber;
    @JsonProperty("account_number")
    @Column(name = "account_number")

    private Integer accountNumber;
    @JsonProperty("address_1")
    @Column(name = "address_1")
    private String address1;
    @JsonProperty("address_2")
    @Column(name = "address_2")
    private String address2;
    @JsonProperty("city")
    @Column(name = "city")
    private String city;
    @JsonProperty("state")
    @Column(name = "state")
    private String state;
    @JsonProperty("zipcode")
    @Column(name = "zipcode")
    private Integer zipcode;
    @JsonProperty("country")
    @Column(name = "country")
    private String country;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
//    private List<PaypalPurchase> paypalPurchasesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Bet> betsList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Session> sessionsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.EAGER)
    private List<UserRoles> userRolesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<UserStatus> userStatusList;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
//    private List<Order> ordersList;
    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String userName, String password, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
//
//    @XmlTransient
//    public List<PaypalPurchase> getPaypalPurchasesList() {
//        return paypalPurchasesList;
//    }
//
//    public void setPaypalPurchasesList(List<PaypalPurchase> paypalPurchasesList) {
//        this.paypalPurchasesList = paypalPurchasesList;
//    }

    @XmlTransient
    public List<Bet> getBetsList() {
        return betsList;
    }

    public void setBetsList(List<Bet> betsList) {
        this.betsList = betsList;
    }

    @XmlTransient
    public List<Session> getSessionsList() {
        return sessionsList;
    }

    public void setSessionsList(List<Session> sessionsList) {
        this.sessionsList = sessionsList;
    }

    @XmlTransient
    public List<UserRoles> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<UserRoles> userRolesList) {
        this.userRolesList = userRolesList;
    }

    @XmlTransient
    public List<UserStatus> getUserStatusList() {
        return userStatusList;
    }

    public void setUserStatusList(List<UserStatus> userStatusList) {
        this.userStatusList = userStatusList;
    }

//    @XmlTransient
//    public List<Order> getOrdersList() {
//        return ordersList;
//    }
//
//    public void setOrdersList(List<Order> ordersList) {
//        this.ordersList = ordersList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.List_Test.Users[ userId=" + userId + " ]";
    }

}

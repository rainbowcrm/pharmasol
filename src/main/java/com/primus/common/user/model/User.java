package com.primus.common.user.model;

import com.primus.abstracts.PrimusModel;
import com.primus.admin.division.model.Division;
import com.primus.admin.role.model.Role;
import com.primus.common.company.model.Company;

import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@AttributeOverrides({
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class User extends PrimusModel{

    String userId;
    String password;
    Company company;
    boolean companyOwner ;
    String firstName;
    String lastName;
    String address1;
    String address2;
    String zipCode;
    String city;
    String phone;
    String email;
    boolean isActive;

    String prefix;
    String suffix;

    Role role ;
    Division division;

    User managerUser;

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="MANAGER_USER_ID"  )
    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public User getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(User managerUser) {
        this.managerUser = managerUser;
    }

    @Column(name  ="USER_ID")
    @Id
    @RadsPropertySet(isBK =  true , isPK = true)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name  ="PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="COMPANY_ID")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name  ="COMPANY_OWNER")
    public boolean isCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(boolean companyOwner) {
        this.companyOwner = companyOwner;
    }

    @Column(name  ="FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name  ="LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name  ="ADDRESS1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name  ="ADDRESS2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name  ="ZIPCODE")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name  ="CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name  ="PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name  ="EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name  ="IS_ACTIVE")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Transient
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Transient
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="ROLE_ID"  )
    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public Role getRole() {
        return role;
    }

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="DIVISION_ID" )
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}

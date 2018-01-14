package com.primus.common.company.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.abstracts.PrimusModel;
import com.primus.common.FiniteValue;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMPANIES")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class Company extends PrimusModel {

    int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  ="ID")
    @RadsPropertySet(isPK = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id=  id;
    }

    /*@Override
    @Column(name  ="CREATED_DATE")
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedDate(createdDate);
    }

    @Override
    @Column(name  ="IS_DELETED")
    public Boolean isDeleted() {
        return super.isDeleted();
    }

    @Override
    public void setDeleted(Boolean deleted) {
        super.setDeleted(deleted);
    }

    @Override
    @Column(name  ="LAST_UPDATED_DATE")
    public Date getLastUpdateDate() {
        return super.getLastUpdateDate();
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        super.setLastUpdateDate(lastUpdateDate);
    }

    @Override
    @Column(name  ="CREATED_BY")
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    @Column(name  ="LAST_UPDATED_BY")
    public String getLastUpdatedBy() {
        return super.getLastUpdatedBy();
    }

    @Override
    public void setLastUpdatedBy(String lastUpdatedBy) {
        super.setLastUpdatedBy(lastUpdatedBy);
    }
    */

    String code;
    String name;
    String address1;
    String address2;
    String zipCode;
    String city;
    String phone;
    FiniteValue industryType;
    FiniteValue businessType ;
    String state;
    String country ;
    String adminName;
    Integer noDivisions;
    Integer noUsers;
    String logo ;
    Date registerationDate;
    Date activationDate;
    Date serviceExpiryDate;
    Boolean isActive;

    @Column(name  ="COMPANY_CODE")
    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    @Column(name  ="COMPANY_NAME")
    @RadsPropertySet(isBK = true)
    public String getName() {
        return name;
    }

    @RadsPropertySet(isBK = true)
    public void setName(String name) {
        this.name = name;
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


    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="INDUSTRY_TYPE")
    public FiniteValue getIndustryType() {
        return industryType;
    }


    public void setIndustryType(FiniteValue industryType) {
        this.industryType = industryType;
    }


    @ManyToOne(cascade=CascadeType.DETACH)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name  ="BUSINESS_TYPE"    )
    public FiniteValue getBusinessType() {
        return businessType;
    }

    public void setBusinessType(FiniteValue businessType) {
        this.businessType = businessType;
    }

    @Column(name  ="STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Column(name  ="COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name  ="ADMIN_NAME")
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Column(name  ="NO_DIVISIONS")
    public Integer getNoDivisions() {
        return noDivisions;
    }

    public void setNoDivisions(Integer noDivisions) {
        this.noDivisions = noDivisions;
    }

    @Column(name  ="NO_USERS")
    public Integer getNoUsers() {
        return noUsers;
    }

    public void setNoUsers(Integer noUsers) {
        this.noUsers = noUsers;
    }

    @Column(name  ="LOGO")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    byte[] image;
    String fileName;
    String fileWithLink;
    String fileWithoutLink;

    String base64Image;


    @Transient
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Transient
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Transient
    public String getFileWithLink() {
        return fileWithLink;
    }

    public void setFileWithLink(String fileWithLink) {
        this.fileWithLink = fileWithLink;
    }

    @Transient
    public String getFileWithoutLink() {
        return fileWithoutLink;
    }

    public void setFileWithoutLink(String fileWithoutLink) {
        this.fileWithoutLink = fileWithoutLink;
    }

    @Transient
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }



    @Column(name  ="REGISTRATION_DATE")
    public Date getRegisterationDate() {
        return registerationDate;
    }

    public void setRegisterationDate(Date registerationDate) {
        this.registerationDate = registerationDate;
    }

    @Column(name  ="ACTIVATION_DATE")
    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    @Column(name  ="SERVICE_EXPIRY_DATE")
    public Date getServiceExpiryDate() {
        return serviceExpiryDate;
    }

    public void setServiceExpiryDate(Date serviceExpiryDate) {
        this.serviceExpiryDate = serviceExpiryDate;
    }

    @Column(name  ="IS_ACTIVE")
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}

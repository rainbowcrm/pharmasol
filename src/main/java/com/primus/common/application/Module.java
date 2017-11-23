package com.primus.common.application;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import javax.persistence.*;

@Entity
@Table(name = "MODULES")
public class Module extends ModelObject {

    String code;
    String description;
    Product product;

    public static final  String ADMIN = "ADM";
    public static final  String HRIS = "HRIS";
    public static final  String OPERATIONS = "OPER";
    public static final  String SELFSERVICE = "SELFSER";
    public static final  String FINANCE = "FINANCE";
    public static final  String MANAGERPORTAL = "MGRPORT";


    public static final  String AGENTPORTAL = "AGPORT";
    public static final  String STOCKIST = "STCK";


    public Module() {

    }
    public Module(String code) {
        this.code = code;
    }

    @Id
    @Column(name  ="CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name  ="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="PRODUCT_CODE")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

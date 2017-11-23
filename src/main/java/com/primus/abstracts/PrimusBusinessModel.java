package com.primus.abstracts;

import com.primus.common.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@MappedSuperclass
public class PrimusBusinessModel extends  PrimusModel {

    protected int id;

    Company company;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  ="ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="COMPANY_ID")
    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public Company getCompany() {
        return company;
    }

    @RadsPropertySet(useBKForJSON = true, useBKForMap = true, useBKForXML = true)
    public void setCompany(Company company) {
        this.company = company;
    }
}

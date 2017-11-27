package com.primus.merchandise.item.model;

import com.primus.abstracts.PrimusBusinessModel;

import javax.persistence.*;

@Entity
@Table(name = "SKUS")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})

public class UOM extends PrimusBusinessModel {


    String code;

    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

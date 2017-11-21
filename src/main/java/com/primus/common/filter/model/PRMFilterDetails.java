package com.primus.common.filter.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import javax.persistence.*;

/*
@Entity
@Table(name = "FILTER_DETAILS")
*/
public class PRMFilterDetails extends ModelObject {

    protected int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  ="ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String field;
    String value ;
    PRMFilter primusFilter;

    @Column(name  ="FIELD")
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Column(name  ="VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JoinColumn(nullable = false, name = "FILTER_ID")
    @ManyToOne(optional = false)
    public PRMFilter getPrimusFilter() {
        return primusFilter;
    }

    public void setPrimusFilter(PRMFilter primusFilter) {
        this.primusFilter = primusFilter;
    }
}

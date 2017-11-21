package com.primus.common;

import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FINITE_VALUES")
public class FiniteValue extends ModelObject implements Serializable,Cloneable{
    String type ;
    String  code;
    String description;
    boolean isDefault ;

    @Column(name  ="TYPE_CODE")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @RadsPropertySet(isPK=true, isBK =true)
    @Id
    @Column(name  ="CODE")
    public String getCode() {
        return code;
    }

    @RadsPropertySet(isPK=true, isBK =true)
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

    @Column(name  ="IS_DEFAULT")
    public boolean isDefault() {
        return isDefault;
    }
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    public FiniteValue(String type, String code, String description,
                       boolean isDefault) {
        super();
        this.type = type;
        this.code = code;
        this.description = description;
        this.isDefault = isDefault;
    }
    public FiniteValue() {
        super();
    }

    @Override
    public String toString() {
        return code;
    }

    public boolean equals(FiniteValue value)  {
        return value!=null&&value.getCode().equals(this.getCode());
    }

    public boolean equals(String code)  {
        return code!=null&&code.equals(this.getCode());
    }

    public FiniteValue(String code) {
        this.code = code;
    }



}


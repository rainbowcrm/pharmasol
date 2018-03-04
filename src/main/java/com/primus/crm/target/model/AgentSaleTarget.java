package com.primus.crm.target.model;


import com.primus.abstracts.PrimusBusinessModel;
import com.primus.common.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "AGENT_SALE_TARGETS")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})

public class AgentSaleTarget extends PrimusBusinessModel {

    User agent ;
    String description ;
    Target target ;
    Float targettedAmount;

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="AGENT_USER_ID")
    public User  getAgent ()
    {
        return agent;
    }

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public void setAgent ( User   val )
    {
        agent  = val;
    }



    @RadsPropertySet(excludeFromMap = true, excludeFromXML = true  , excludeFromJSON =  true)
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="TARGET_ID")
    public Target  getTarget ()
    {
        return target;
    }

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public void setTarget ( Target   val )
    {
        target  = val;
    }





    @Column(name  ="DESCRIPTION")
    public String  getDescription ()
    {
        return description;
    }


    public void setDescription ( String   val )
    {
        description  = val;
    }


    @Column(name  ="TARGETTED_AMOUNT")
    public Float getTargettedAmount() {
        return targettedAmount;
    }

    public void setTargettedAmount(Float targettedAmount) {
        this.targettedAmount = targettedAmount;
    }
}

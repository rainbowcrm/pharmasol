package com.primus.common.user.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.admin.region.model.Region;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "USER_REGIONS")
@AttributeOverrides({
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class UserRegion extends PrimusBusinessModel{

    User user ;
    Region region ;
    Boolean accessAlowed;

    @RadsPropertySet(excludeFromJSON =  true,excludeFromMap = true, excludeFromXML = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="USER_ID")
    public User getUser() {
        return user;
    }

    @RadsPropertySet(excludeFromJSON =  true,excludeFromMap = true, excludeFromXML = true )
    public void setUser(User user) {
        this.user = user;
    }

    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="REGION_ID")
    public Region getRegion() {
        return region;
    }

    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    public void setRegion(Region region) {
        this.region = region;
    }

    @Column(name  ="ACCESS_ALLOWED")
    public Boolean getAccessAlowed() {
        return accessAlowed;
    }

    public void setAccessAlowed(Boolean accessAlowed) {
        this.accessAlowed = accessAlowed;
    }
}

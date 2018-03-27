package com.primus.common.user.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.admin.zone.model.Zone;
import com.primus.common.FiniteValue;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "USER_PORTFOLIO")
@AttributeOverrides({
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class UserPortfolio extends PrimusBusinessModel {


    Zone zone;
    Region region ;
    Location location;
    FiniteValue accessLevel ;




    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="ZONE_ID")
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="REGION_ID")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="LOCATION_ID")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @RadsPropertySet(useBKForXML =  true,useBKForMap = true, useBKForJSON = true )
    @ManyToOne(cascade= CascadeType.DETACH)
    @JoinColumn(name  ="ACCESS_LEVEL")
    public FiniteValue getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(FiniteValue accessLevel) {
        this.accessLevel = accessLevel;
    }
}

package com.primus.externals.doctor.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.admin.region.model.Location;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "DOCTOR_ASSOCIATIONS")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})
public class DoctorAssociation extends PrimusBusinessModel {

    Doctor doctor ;
    String description ;
    Boolean associated;
    Location location ;


    @ManyToOne(cascade=CascadeType.DETACH  )
    @JoinColumn(name  ="DOCTOR_ID" )
    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public Doctor getDoctor() {
        return doctor;
    }

    @RadsPropertySet(excludeFromMap = true, excludeFromJSON = true, excludeFromXML = true)
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "STATUS")
    public Boolean getAssociated() {
        return associated;
    }

    public void setAssociated(Boolean associated) {
        this.associated = associated;
    }

    @ManyToOne(cascade=CascadeType.DETACH  )
    @JoinColumn(name  ="LOCATION_ID")
    @RadsPropertySet(useBKForXML = true,useBKForJSON = true, useBKForMap = true)
    public Location getLocation() {
        return location;
    }

    @RadsPropertySet(useBKForXML = true,useBKForJSON = true, useBKForMap = true)
    public void setLocation(Location location) {
        this.location = location;
    }


}

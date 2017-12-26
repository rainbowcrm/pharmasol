package com.primus.profiles.model;

import com.primus.abstracts.PrimusModel;
import com.primus.admin.region.model.Location;
import com.primus.externals.doctor.model.Doctor;
import com.primus.externals.stockist.model.Stockist;
import com.primus.externals.store.model.Store;

public class ProfileInit extends PrimusModel {


    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location val) {
        location = val;
    }

    Doctor doctor;

    Store store ;

    Stockist stockist;


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Stockist getStockist() {
        return stockist;
    }

    public void setStockist(Stockist stockist) {
        this.stockist = stockist;
    }
}

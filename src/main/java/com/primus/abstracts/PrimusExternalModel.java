package com.primus.abstracts;

import javax.persistence.*;

@MappedSuperclass
public class PrimusExternalModel extends  PrimusModel {

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


}

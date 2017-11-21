package com.primus.abstracts;

import java.util.Collection;

public class TransactionUpdateDelta {
    Collection<? extends PrimusBusinessModel> deletedRecords ;
    Collection<? extends PrimusBusinessModel> newRecords;


    public Collection<? extends PrimusBusinessModel> getDeletedRecords() {
        return deletedRecords;
    }

    public void setDeletedRecords(Collection<? extends PrimusBusinessModel> deletedRecords) {
        this.deletedRecords = deletedRecords;
    }

    public Collection<? extends PrimusBusinessModel> getNewRecords() {
        return newRecords;
    }

    public void setNewRecords(Collection<? extends PrimusBusinessModel> newRecords) {
        this.newRecords = newRecords;
    }
}

package com.primus.merchandise.item.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_COMPOSITIONS")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})

public class ItemComposition extends PrimusBusinessModel {

    Item item;
    String ingredient  ;
    double qtyPercent;
    String description  ;

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="ITEM_ID")
    public Item getItem() {
        return item;
    }

    @RadsPropertySet(useBKForJSON =  true, useBKForMap = true, useBKForXML = true)
    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name  ="INGREDIENT")
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Column(name  ="QTY_PERCENT")
    public double getQtyPercent() {
        return qtyPercent;
    }

    public void setQtyPercent(double qtyPercent) {
        this.qtyPercent = qtyPercent;
    }

    @Column(name  ="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

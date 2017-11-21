package com.primus.common.filter.model;

import com.primus.abstracts.PrimusBusinessModel;
import com.primus.abstracts.PrimusModel;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.company.model.Company;
import com.primus.common.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.controller.abstracts.ViewController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.utils.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*@Entity
@Table(name = "FILTERS")
@AttributeOverrides({
        @AttributeOverride(name="ID", column=@Column(name="ID")),
        @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE")),
        @AttributeOverride(name="createdBy", column=@Column(name="CREATED_BY")),
        @AttributeOverride(name="lastUpdateDate", column=@Column(name="LAST_UPDATED_DATE")),
        @AttributeOverride(name="lastUpdatedBy", column=@Column(name="LAST_UPDATED_BY")),
        @AttributeOverride(name="version", column=@Column(name="VERSION"))

})*/
public class PRMFilter  extends PrimusBusinessModel{

    Company company;
    User user;
    String  page;
    String name ;
    boolean isPublic;
    List<PRMFilterDetails> primusFilterDetails;


    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="COMPANY_ID")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name  ="USER_ID")
    @RadsPropertySet(isBK = true)
    public User getUser() {
        return user;
    }

    @RadsPropertySet(isBK = true)
    public void setUser(User user) {
        this.user = user;
    }

    @Column(name  ="PAGE")
    @RadsPropertySet(isBK = true)
    public String getPage() {
        return page;
    }

    @RadsPropertySet(isBK = true)
    public void setPage(String page) {
        this.page = page;
    }

    @Column(name  ="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name  ="IS_PUBLIC")
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "primusFilter")
    public List<PRMFilterDetails> getPrimusFilterDetails() {
        return primusFilterDetails;
    }

    public void setPrimusFilterDetails(List<PRMFilterDetails> primusFilterDetails) {
        this.primusFilterDetails = primusFilterDetails;
    }

    public void addPrimusFilterDetail (PRMFilterDetails primusFilterDetail)
    {
        if(primusFilterDetails  == null )
            primusFilterDetails = new ArrayList<PRMFilterDetails>() ;
        primusFilterDetails.add(primusFilterDetail);

    }


    public static PRMFilter parseRadsFilter(ViewController viewController, Filter filter) {
        PRMFilter newFilter = new  PRMFilter();
        int detailCount = 0;
        ProductContext ctx= (ProductContext)viewController.getContext() ;
        newFilter.setUser(CommonUtil.getUser(ctx.getUser()));
        newFilter.setCompany(CommonUtil.getCompany(ctx.getLoggedinCompany()));
        newFilter.setPage(viewController.getClass().getName());
        if (filter != null && !Utils.isNullList(filter.getNodeList())) {
            for (FilterNode node : filter.getNodeList()) {
                PRMFilterDetails detail = new PRMFilterDetails();
                //detail.setId(++detailCount);
                String field = node.getField();
                String value = String.valueOf(node.getValue()) ;
                detail.setField(field);
                detail.setValue(value);
                detail.setPrimusFilter(newFilter);
                if (field.equals("FilterName")) {
                    newFilter.setName(value);
                }
                newFilter.addPrimusFilterDetail(detail);
            }
        }
        return newFilter;
    }
}

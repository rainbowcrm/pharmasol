package com.primus.externals.store.controller;

import com.primus.abstracts.AbstractCRUDController;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.model.Region;
import com.primus.admin.region.service.RegionService;
import com.primus.common.CommonUtil;
import com.primus.common.ServiceFactory;
import com.primus.externals.store.model.StoreAssociation;
import com.primus.externals.store.service.StoreService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoreAssociationController extends AbstractCRUDController {

    @Override
    protected String getServiceName() {
        return "StoreService";
    }


    @Override
    protected String getValidatorName() {
        return "StoreValidator";
    }

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        if ("Save".equalsIgnoreCase(actionParam)) {
            StoreAssociation association = (StoreAssociation) object;

            StoreService service = ServiceFactory.getStoreService();
            service.associateStore(association, getProductContext());
        }
        PageResult result = new PageResult();
        result.setNextPageKey("stores");
        result.setResult(TransactionResult.Result.SUCCESS);
        return result;
    }

    public Map<String,String> getAllRegions()
    {
        Map ans = new LinkedHashMap() ;
        RegionService service = ServiceFactory.getRegionService() ;
        List<Region> regions =(List<Region> ) service.fetchAllActive(null,"name", getProductContext()) ;
        regions.forEach( region ->  {
            ans.put(String.valueOf(region.getId()),region.getName());
        });
        return ans;

    }

    public Map<String,String> getAllLocations()
    {
        Map ans = new LinkedHashMap() ;
        RegionService service = ServiceFactory.getRegionService() ;
        List<Location> locations =(List<Location> ) service.fetchAllActive("com.primus.admin.region.model.Location",null,"name", getProductContext()) ;
        locations.forEach( location ->  {
            ans.put(String.valueOf(location.getId()),location.getName());
        });
        return ans;

    }
}

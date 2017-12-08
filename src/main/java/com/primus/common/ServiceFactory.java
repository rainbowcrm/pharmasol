package com.primus.common;

import com.primus.admin.department.model.Department;
import com.primus.admin.department.service.DepartmentService;
import com.primus.admin.division.model.Division;
import com.primus.admin.division.service.DivisionService;
import com.primus.admin.reasoncode.service.ReasonCodeService;
import com.primus.admin.region.model.Location;
import com.primus.admin.region.service.RegionService;
import com.primus.admin.role.service.RoleService;
import com.primus.admin.zone.service.ZoneService;
import com.primus.common.company.service.CompanyService;
import com.primus.common.filter.service.FilterService;
import com.primus.common.login.service.LoginService;
import com.primus.common.user.service.UserService;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.crm.appointment.service.AppointmentTemplateService;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.stockist.service.StockistService;
import com.primus.externals.store.service.StoreService;
import com.primus.merchandise.category.service.CategoryService;
import com.primus.merchandise.item.service.ItemService;
import com.primus.merchandise.item.service.SkuService;
import com.primus.merchandise.item.service.UOMService;
import com.primus.merchandise.product.service.ProductService;
import com.primus.util.ServiceLibrary;
import com.techtrade.rads.framework.utils.Utils;


public class ServiceFactory {


    public static FilterService getFilterService()
    {
        FilterService service = (FilterService) ServiceLibrary.services().getService("FilterService")  ;
        return service ;
    }

    public static LoginService getLoginService()
    {
        LoginService service = (LoginService)ServiceLibrary.services().getService("login")  ;
        return service ;
    }

    public static CompanyService getCompanyService()
    {
        CompanyService companyService = (CompanyService)ServiceLibrary.services().getService("CompanyService")  ;
        return companyService ;
    }

    public static DivisionService getDivisionService()
    {
        DivisionService service = (DivisionService)ServiceLibrary.services().getService("DivisionService")  ;
        return service ;
    }

    public static Division getDivision (Division division, ProductContext context) {
        DivisionService service = getDivisionService() ;
        if(division != null && division.getId() > 0 )  {
            return (Division) service.getById(division.getId()) ;
        }else if (division != null ) {
           return service.getByName(division.getName(),context);
        }
        return null;
    }


    public static RoleService getRoleService()
    {
        RoleService service = (RoleService)ServiceLibrary.services().getService("RoleService")  ;
        return service ;
    }

    public static RegionService getRegionService()
    {
        RegionService service = (RegionService)ServiceLibrary.services().getService("RegionService")  ;
        return service ;
    }

    public static ZoneService getZoneService()
    {
        ZoneService service = (ZoneService)ServiceLibrary.services().getService("ZoneService")  ;
        return service ;
    }

    public static UserService getUserService()
    {
        UserService userService = (UserService)ServiceLibrary.services().getService("UserService")  ;
        return userService ;
    }

    public static DepartmentService getDepartmentService()
    {
        DepartmentService service = (DepartmentService)ServiceLibrary.services().getService("DepartmentService")  ;
        return service ;
    }

    public static StockistService getStockistService()
    {
        StockistService service = (StockistService)ServiceLibrary.services().getService("StockistService")  ;
        return service ;
    }

    public static StoreService getStoreService()
    {
        StoreService service = (StoreService)ServiceLibrary.services().getService("StoreService")  ;
        return service ;
    }

    public static DoctorService getDoctorService()
    {
        DoctorService service = (DoctorService)ServiceLibrary.services().getService("DoctorService")  ;
        return service ;
    }

    public static CategoryService getCategoryService()
    {
        CategoryService service = (CategoryService)ServiceLibrary.services().getService("CategoryService")  ;
        return service ;
    }

    public static UOMService getUOMService()
    {
        UOMService service = (UOMService)ServiceLibrary.services().getService("UOMService")  ;
        return service ;
    }

    public static ProductService getProductService()
    {
        ProductService service = (ProductService)ServiceLibrary.services().getService("ProductService")  ;
        return service ;
    }

    public static ItemService getItemService()
    {
        ItemService service = (ItemService)ServiceLibrary.services().getService("ItemService")  ;
        return service ;
    }

    public static SkuService getSKUService()
    {
        SkuService service = (SkuService)ServiceLibrary.services().getService("SkuService")  ;
        return service ;
    }

    public static AppointmentTemplateService getAppointmentTemplateService()
    {
        AppointmentTemplateService service = (AppointmentTemplateService)ServiceLibrary.services().getService("AppointmentTemplateService")  ;
        return service ;
    }
    public static AppointmentService getAppointmentService()
    {
        AppointmentService service = (AppointmentService)ServiceLibrary.services().getService("AppointmentService")  ;
        return service ;
    }

    public static ReasonCodeService getReasonCodeService()
    {
        ReasonCodeService service  =(ReasonCodeService)ServiceLibrary.services().getService("ReasonCodeService")  ;
        return service ;
    }

    public static Location getLocation (Location location, ProductContext context) {
        RegionService service = getRegionService() ;
        if(location != null && location.getId() > 0 )  {
            return (Location) service.getLocationById(location.getId(),context) ;
        }else if (location != null && !Utils.isNull(location.getName()) ) {
            return (Location) service.getLocationByName(location.getName(),context);
        }
        return null;
    }

    public static Department getDepartment (Department department, ProductContext context) {
        DepartmentService service = getDepartmentService() ;
        if(department != null && department.getId() > 0 )  {
            return (Department) service.getById(department.getId()) ;
        }else if (department != null ) {
            return (Department) service.getByBusinessKey(department,context);
        }
        return null;
    }





}


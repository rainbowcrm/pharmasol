package com.primus.util;

import com.primus.ApplicationManager;
import com.primus.abstracts.AbstractValidator;
import com.primus.admin.reasoncode.service.ReasonCodeService;
import com.primus.admin.reasoncode.validator.ReasonCodeValidator;
import com.primus.admin.region.service.RegionService;
import com.primus.admin.region.validator.RegionValidator;
import com.primus.admin.zone.service.ZoneService;
import com.primus.admin.zone.validator.ZoneValidator;
import com.primus.common.GeneralSQL;
import com.primus.common.company.service.CompanyService;
import com.primus.common.company.validator.CompanyValidator;
import com.primus.common.filter.service.FilterService;
import com.primus.common.login.service.LoginService;
import com.primus.abstracts.AbstractService;
import com.primus.common.user.service.UserService;
import com.primus.common.user.validator.UserValidator;
import com.primus.crm.appointment.service.AppointmentService;
import com.primus.crm.appointment.service.AppointmentTemplateService;
import com.primus.crm.appointment.validator.AppointmentTemplateValidator;
import com.primus.crm.appointment.validator.AppointmentValidator;
import com.primus.externals.competitor.service.CompetitorService;
import com.primus.externals.competitor.validator.CompetitorValidator;
import com.primus.externals.doctor.service.DoctorService;
import com.primus.externals.doctor.validator.DoctorValidator;
import com.primus.externals.stockist.service.StockistService;
import com.primus.externals.stockist.validator.StockistValidator;
import com.primus.externals.store.service.StoreService;
import com.primus.externals.store.validator.StoreValidator;
import com.primus.framework.nextup.NextUPSQL;
import com.primus.admin.department.service.DepartmentService;
import com.primus.admin.department.validator.DepartmentValidator;
import com.primus.admin.role.service.RoleService;
import com.primus.admin.role.validator.RoleValidator;
import com.primus.admin.division.service.DivisionService;
import com.primus.admin.division.validator.DivisionValidator;
import com.primus.merchandise.category.service.CategoryService;
import com.primus.merchandise.category.validator.CategoryValidator;
import com.primus.merchandise.item.service.ItemService;
import com.primus.merchandise.item.service.SkuService;
import com.primus.merchandise.item.service.UOMService;
import com.primus.merchandise.item.validator.ItemValidator;
import com.primus.merchandise.product.service.ProductService;
import com.primus.merchandise.product.validator.ProductValidator;
import com.primus.operations.iborder.service.InboundOrderService;
import com.primus.operations.iborder.validator.InboundOrderValidator;
import com.primus.profiles.service.ProfileService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ServiceLibrary implements  IServiceLibrary,ApplicationContextAware {



    private static ApplicationContext applicationContext;

    @Autowired
    LoginService loginService ;

    @Autowired
    CompanyService companyService;

    @Autowired
    FilterService filterService;

    @Autowired
    UserService userService ;

    @Autowired
    GeneralSQL generalSQL;

    @Autowired
    NextUPSQL nextUPSQL ;

    @Autowired
    CompanyValidator companyValidator;

    @Autowired
    UserValidator userValidator;

    @Autowired
    ApplicationManager applicationManager;


    @Autowired
    DivisionService divisionService;

    @Autowired
    DivisionValidator divisionValidator;


    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentValidator departmentValidator;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleValidator roleValidator;

    @Autowired
    RegionService regionService;

    @Autowired
    RegionValidator regionValidator;

    @Autowired
    StockistService stockistService ;

    @Autowired
    StockistValidator stockistValidator;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreValidator storeValidator;

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorValidator doctorValidator;

    @Autowired
    ZoneService zoneService;

    @Autowired
    ZoneValidator zoneValidator;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryValidator categoryValidator;

    @Autowired
    ProductService productService;

    @Autowired
    ProductValidator productValidator;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemValidator itemValidator ;

    @Autowired
    SkuService skuService ;

    @Autowired
    UOMService uomService ;


    @Autowired
    AppointmentTemplateService appointmentTemplateService ;

    @Autowired
    AppointmentTemplateValidator appointmentTemplateValidator;

    @Autowired
    AppointmentService appointmentService ;

    @Autowired
    AppointmentValidator appointmentValidator;

    @Autowired
    ReasonCodeService reasonCodeService ;

    @Autowired
    ReasonCodeValidator reasonCodeValidator ;

    @Autowired
    CompetitorService competitorService ;

    @Autowired
    CompetitorValidator competitorValidator ;

    @Autowired
    ProfileService profileService;

    @Autowired
    InboundOrderService inboundOrderService;

    @Autowired
    InboundOrderValidator inboundOrderValidator ;


    public  ApplicationManager getApplicationManager()
   {
       return applicationManager ;
   }

    @Override
    public GeneralSQL getGeneralSQL() {
        return generalSQL;
    }

    @Override
    public NextUPSQL getNextUPSQL() {
        return nextUPSQL;
    }

    public ProfileService getProfileService() {
        return  profileService ;
    }

    @Override
    public AbstractService getService(String serviceName) {
        switch(serviceName) {
            case "login" :       return loginService;
            case "CompanyService" : return companyService ;
            case "UserService" : return userService ;
            case "FilterService" : return filterService ;
            case "DivisionService" : return divisionService ;
            case "DepartmentService" : return departmentService ;
            case "RoleService" : return roleService ;
            case "RegionService" : return regionService ;
            case "StockistService": return stockistService ;
            case "StoreService": return storeService ;
            case "DoctorService": return doctorService ;
            case "ZoneService": return zoneService ;
            case "CategoryService" : return categoryService;
            case "ProductService"  : return  productService ;
            case "ItemService" : return itemService ;
            case "SkuService"  : return skuService ;
            case "UOMService" : return uomService ;
            case "AppointmentTemplateService"  : return  appointmentTemplateService;
            case "AppointmentService"  : return  appointmentService;
            case "ReasonCodeService" : return reasonCodeService ;
            case "CompetitorService" : return competitorService ;
            case "InboundOrderService" : return  inboundOrderService;

        }
        return null;

    }

    @Override
    public AbstractValidator getValidator(String serviceName) {

       switch(serviceName) {
           case "CompanyValidator" : return companyValidator;
           case "UserValidator" : return userValidator;
           case "DivisionValidator" : return divisionValidator ;
           case "DepartmentValidator" : return departmentValidator;
           case "RoleValidator" : return roleValidator;
           case "RegionValidator" : return regionValidator;
           case "StockistValidator" : return stockistValidator;
           case "StoreValidator" : return storeValidator ;
           case "DoctorValidator" : return doctorValidator;
           case "ZoneValidator" : return zoneValidator;
           case "CategoryValidator" : return categoryValidator;
           case "ProductValidator" : return productValidator ;
           case "ItemValidator" : return itemValidator;
           case "AppointmentTemplateValidator" : return  appointmentTemplateValidator;
           case "AppointmentValidator" : return appointmentValidator ;
           case "ReasonCodeValidator" : return reasonCodeValidator ;
           case "CompetitorValidator" : return competitorValidator;
           case "InboundOrderValidator" : return inboundOrderValidator;

       }

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    public static ServiceLibrary services() {
        return applicationContext.getBean(ServiceLibrary.class);
    }

}

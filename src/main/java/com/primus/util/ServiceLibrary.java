package com.primus.util;

import com.primus.ApplicationManager;
import com.primus.abstracts.AbstractValidator;
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

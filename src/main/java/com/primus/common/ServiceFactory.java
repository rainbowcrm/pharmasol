package com.primus.common;

import com.primus.admin.department.model.Department;
import com.primus.admin.department.service.DepartmentService;
import com.primus.admin.division.model.Division;
import com.primus.admin.division.service.DivisionService;
import com.primus.admin.role.service.RoleService;
import com.primus.common.company.service.CompanyService;
import com.primus.common.filter.service.FilterService;
import com.primus.common.login.service.LoginService;
import com.primus.common.user.service.UserService;
import com.primus.util.ServiceLibrary;


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

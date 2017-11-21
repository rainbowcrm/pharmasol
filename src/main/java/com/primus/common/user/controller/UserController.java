package com.primus.common.user.controller;

import com.primus.abstracts.AbstractCRUDController;
import com.primus.common.CommonUtil;
import com.primus.common.ProductContext;
import com.primus.common.user.model.User;
import com.primus.admin.role.model.Role;
import com.primus.admin.role.service.RoleService;
import com.techtrade.rads.framework.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController extends AbstractCRUDController{
    @Override
    protected String getServiceName() {
        return "UserService";
    }

    @Override
    protected String getValidatorName() {
        return "UserValidator";
    }

    public String getSuffix() {
        System.out.println("Context  =" + getContext().getUser());
        if (object != null)  {
            User currUsr = (User)object;
            if (!Utils.isNull(currUsr.getSuffix()))
                return "@" + currUsr.getSuffix() ;
        }

        ProductContext ctx=(ProductContext) getContext();
        return "@" + ctx.getLoggedinCompanyCode();
    }

    public Map<String,String> getRoles()
    {
        Map<String,String> ans = new HashMap<String,String>() ;
        RoleService roleService = CommonUtil.getRoleService() ;
        List<Role> roles = (List<Role>)roleService.fetchAllActive("","",(ProductContext) getContext());
        roles.forEach( role ->  {

                ans.put(String.valueOf(role.getId()),role.getRole());
        });
        return ans;
    }
}

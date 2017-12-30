package com.primus.common.ajaxservices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.primus.common.CommonUtil;
import com.primus.common.FiniteValue;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxUpdateService;

public class AllFiniteValues implements IAjaxUpdateService {

    @Override
    public String update(String jsonString, IRadsContext ctx) {
        List<FiniteValue> values = CommonUtil.getAllFiniteValues();
        StringBuffer replies = new StringBuffer (  " {\n \"allFiniteValues\": [  ");
        for (int i = 0 ;   i < values.size() ; i ++ ) {
            String replyJSON = values.get(i).toJSON() ;
            replies.append( replyJSON  ) ;
            if (i < values.size() -1 )
                replies.append( ",");
            replies.append("\n");
        }
        replies.append("]\n");
        replies.append("}");
        return replies.toString();


    }

    @Override
    public IRadsContext generateContext(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return CommonUtil.generateContext(request,null);
    }



}
package com.primus.util;

import com.primus.abstracts.AbstractService;
import com.primus.abstracts.AbstractValidator;
import com.primus.common.GeneralSQL;
import com.primus.framework.nextup.NextUPSQL;

public interface IServiceLibrary {

    public AbstractService getService(String serviceName);

    public AbstractValidator getValidator(String validatorName);

    public GeneralSQL getGeneralSQL();

    public NextUPSQL getNextUPSQL();
}

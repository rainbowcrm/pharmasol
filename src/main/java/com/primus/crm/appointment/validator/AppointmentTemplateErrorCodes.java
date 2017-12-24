package com.primus.crm.appointment.validator;

import com.primus.abstracts.CommonErrorCodes;

public class AppointmentTemplateErrorCodes extends CommonErrorCodes {
    public static  final int PARTY_WRONGWITH_TYPE= 1000101;
    public static  final int END_BEFORE_START= 1000102;
    public static  final int NOT_LINKED_FOR_BUSINESS= 1000103;
    public static  final int NOT_LINKED_WITH_LOCATION= 1000104;

    public static  final int DAYS_CANNOT_SELECTEDFORMONTHAPPTS= 1000105;
    public static  final int ONE_DAYSELECFOR_WEEKBIWEEK= 1000106;
    public static  final int DAYS_SHOULDBESELECTED_DAILYAPPT= 1000107;
    public static  final int START_DATE_NOTFROMPAST= 1000108;
    public static  final int INSTANCEES_ALREADY_CREATED= 1000109;
    public static  final int APPOINTMENT_NOTINCANCELATONSTATUS= 1000110;
    public static  final int REASON_CODE_MANDATORYFORCANCEL= 1000111;
    public static  final int APPOINTMENT_NOTINCOMPLETESTATUS= 1000112;
    public static  final int PARTY_MANDATORY_TYPEIS= 1000113;
    public static  final int FEEDBACK_REQD_FORCOMPLETION= 1000114;
    public static  final int FEEDBACK_ONLY_FORCOMPLETION= 1000115;
    public static  final int ORDERLINES_ONLY_FORCOMPLETION= 1000116;
    public static  final int COMPSALES_ONLY_FORCOMPLETION= 1000117;
    public static  final int NEXT_VISIT_ONLYONCOMPLETION= 1000118;

}

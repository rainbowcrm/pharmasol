package com.primus.common;

public class FVConstants {

    public final static String  PGM_APPTTEMPLATE = "AppointmentTemplate";
    public final static String  PGM_APPT = "Appointment";
    public final static String  PGM_INBOUNDORDER= "InboundOrder";

    public final static String FV_INDUSTRY_TYPE = "INDUSTRY";
    public final static String FV_BUSINESS_TYPE = "BUSINESS";
    public final static String FV_DIVISION_TYPE = "DIVTYPE";
    public final static String FV_CALCULATION_OPERANDS = "CALCOPER";
    public final static String FV_EMP_STATUS = "EMPSTATUS";
    public final static String FV_DATEPATTERN = "DTPTTN";
    public final static String FV_APPOINTMENTSTATUS = "APPTSTATUS";
    public final static String FV_EXTERNALPARTYTPE = "EXPTYTYPE";
    public final static String FV_VISITTARGET =  "VSTTRGT";
    public final static String FV_DOCTORCLASS = "DOCCLASS";
    public final static String FV_USACCESS = "USACCESS";

    public final static class DATE_PATTERN {
        public final static String DAILY ="PTNDLY";
        public final static String WEEKLY ="PTNWKLY";
        public final static String BIWEEKLY ="PTNBIWKLY";
        public final static String MONTHLY ="PTNMNT";
    }

    public final static class EXTERNAL_PARTY {
        public final static String STOCKIST ="PTSTCK";
        public final static String STORE ="PTSTRE";
        public final static String DOCTOR ="PTDCT";
    }

    public final static class USER_ACCESS {
        public final static String REGION ="ACCREGN";
        public final static String COMPANY ="ACCCOMP";
        public final static String ZONE ="ACCZNE";
        public final static String LOCATION = "ACCLCN";
    }

    public final static class DOCTOR_CLASS {
        public final static String CLASSA ="DCCLSC";
        public final static String CLASSB ="DCCLSB";
        public final static String CLASSC ="DCCLSA";
    }

    public final static class VISIT_TO {

        public final static String ALL ="VSALL";
        public final static String DOCTOR_CLASS ="VSDCLASS";
        public final static String ALL_STOCKIST ="VSALLSTCKS";
        public final static String ALL_STORE ="VSALLSTRS";
        public final static String ALL_DOCTOR ="VSALLDOCS";
        public final static String IND_STOCKIST ="VSINDSTK";
        public final static String IND_STORE ="VSINDSTR";
        public final static String IND_DOCTOR ="VSINDDOC";

    }

    public final static class APPT_STATUS {
        public final static String PLANNED ="PLND";
        public final static String SCHEDULED ="SCHD";
        public final static String INPROG ="INPRG";
        public final static String CANCELLED ="CNCLD";
        public final static String TEMPLATECANCELLED ="TMPCNCLD";
        public final static String COMPLETED ="CMPLTD";
        public final static String PENDING ="PNDG";
        public final static String MISSED ="MSSD";
        public final static String CLOSED ="CLSD";
        public final static String CLOSEDCANCELED ="CLSCNCLD";
    }




}

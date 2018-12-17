package tms.util;

/**
 * Created by tang on 16/5/12.
 */
public class Constants {
    public final static String LOG_SEPARATOR = "#";

    public final static int MAX_PAGESIZE = 1000;//最大分页数

    public static final  int TOKEN_EXPE_HOURS = 6;//token过期月数

    public static final String OA_TOKEN = "token";

    public static final int EFFECTIVE = 1; //有效

    public static final int UN_EFFECTIVE = 2; //无效

    public static final int DINGDINGNOTE_ROLE=0;

    public static final int HANDLER_ROLE=1;

    public static final int REVIEWER_ROLE=2;

    public static final int FINANCE_ROLE=3;

    public static final int PERSONNEL_ROLE=4;

    public static final int APPROVAL_ROLE=5;

    public static final int WAIT_APPROVAL=-1;

    public static final int AGREE_APPROVAL=1;

    public static final int REFUSE_APPROVAL=2;



    public static enum FILTERBY_TYPE {
        GE("GE"), GT("GT"), EQ("EQ"), NE("NE"), LE("LE"), LT("LT"), LIKE("LIKE"), START("START"),IN("IN"), TOP("TOP"), NOTIN("NOTIN");

        private String code;

        private FILTERBY_TYPE(String code) {
            this.code = code;
        }

        public String toString() {
            return code;
        }
    }

    public static enum AREA_TYPE {
        PROVINCE(1),CITY(2);

        private int code;

        private AREA_TYPE(int code) {
            this.code = code;
        }

        public int toInt() {
            return code;
        }
    }


}

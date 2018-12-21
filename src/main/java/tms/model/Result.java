package tms.model;


import tms.component.exception.BusinessException;

public class Result {

    public static final String RETURN_LIST = "list";
    public static final String RETURN_PAGE = "page";
    public static final String RETURN_PAGE_COUNT = "pageCount";
    public static final String RETURN_TOTAL_SIZE = "totalSize";
    // 对应的编码与内容(通用的)
    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MSG = "操作成功！";

    private int code;
    private String msg;
    private Object content;

    public static final Result SUCCESS = new Result(SUCCESS_CODE, SUCCESS_MSG, null);

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public Result(Object content) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
        this.content = content;
    }

    // 默认成功的返回
    public static Result defaultSuccessResult() {
        return new Result(SUCCESS_CODE, SUCCESS_MSG);
    }

    // 成功的返回,data为返回的数据
    public static Result successResult(Object content) {
        return new Result(SUCCESS_CODE, SUCCESS_MSG, content);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        checkStaticResult();
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        checkStaticResult();
        this.msg = msg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        checkStaticResult();
        this.content = content;
    }

    private void checkStaticResult() {
        if(this==SUCCESS) {
            throw new BusinessException("静态Result不可以修改值");
        }
    }
}

package effect.effect.common.vo;

import effect.effect.common.constants.CommonConstants;

import java.io.Serializable;

/**
 * @outhor feilongchen
 * @create 2018-01-22 7:39 PM
 */
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code = CommonConstants.RESPONSE_CODE_SUCCESS;  //成功 为0   失败 为 1
    private String msg = "";
    private Object data;
    private String debugInfo;

    public ResponseVO() {

    }

    public boolean checkFailure() {
        boolean result = false;
        if (this.code == CommonConstants.RESPONSE_CODE_FAILURE) {
            result = true;
        }
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setResponseCodeFailure() {
        this.code = CommonConstants.RESPONSE_CODE_FAILURE;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

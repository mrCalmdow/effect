package effect.effect.common.util;

import effect.effect.common.constants.CommonConstants;
import effect.effect.common.vo.ResponseVO;

/**
 * @outhor feilongchen
 * @create 2018-01-22 7:47 PM
 */
public class ResponseUtil {
    public static ResponseVO response(int code, String msg, Object data, String debugInfo) {
        ResponseVO ResponseVO = new ResponseVO();
        ResponseVO.setCode(code);
        ResponseVO.setMsg(msg);
        ResponseVO.setData(data);
        ResponseVO.setDebugInfo(debugInfo);
        return ResponseVO;
    }

    public static ResponseVO response(int code, String msg, Object data) {
        return response(code, msg, data, null);
    }

    public static ResponseVO response(int code, String msg) {
        return response(code, msg, null, null);
    }

    public static ResponseVO success(String msg) {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, msg, null, null);
    }

    public static ResponseVO success() {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, null, null, null);
    }

    public static ResponseVO successWithData(String msg, Object data) {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, msg, data, null);
    }

    public static ResponseVO error(int code, String message) {
        return response(code, message);
    }

    public static ResponseVO error(String message, String debugInfo) {
        return response(CommonConstants.RESPONSE_CODE_FAILURE, message, null, debugInfo);
    }

    public static ResponseVO error(String message) {
        return error(CommonConstants.RESPONSE_CODE_FAILURE, message);
    }

}

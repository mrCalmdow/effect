package effect.effect.common;

import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.exception.CreateDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


/**
 * @author feilongchen
 * @create 2018-02-24 12:04 PM
 */
public abstract class BaseExceptionHandler {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    //use to recognize environment
    @Value("${evn.isDev:false}")
    protected boolean isDev;
    protected String debugInfo = null;

    /**
     * handler default exceptions
     * @param e
     * @return responseVO with exception messages
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseVO defaultErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        debugInfo = null;
        if(isDev) {
            debugInfo = e.toString() + e.getMessage() + "--->" + getMessage(e.getStackTrace());
            //wrap exception to responseVO
        }
        return ResponseUtil.error("Internal error occurred, please contact administrator.", debugInfo);
    }

    /**
     * handler http request method not support exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseVO requestMethodNotSupport(Exception e) {
        if(log.isDebugEnabled()) {
            log.debug(e.getMessage(), e);
        }
        String message = "Not support " + e.getMessage() + "request.";

        debugInfo = null;
        if(isDev) {
            //debugInfo = e.toString();
            debugInfo = e.toString() + e.getMessage() + "--->" + getMessage(e.getStackTrace());
        }
        return ResponseUtil.error(message, debugInfo);
    }


    /**
     * handler http request media type not support exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseVO requestMediaTypeNotSupport(Exception e) {
        if(log.isDebugEnabled()) {
            log.debug(e.getMessage(), e);
        }

        String message = "Not support" + e.getMessage() + "request media type";
        debugInfo = null;
        if(isDev) {
            //debugInfo = e.toString();
            debugInfo = e.toString() + e.getMessage() + "--->" + getMessage(e.getStackTrace());
        }
        return ResponseUtil.error(message, debugInfo);
    }

    /**
     * handler custom exceptions
     * @param e
     * @return
     */
    @ExceptionHandler(value = {CreateDomainException.class})
    public ResponseVO customExceptions(Exception e) {
        if(log.isDebugEnabled()) {
            log.debug(e.getMessage(), e);
        }
        String message = "" + e.getMessage() + "";
        debugInfo = null;
        if(isDev) {
            //debugInfo = e.toString();
            debugInfo = e.toString() + e.getMessage() + "--->" + getMessage(e.getStackTrace());
        }

        return ResponseUtil.error(message, debugInfo);
    }

    /**
     * to concatenate all message in a string
     * @param stackTraceElements
     * @return stackTraceElements messages
     */
    private static String getMessage(StackTraceElement[] stackTraceElements) {
        String messages = null;
        for (StackTraceElement e: stackTraceElements) {
            messages += e.toString();
        }
        return messages;
    }
}

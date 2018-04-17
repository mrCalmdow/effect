package effect.effect.common.filter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import effect.effect.common.constants.CommonConstants;
import effect.effect.common.constants.JwtConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.security.JwtTokenService;
import effect.effect.common.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author feilongchen
 * @create 2018-01-28 10:24 PM
 */
public class TokenFilter extends OncePerRequestFilter {

    private static Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Value("${jwt.header:x-user-token}")
    private String authenticHeader;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("---------------Entry token filter-----------");

        ResponseVO responseVO = new ResponseVO();
        String token = httpServletRequest.getHeader(authenticHeader);

        boolean error = false;
        if(StringUtils.isEmpty(token)) {
            error = true;
        } else {
            try {
                JwtTokenService.Payload payload = jwtTokenService.loadPayload(token);
                httpServletRequest.setAttribute(JwtConstants.USER_ID, payload.getUserId());
                httpServletRequest.setAttribute(JwtConstants.ACCOUNT, payload.getAccount());
            } catch (Exception e) {
                log.warn("token invalid");
                error = true;
            }
        }
        if (error) {
            responseVO.setCode(CommonConstants.RESPONSE_CODE_ANONYMOUS);
            responseVO.setMsg(MessageConstants.TOKEN_INVALID);
            //httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.append(JSON.toJSONString(responseVO));
            writer.close();
        } else {
            //httpServletResponse.setContentType("application/json; charset=utf-8");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

package effect.effect.web.controller.pub;

import com.alibaba.fastjson.JSON;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.security.JwtTokenService;
import effect.effect.common.util.MD5Util;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.domain.DomainFactory;
import effect.effect.domain.UserDomain;
import effect.effect.po.User;
import effect.effect.repo.UserAutoRepo;
import effect.effect.web.URLs;
import effect.effect.web.vo.LoginVO;
import effect.effect.web.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @outhor feilongchen
 * @create 2018-01-22 7:31 PM
 */
@Api(tags = "user sign up and sign in")
@RestController
@RequestMapping(URLs.User.PUB_USER)
public class UserPubController extends BaseController {

    @Autowired
    private UserAutoRepo userAutoRepo;
    @Autowired
    private JwtTokenService jwtTokenService;

    @ApiOperation(value = "user sign in")
    @PostMapping("/register")
    public ResponseVO register(@RequestBody @Valid RegisterVO registerVO) {
        String account = registerVO.getAccount();
        UserDomain domain = DomainFactory.createDomain(UserDomain.class);
        if(domain.checkUserExists(account)) {
            return ResponseUtil.error(MessageConstants.ERROR_USER_EXISTS);
        }
        registerVO.setPassword(MD5Util.MD5Encrypt(registerVO.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(registerVO, user);
        user.setRegTime(new Date());
        user.setGender(0);
        userAutoRepo.save(user);
        log.info("register success account: " + user.getAccount());
        return ResponseUtil.success();
    }

    @ApiOperation(value = "user sign up")
    @PostMapping("/login")
    public ResponseVO signup(@RequestBody @Valid LoginVO loginVO) {
        ResponseVO responseVO = new ResponseVO();
        User user = userAutoRepo.findByAccount(loginVO.getAccount());
        UserDomain domain = DomainFactory.createDomain(UserDomain.class);
        domain.setEntity(user);
        if(!domain.checkValid()) {
            return ResponseUtil.error(MessageConstants.ERROR_USER_NOT_EXISTS);
        }
        if(!domain.checkPassword(loginVO.getPassword())) {
            return ResponseUtil.error(MessageConstants.ERROR_INVALID_PASSWORD);
        }
        log.info("login success account: " + loginVO.getAccount());
        //TODO return token
        String token = jwtTokenService.generateToken(loginVO.getAccount(), "web");
        responseVO.setData(token);
        return responseVO;
    }
}

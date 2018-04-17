package effect.effect.web.controller.member;

import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.MD5Util;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.User;
import effect.effect.repo.UserAutoRepo;
import effect.effect.web.URLs;
import effect.effect.web.vo.ResetPasswordVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author feilongchen
 * @create 2018-01-29 10:00 AM
 */
@Api(tags = "user reset password")
@RestController
@RequestMapping(URLs.User.MEMBER_USER)
public class UserMemberController extends BaseController {

    @Autowired
    private UserAutoRepo userAutoRepo;

    @PostMapping("/resetPwd")
    public ResponseVO resetPassword(@RequestBody @Valid ResetPasswordVO resetPasswordVO) {
        User user = userAutoRepo.findByAccount(resetPasswordVO.getAccount());
        if(null != user) {
            if(user.getPassword().equals(MD5Util.MD5Encrypt(resetPasswordVO.getOldPassword()))) {
                user.setPassword(MD5Util.MD5Encrypt(resetPasswordVO.getNewPassword()));
                userAutoRepo.save(user);
                log.info("reset password success");
                return ResponseUtil.success(MessageConstants.RESET_PASSWORD_SUCCESS);
            } else {
                log.info("old password incorrect");
                return ResponseUtil.error(MessageConstants.ERROR_OLD_PASSWORD_INCORRECT);
            }
        }
        log.info("user not exists");
        return ResponseUtil.error(MessageConstants.ERROR_USER_NOT_EXISTS);
    }

}

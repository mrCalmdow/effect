package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.security.JwtTokenService;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.UserService;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.LoginVO;
import effect.effect.web.vo.RegisterVO;
import effect.effect.web.vo.ResetPasswordVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-11 10:00 AM
 */
public class UserMemberControllerTest extends BaseCommonControllerTest {
    @Autowired
    private UserAutoRepo userAutoRepo;

    private String urlTemplate = URLs.User.PUB_USER;
    private String urlTemplateMember = URLs.User.MEMBER_USER;

    @Before
    public void setup() {
        userAutoRepo.deleteAll();
    }
    @After
    public void tearDown() {
        userAutoRepo.deleteAll();
    }

    @Test
    public void testResetPWD() throws Exception {
        RegisterVO registerVO = generateRegisterVO();
        LoginVO loginVO = generateLoginVO();
        String newPwd = "aaaaaaa";
        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/register", registerVO))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/login", loginVO))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").isNotEmpty());

        ResetPasswordVO resetPasswordVO = new ResetPasswordVO();
        resetPasswordVO.setAccount(registerVO.getAccount());
        resetPasswordVO.setOldPassword(registerVO.getPassword());
        resetPasswordVO.setNewPassword(newPwd);

        String token = jwtTokenService.generateToken(resetPasswordVO.getAccount(), JwtConstants.WEB);
        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplateMember + "/resetPwd", resetPasswordVO)
                .header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

        loginVO.setPassword(resetPasswordVO.getNewPassword());

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/login", loginVO))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
    }
}

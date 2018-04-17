package effect.effect.web.controller.pub;

import effect.effect.common.security.JwtTokenService;
import effect.effect.repo.UserAutoRepo;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.LoginVO;
import effect.effect.web.vo.RegisterVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @outhor feilongchen
 * @create 2018-01-22 10:53 PM
 */
public class UserPubControllerTest extends BaseCommonControllerTest {

    @Autowired
    private UserAutoRepo userAutoRepo;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private MockMvc mockMvc;

    private String urlTemplate = URLs.User.PUB_USER;


    @Before
    public void setup() {
        System.out.println("------before------");
        userAutoRepo.deleteAll();
    }

    @After
    public void tearDown() {
        System.out.println("------after------");
        userAutoRepo.deleteAll();
    }

    @Test
    public void testRegister() throws Exception {
        RegisterVO registerVO = generateRegisterVO();

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/register", registerVO)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void testLogin() throws Exception {
        LoginVO loginVO = generateLoginVO();

        RegisterVO registerVO = generateRegisterVO();

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/register", registerVO)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));

        String xUserToken = jwtTokenService.generateToken(account,"web");
        System.out.println("----------" + xUserToken + "----------");
        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/login", loginVO)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    public void testLoginFailure() throws Exception {
        RegisterVO registerVO = generateRegisterVO();
        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/register", registerVO)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));
        LoginVO loginVO = generateLoginVO(account, "aaaa");
        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate + "/login", loginVO)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1"));
    }
}

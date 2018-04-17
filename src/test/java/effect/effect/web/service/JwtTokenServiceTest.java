package effect.effect.web.service;

import com.alibaba.fastjson.JSON;
import effect.effect.common.security.JwtTokenService;
import effect.effect.po.User;
import effect.effect.repo.UserAutoRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author feilongchen
 * @create 2018-01-29 12:48 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenServiceTest {

    @Autowired
    private UserAutoRepo userAutoRepo;
    @Autowired
    private JwtTokenService jwtTokenService;

    private User user;

    @Before
    public void setup() {
        String account = "18688888888";
        User tmp = new User();
        tmp.setAccount(account);
        tmp.setPassword("111111");
        tmp.setNickname("tester");
        user = userAutoRepo.save(tmp);
        System.out.println(user.toString());
    }

    @After
    public void tearDown() {
        userAutoRepo.delete(user);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtTokenService.generateToken(user.getAccount(), "web");
        System.out.println("-------token = " + token);
        JwtTokenService.Payload payload = jwtTokenService.loadPayload(token);
        System.out.println("-------payload = " + payload.getAccount() + payload.getUserId() + payload.getAppName());
        Assert.assertTrue(jwtTokenService.validateToken(token));
    }
}

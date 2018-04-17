package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.po.ArticleCategory;
import effect.effect.service.ArticleCategoryService;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleCategoryAddVO;
import effect.effect.web.vo.ArticleCategoryVO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-11 11:13 AM
 */
public class ArticleCategoryMemberControllerTest extends BaseCommonControllerTest {

    @Before
    public void setup() {
        userAutoRepo.deleteAll();;
        articleCategoryAutoRepo.deleteAll();
    }

    @After
    public void tearDown() {
        userAutoRepo.deleteAll();;
        articleCategoryAutoRepo.deleteAll();
    }

    @Test
    public void testAddArticleCategory() throws Exception {
        String account = "18688888888";
        String urlTemplate = createUserAndUrlTemplate(URLs.Member.CATEGORY, account);

        ArticleCategoryAddVO articleCategoryAddVO = new ArticleCategoryAddVO();
        articleCategoryAddVO.setName("sayHello");
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);
        System.out.println("--------" + urlTemplate + "--------");

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate, articleCategoryAddVO).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void testUpdate() throws Exception {
        testAddArticleCategory();  //add a category
        String account = "18688888888";
        String newName = "sayHi";
        ArticleCategory articleCategory = articleCategoryAutoRepo.findAll().get(0);
        ArticleCategoryVO articleCategoryVO = new ArticleCategoryVO();
        BeanUtils.copyProperties(articleCategory, articleCategoryVO);
        articleCategoryVO.setName(newName);

        String urlTemplate = createUrlTemplate(URLs.Member.CATEGORY, account);
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);
        mockMvc.perform(MockUtility.populatePutBuilder(urlTemplate, articleCategoryVO).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

        String newUrlTemplate = createUrlTemplate(URLs.Pub.CATEGORY, account);
        mockMvc.perform(MockUtility.populateGetBuilder(newUrlTemplate)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data[0].name").value(newName));
    }

    @Test
    public void testDelete() throws Exception {
        String account = "18688888888";
        testAddArticleCategory();  //add a category
        ArticleCategory articleCategory = articleCategoryAutoRepo.findAll().get(0);
        String cId = articleCategory.getId();
        String urlTemplate = createUrlTemplate(URLs.Member.CATEGORY, account) + "/" + cId;
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);

        mockMvc.perform(MockUtility.populateDeleteBuilder(urlTemplate).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
        Assert.assertTrue(null == articleCategoryAutoRepo.findById(cId));
    }

    /**
     * create user object and generate a urlTemplate
     * @param account
     * @return
     */
    private String createUserAndUrlTemplate(String url, String account) {
        if(StringUtils.isEmpty(account)) {
            account = "18688888888";
        }
        createUser(account);
        return createUrlTemplate(url, account);
    }

}

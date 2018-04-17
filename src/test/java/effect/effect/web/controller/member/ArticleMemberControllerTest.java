package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.security.JwtTokenService;
import effect.effect.po.Article;
import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.UserAutoRepo;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleAddVO;
import effect.effect.web.vo.ArticleCategoryAddVO;
import effect.effect.web.vo.ArticleUpdateVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-05 8:02 PM
 */
public class ArticleMemberControllerTest extends BaseCommonControllerTest {

    private final String account = "18688888801";

    @Before
    public void setup() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
        articleAutoRepo.deleteAll();
    }
    @After
    public void tearDown() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
        articleAutoRepo.deleteAll();
    }

    @Test
    public void testissue() throws Exception {
        String cId = ready2AddArticle(account, "test");
        ArticleAddVO articleAddVO = new ArticleAddVO();
        articleAddVO.setCatId(cId);
        articleAddVO.setTitle("test title");
        articleAddVO.setContent("test Contents");
        String urlTemplate = createUrlTemplate(URLs.Member.ARTICLE, account);
        String token = CreateToken(account);

        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate, articleAddVO).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void testUpdateArticle() throws Exception {
        testissue();
        Article article = articleAutoRepo.findAll().get(0);
        ArticleUpdateVO articleUpdateVO = new ArticleUpdateVO();
        BeanUtils.copyProperties(article, articleUpdateVO);
        articleUpdateVO.setTitle("new title");
        articleUpdateVO.setContent("new content");
        String urlTemplate = createUrlTemplate(URLs.Member.ARTICLE, account);
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);

        mockMvc.perform(MockUtility.populatePutBuilder(urlTemplate, articleUpdateVO).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
    }

    @Test
    public void testDeleteArticle() throws Exception {
        testissue();
        Article article = articleAutoRepo.findAll().get(0);
        String urlTemplate = createUrlTemplate(URLs.Member.ARTICLE, account) + "/" + article.getId();
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);

        mockMvc.perform(MockUtility.populateDeleteBuilder(urlTemplate).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));
    }
}

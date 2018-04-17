package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.po.Article;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.CommentAddVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-14 11:59 AM
 */
public class CommentMemberControllerTest extends BaseCommonControllerTest {

    @Before
    public void setup() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
        articleAutoRepo.deleteAll();
        commentAutoRepo.deleteAll();
    }

    @After
    public void tearDown() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
        articleAutoRepo.deleteAll();
        commentAutoRepo.deleteAll();
    }

    @Test
    public void testCommentArticle() throws Exception {
        String account = "18900000002";
        String title = "myArticle";
        String comment = "it's comment context";
        Article article = ready2Comment(account, title);
        CommentAddVO commentAddVO = new CommentAddVO();
        commentAddVO.setArticleId(article.getId());
        commentAddVO.setCommitIP("localhost");
        commentAddVO.setContent(comment);
        String urlTemplate = createUrlTemplate(URLs.Member.COMMENT, account);
        String token = jwtTokenService.generateToken(account, JwtConstants.WEB);


        mockMvc.perform(MockUtility.populatePostBuilder(urlTemplate, commentAddVO).header(authenticHeader, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.content").value(comment));
    }
}

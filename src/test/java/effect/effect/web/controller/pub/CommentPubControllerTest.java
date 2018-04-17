package effect.effect.web.controller.pub;

import effect.effect.po.Article;
import effect.effect.po.Comment;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import effect.effect.web.vo.CommentAddVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-14 12:20 PM
 */
public class CommentPubControllerTest extends BaseCommonControllerTest {

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
    public void testDefaultShowComments() throws Exception {
        String account = "18811111111";
        String title = "title";
        String content = "hello";
        int count = 26;
        int pageNumber = 0;
        int pageSize = 10;
        int offset = pageNumber * pageSize;
        Article article = ready2Comment(account, title);
        generateComments(article.getId(), userAutoRepo.findByAccount(account).getUid(), content, count);
        String urlTemplate = createUrlTemplate(URLs.Pub.COMMENT, account) + "/" + article.getId();

        ResultActions resultActions = mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

        int max = getCurrentPageSize(count, pageSize, offset);
        for(int i = 0; i < max; i ++) {
            resultActions.andExpect(jsonPath("$.data.contents[" + i + "].content").value(content + (offset + i)));
        }
    }

    @Test
    public void testPageable() throws Exception {
        String account = "18811111111";
        String title = "title";
        String content = "hello";
        int count = 36;
        int pageNumber = 1;
        int pageSize = 10;
        int offset = pageNumber * pageSize;

        Article article = ready2Comment(account, title);
        //generate comments
        generateComments(article.getId(), userAutoRepo.findByAccount(account).getUid(), content, count);
        String urlTemplate = createUrlTemplate(URLs.Pub.COMMENT, account) + "/" + article.getId() + "?offset=" + offset
                + "&limit=" + pageSize;

        ResultActions resultActions = mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

        int max = getCurrentPageSize(count, pageSize, offset);
        for(int i = 0; i < max; i ++) {
            resultActions.andExpect(jsonPath("$.data.contents[" + i + "].content").value(content + (offset + i)));
        }

        //next page
        while(count >= pageNumber * pageSize) {
            offset = offset + pageSize;
            urlTemplate = createUrlTemplate(URLs.Pub.COMMENT, account) + "/" + article.getId() + "?offset=" + offset
                    + "&limit=" + pageSize;

            resultActions = mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"));

            max = getCurrentPageSize(count, pageSize, offset);
            for (int i = 0; i < max; i++) {
                resultActions.andExpect(jsonPath("$.data.contents[" + i + "].content").value(content + (offset + i)));
            }
            pageNumber++;
        }

    }



    /**
     * generate times comments fro point article and user
     * @param articleId
     * @param commitUserId
     * @param prefixOfContent
     * @param times
     */
    private void generateComments(String articleId, String commitUserId, String prefixOfContent, int times) {
        if(null != articleAutoRepo.findOne(articleId) && null != userAutoRepo.findOne(commitUserId)) {
            for(int i = 0; i < times; i++) {
                Comment comment = new Comment();
                CommentAddVO commentAddVO = new CommentAddVO();
                commentAddVO.setCommitIP("localhost");
                if(StringUtils.isEmpty(prefixOfContent)) {
                    prefixOfContent = "default contents" + i;
                }
                commentAddVO.setContent(prefixOfContent + i);
                commentAddVO.setArticleId(articleId);
                BeanUtils.copyProperties(commentAddVO, comment);
                comment.setUid(articleAutoRepo.findOne(articleId).getUid());
                comment.setCommitUserId(commitUserId);
                commentAutoRepo.save(comment);
            }
        }
    }
}

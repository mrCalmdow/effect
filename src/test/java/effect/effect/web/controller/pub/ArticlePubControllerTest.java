package effect.effect.web.controller.pub;

import effect.effect.common.constants.CommonConstants;
import effect.effect.po.Article;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-05 8:03 PM
 */
public class ArticlePubControllerTest extends BaseCommonControllerTest {

    private final String account = "18688888883";

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
    public void testDetail() throws Exception {
        String title = "myArticle";
        Article article = ready2Comment(account, title);
        String urlTemplate = createUrlTemplate(URLs.Pub.ARTICLE, account) + "/" + article.getId();

        mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"))
                .andExpect(jsonPath("$.data.title").value(title))
                .andExpect(jsonPath("$.data.content").value(title + account));
    }

    @Test
    public void testDefaultListAllArticles() throws Exception {
        String account  = "18900000001";
        String prefixOfTitle = "title";
        int num = 16;
        String urlTemplate = createUrlTemplate(URLs.Pub.ARTICLE, account);
        generateAticles(account, "title", num);

        ResultActions resultActions =  mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));

        int max = num < CommonConstants.DEFAULT_PAGE_SIZE ? num : CommonConstants.DEFAULT_PAGE_SIZE;
        for(int i = 0; i < max; i ++) {
            resultActions.andExpect(jsonPath("$.data.contents[" + i +"].title").value(prefixOfTitle + String.valueOf(i)));
        }
    }

    @Test
    public void testArticlesPageable() throws Exception {
        String account  = "18900000001";
        String prefixOfTitle = "title";
        int num = 50;
        int pageNumber = 1;
        int pageSize = 10;
        int offset = pageNumber * pageSize;
        String urlTemplate = createUrlTemplate(URLs.Pub.ARTICLE, account) + "?" + "offset=" + offset + "&limit=" + pageSize;
        generateAticles(account, "title", num);

        ResultActions resultActions =  mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));

        int max = getCurrentPageSize(num, pageSize, offset);
        //System.out.println("max = " + max);
        for(int i = 0; i < max; i ++) {
            //order by time desc
            int index = offset + i;
            resultActions.andExpect(jsonPath("$.data.contents[" + i +"].title").value(prefixOfTitle + String.valueOf(index)));
        }
}

    /**
     * generate articles list
     * @param account
     * @param prefixOfTitle
     * @param num
     * @return articles list
     */
    private List<Article> generateAticles(String account, String prefixOfTitle, int num) {
        List<Article> articles = new ArrayList<>();
        if(0 >= num) {
            return articles;
        }
        for(int i = 0; i < num; i ++) {
            Article article = ready2Comment(account, prefixOfTitle + String.valueOf(i));
            articles.add(article);
        }
        return articles;
    }

}

package effect.effect.web.controller.pub;

import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.web.BaseCommonControllerTest;
import effect.effect.web.MockUtility;
import effect.effect.web.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author feilongchen
 * @create 2018-02-11 2:40 PM
 */
public class ArticleCategoryPubControllerTest extends BaseCommonControllerTest {

    @Before
    public void setup() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
    }

    @After
    public void tearDown() {
        userAutoRepo.deleteAll();
        articleCategoryAutoRepo.deleteAll();
    }

    @Test
    public void listArticleCategories() throws Exception {
        String account = "18688888888";
        User user = createUser(account);
        for(int i = 0; i < 10; i++) {
            createArticleCategory("category" + i, user.getUid());
        }
        String urlTemplate = createUrlTemplate(URLs.Pub.CATEGORY, account);
        ResultActions resultActions = mockMvc.perform(MockUtility.populateGetBuilder(urlTemplate)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("0"));

        for(int i = 0; i < 10; i++) {
            resultActions.andExpect(jsonPath("$.data[" + i +"].name").value("category" + i));
        }
    }

    private void createArticleCategory(String name, String uid) {
        ArticleCategory category = new ArticleCategory();
        category.setName(name);
        category.setBelongTo(uid);
        category.setSort(0);
        articleCategoryAutoRepo.save(category);
    }
}

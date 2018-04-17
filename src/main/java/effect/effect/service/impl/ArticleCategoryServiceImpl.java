package effect.effect.service.impl;

import effect.effect.po.ArticleCategory;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-08 11:30 PM
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryAutoRepo articleCategoryAutoRepo;

    /**
     * wrapped up all article categories in a map
     * @return
     */
    public HashMap<String, String> getArticleCategoriesMap() {
        List<ArticleCategory> articleCategories = articleCategoryAutoRepo.findAll();
        HashMap<String, String> articleCategoriesMap = new HashMap<>();
        for(ArticleCategory articleCategory : articleCategories) {
            articleCategoriesMap.put(articleCategory.getId(), articleCategory.getName());
        }
        return articleCategoriesMap;
    }
}

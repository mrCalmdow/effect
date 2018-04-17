package effect.effect.service.impl;

import effect.effect.po.Article;
import effect.effect.service.ArticleCategoryService;
import effect.effect.service.ArticleService;
import effect.effect.web.vo.ArticleSearchResultVO;
import effect.effect.web.vo.bean.ArticleSearchResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-08 11:18 PM
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * transform category id to category name in Article
     * @param sources
     * @return
     */
    public ArticleSearchResultVO wrapArticlesBean(List<Article> sources) {
        ArticleSearchResultVO target = new ArticleSearchResultVO();
        if(CollectionUtils.isEmpty(sources)) {
            return target;
        }
        List<ArticleSearchResultBean> temporary = transformArticleCategoryName(sources);
        target.setContents(temporary);
        return target;
    }

    /**
     * cast Article to ArticleSearchResultBean
     * @param sources
     * @return cast result is list of ArticleSearchResultBean
     */
    public List<ArticleSearchResultBean> transformArticleCategoryName(List<Article> sources) {
        HashMap<String, String> map = articleCategoryService.getArticleCategoriesMap();
        List<ArticleSearchResultBean> targets = new ArrayList<>();
        for(Article source : sources) {
            ArticleSearchResultBean subTarget = new ArticleSearchResultBean();
            BeanUtils.copyProperties(source, subTarget);
            subTarget.setCategoryName(map.get(source.getCatId()));
            targets.add(subTarget);
        }
        return targets;
    }
}

package effect.effect.service;

import effect.effect.po.Article;
import effect.effect.web.vo.ArticleSearchResultVO;
import effect.effect.web.vo.bean.ArticleSearchResultBean;

import java.util.List;
import java.util.Map;

/**
 * @author feilongchen
 * @create 2018-02-08 11:20 PM
 */
public interface ArticleService {

    List<ArticleSearchResultBean> transformArticleCategoryName(List<Article> sources);
    ArticleSearchResultVO wrapArticlesBean(List<Article> sources);
}

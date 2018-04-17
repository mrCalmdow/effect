package effect.effect.repo;

import effect.effect.po.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:34
 */
public interface ArticleCategoryAutoRepo extends JpaRepository<ArticleCategory, String> {
    List<ArticleCategory> findByBelongTo(String belongTo);
    ArticleCategory findById(String id);
    ArticleCategory findByName(String name);
    ArticleCategory findByNameLike(String name);
}

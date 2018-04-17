package effect.effect.repo;

import effect.effect.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:33
 */
public interface ArticleAutoRepo extends JpaRepository<Article,String> {

    Article findByTitle(String title);
    Page<Article> findByUidOrderByUpdateTimeDesc(String uid, Pageable pageable);
    Page<Article> findByCatIdLikeOrderByUpdateTimeDesc(String catId, Pageable pageable);
    Page<Article> findByContentLikeOrderByUpdateTimeDesc(String Content, Pageable pageable);
    Page<Article> findByTitleLikeOrderByUpdateTimeDesc(String title, Pageable pageable);

    Page<Article> findByUidAndTitleContainingOrContentContainingOrderByUpdateTimeDesc(String uid, String title, String content, Pageable pageable);
    Page<Article> findByTitleContainingOrContentContainingOrderByUpdateTimeDesc(String title, String content, Pageable pageable);
}

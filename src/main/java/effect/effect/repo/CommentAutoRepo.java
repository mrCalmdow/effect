package effect.effect.repo;

import effect.effect.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author feilongchen
 * @create 2018-02-10 11:40 AM
 */
public interface CommentAutoRepo extends JpaRepository<Comment,String> {

    Page<Comment> findByArticleId(String id, Pageable pageable);
}

package effect.effect.repo;

import effect.effect.po.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:34
 */
public interface ReplyAutoRepo extends JpaRepository<Reply, String> {

    List<Reply> findByCommentIdOrderByCreTime(String commentId);
}

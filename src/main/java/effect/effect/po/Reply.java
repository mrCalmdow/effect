package effect.effect.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 回复表，晚点可以考虑盖楼
 *
 * @author Beldon
 * @create 2018-01-22 下午3:16
 */
@Entity
@Table(name = "t_reply")
@Data
public class Reply {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 用户id
     */
    @Column(length = 32)
    private String uid;

    /**
     * 被评论的文章id
     */
    @Column(length = 32,name = "article_id")
    private String articleId;

    /**
     * 评论ID
     */
    @Column(length = 32,name = "comment_id")
    private String commentId;

    /**
     * 内容
     */
    @Column(length = 256)
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "cre_time")
    private Date creTime;

}

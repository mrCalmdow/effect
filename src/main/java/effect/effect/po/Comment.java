package effect.effect.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author feilongchen
 * @create 2018-02-10 10:34 AM
 */
@Entity
@Table(name = "t_comment")
@Data
public class Comment {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 收到评论的用户id
     */
    @Column(length = 32)
    private String uid;

    @Column(length = 32, name = "commit_user_id")
    private String commitUserId;

    /**
     * 被评论的文章id
     */
    @Column(length = 32,name = "article_id")
    private String articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @Column(name = "commit_time")
    private Date commitTime;

    @Column(length = 15, name = "commit_ip")
    private String commitIP;

}

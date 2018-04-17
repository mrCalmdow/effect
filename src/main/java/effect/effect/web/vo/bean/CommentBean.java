package effect.effect.web.vo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author feilongchen
 * @create 2018-02-10 9:50 PM
 */
@Getter
@Setter
public class CommentBean {

    /**
     * 收到评论的用户id
     */
    private String uid;

    /**
     * 评论人ID
     */
    private String commitUserId;

    /**
     * 被评论的文章id
     */
    private String articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人IP
     */
    private String commitIP;
}

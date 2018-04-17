package effect.effect.web.vo.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author feilongchen
 * @create 2018-02-18 11:29 AM
 */
@Getter
@Setter
public class ReplyBean {

    /**
     * replyId
     */
    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 被评论的文章id
     */
    private String articleId;

    /**
     * 评论ID
     */
    private String commentId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date creTime;

}

package effect.effect.web.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-02-17 12:38 PM
 */
@Getter
@Setter
public class ReplyAddVO {
    /**
     * 被评论的文章id
     */
    @NotBlank
    private String articleId;

    /**
     * 评论ID
     */
    @NotBlank
    private String commentId;

    /**
     * 内容
     */
    @NotBlank
    @Length(max = 256)
    private String content;
}

package effect.effect.web.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author feilongchen
 * @create 2018-02-10 11:58 AM
 */
@Getter
@Setter
public class CommentAddVO {

    /**
     * 被评论的文章id
     */
    @NotBlank
    private String articleId;

    /**
     * 评论内容
     */
    @NotBlank
    //可考虑用自定义注解来做过滤XSS攻击字符
    private String content;

    /**
     * 评论人IP
     */
    private String commitIP;
}

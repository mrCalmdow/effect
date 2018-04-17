package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-01-31 10:41 PM
 */
@Getter
@Setter
public class ArticleAddVO extends BaseVO {
    @ApiModelProperty("article category id")
    @NotBlank
    private String catId;

    @ApiModelProperty("article title")
    @NotBlank
    @Length(min = 1, max = 128, message = "article title must between 1 and 128 characters")
    private String title;

    @ApiModelProperty("article content")
    @NotBlank
    private String content;
}

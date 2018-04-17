package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-01-29 6:56 PM
 */
@Getter
@Setter
public class ArticleCategoryAddVO extends BaseVO {

    @ApiModelProperty("article category name")
    @NotBlank
    private String name;
}

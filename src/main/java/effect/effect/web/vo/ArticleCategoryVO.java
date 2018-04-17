package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author feilongchen
 * @create 2018-01-29 6:19 PM
 */
@Getter
@Setter
public class ArticleCategoryVO extends BaseVO {
    private String id;

    private String name;

    private Integer sort;
}

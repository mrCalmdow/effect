package effect.effect.web.vo;

import effect.effect.common.entity.BaseVO;
import effect.effect.common.vo.PageableVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author feilongchen
 * @create 2018-02-07 12:53 PM
 */
@Getter
@Setter
public class ArticleSearchVO {

    private String categoryName;

    private String title;

    private String content;
}

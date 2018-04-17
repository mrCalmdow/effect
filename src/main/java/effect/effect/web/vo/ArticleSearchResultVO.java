package effect.effect.web.vo;

import effect.effect.web.vo.bean.ArticleSearchResultBean;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-07 2:22 PM
 */
@Getter
@Setter
public class ArticleSearchResultVO {

    private Long offset;
    private Integer limit;
    private Integer totalPages;
    private Long total;

    List<ArticleSearchResultBean> contents = new ArrayList<>();

}

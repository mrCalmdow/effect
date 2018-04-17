package effect.effect.web.vo.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author feilongchen
 * @create 2018-02-09 9:52 AM
 */
@Getter
@Setter
public class ArticleSearchResultBean {

    private String id;

    private String uid;

    private String categoryName;

    private String title;

    private String content;

    private Long views;

    private Date creTime;

    private Date updateTime;
}

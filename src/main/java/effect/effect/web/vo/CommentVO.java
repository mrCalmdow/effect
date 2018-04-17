package effect.effect.web.vo;

import effect.effect.web.vo.bean.CommentBean;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-10 12:04 PM
 */
@Getter
@Setter
public class CommentVO {

    private Long offset;
    private Integer limit;
    private Integer totalPages;
    private Long total;

    List<CommentBean> contents = new ArrayList<>();

}

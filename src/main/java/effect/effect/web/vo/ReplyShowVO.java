package effect.effect.web.vo;

import effect.effect.web.vo.bean.ReplyBean;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-18 11:28 AM
 */
@Getter
@Setter
public class ReplyShowVO {

    private Long offset;
    private Integer limit;
    private Integer totalPages;
    private Long total;

    List<ReplyBean> replies = new ArrayList<>();
}

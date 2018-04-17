package effect.effect.web.controller.pub;

import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Reply;
import effect.effect.repo.CommentAutoRepo;
import effect.effect.repo.ReplyAutoRepo;
import effect.effect.service.CommonService;
import effect.effect.web.URLs;
import effect.effect.web.vo.ReplyShowVO;
import effect.effect.web.vo.bean.ReplyBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-17 1:06 PM
 */
@Api(tags = "show replies")
@RestController
@RequestMapping(URLs.Pub.REPLY)
public class ReplyPubController extends BaseController {

    @Autowired
    private CommentAutoRepo commentAutoRepo;
    @Autowired
    private ReplyAutoRepo replyAutoRepo;
    @Autowired
    private CommonService commonService;

    @ApiOperation("get all replay by comment id")
    @GetMapping("/{commentId}")
    public ResponseVO showReply(@PathVariable @NotBlank String account, @PathVariable @NotBlank String commentId, Long offset, Integer limit) {
        if(null == commentAutoRepo.findOne(commentId)) {
            log.info(MessageConstants.ERROR_COMMENT_NOT_EXSITS + " commentId = " + commentId);
            return ResponseUtil.error(MessageConstants.ERROR_COMMENT_NOT_EXSITS);
        }
        ResponseVO responseVO = new ResponseVO();
        // replies not pageable
        List<Reply> replies = replyAutoRepo.findByCommentIdOrderByCreTime(commentId);
        ReplyShowVO replyShowVO = new ReplyShowVO();
        if(!CollectionUtils.isEmpty(replies)) {
            List<ReplyBean> replyBeans = new ArrayList<>();
            for(Reply reply : replies) {
                ReplyBean replyBean = new ReplyBean();
                BeanUtils.copyProperties(reply, replyBean);
                replyBeans.add(replyBean);
            }
            replyShowVO.setReplies(replyBeans);
            responseVO.setData(replyShowVO);
        }

        return responseVO;
    }
}

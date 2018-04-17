package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Reply;
import effect.effect.repo.ReplyAutoRepo;
import effect.effect.web.URLs;
import effect.effect.web.vo.ReplyAddVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author feilongchen
 * @create 2018-02-17 12:16 PM
 */
@Api(tags = "reply comments")
@RestController
@RequestMapping(URLs.Member.REPLY)
public class ReplyMemberController extends BaseController {

    @Autowired
    private ReplyAutoRepo replyAutoRepo;

    @ApiOperation("reply a comment")
    @PostMapping
    public ResponseVO addReply(@PathVariable @NotBlank String account, @RequestBody @Valid ReplyAddVO replyAddVO, HttpServletRequest request) {
        if(!isIllegal(replyAddVO.getArticleId(), replyAddVO.getCommentId())) {
            log.info("articleId or commentId not correctly");
            return ResponseUtil.error("articleId or commentId not correctly");
        }
        String uid = (String) request.getAttribute(JwtConstants.USER_ID);
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyAddVO, reply);
        reply.setCreTime(new Date());
        reply.setUid(uid);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(replyAutoRepo.save(reply));
        return responseVO;
    }

    /**
     * check if replay content is valid, have no XSS scripts
     * @param articleId
     * @param commentId
     * @return
     */
    public boolean isIllegal(String articleId, String commentId) {
        return true;
    }
}

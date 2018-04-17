package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Article;
import effect.effect.po.Comment;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.CommentAutoRepo;
import effect.effect.service.CommentService;
import effect.effect.web.URLs;
import effect.effect.web.vo.CommentAddVO;
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
 * @create 2018-02-10 11:49 AM
 */
@Api(tags = "comment member methods")
@RestController
@RequestMapping(URLs.Member.COMMENT)
public class CommentMemberController extends BaseController {

    @Autowired
    private CommentAutoRepo commentAutoRepo;

    @Autowired
    private ArticleAutoRepo articleAutoRepo;

    @Autowired
    private CommentService commentService;

    @ApiOperation("comment a article")
    @PostMapping
    public ResponseVO commit(@PathVariable @NotBlank String account, @RequestBody @Valid CommentAddVO commentAddVO, HttpServletRequest request) {
        if(!commentService.isValidContent(commentAddVO.getContent())) {
            log.info(MessageConstants.ERROR_COMMENT_INVALID);
            return ResponseUtil.error(MessageConstants.ERROR_COMMENT_INVALID);
        }
        Article article = articleAutoRepo.findOne(commentAddVO.getArticleId());
        if(null == article) {
            log.info(MessageConstants.ERROR_COMMENT_NOT_ALLOW);
            return ResponseUtil.error(MessageConstants.ERROR_COMMENT_NOT_ALLOW);
        }
        String uid = (String) request.getAttribute(JwtConstants.USER_ID);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddVO, comment);
        comment.setCommitUserId(uid);
        comment.setUid(article.getUid());
        comment.setCommitTime(new Date());
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(commentAutoRepo.save(comment));
        return responseVO;
    }
}

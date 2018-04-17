package effect.effect.web.controller.pub;

import effect.effect.common.constants.CommonConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Comment;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.CommentAutoRepo;
import effect.effect.service.CommentService;
import effect.effect.service.CommonService;
import effect.effect.service.UserService;
import effect.effect.web.URLs;
import effect.effect.web.vo.CommentVO;
import effect.effect.web.vo.bean.CommentBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-10 6:16 PM
 */
@Api(tags = "comment public methods")
@RestController
@RequestMapping(URLs.Pub.COMMENT)
public class CommentPubController extends BaseController {

    @Autowired
    private CommentAutoRepo commentAutoRepo;

    @Autowired
    private ArticleAutoRepo articleAutoRepo;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @ApiOperation("load comments of one article")
    @GetMapping("/{id}")
    public ResponseVO showComments(@PathVariable @NotBlank String account, @PathVariable @NotBlank String id, Long offset, Integer limit)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(null == articleAutoRepo.findOne(id)) {
            log.info(MessageConstants.ERROR_ARTICLE_NOT_EXSITS);
            return ResponseUtil.error(MessageConstants.ERROR_ARTICLE_NOT_EXSITS);
        }
        Page<Comment> page;
        Pageable pageable = commonService.generatePageRequest(offset, limit);
        page = commentAutoRepo.findByArticleId(id, pageable);
        List<Comment> comments = page.getContent();
        if(CollectionUtils.isEmpty(comments)) {
            log.info(MessageConstants.COMMENTS_IS_EMPTY);
            return ResponseUtil.success(MessageConstants.COMMENTS_IS_EMPTY);
        }
        //batch copy
        List<CommentBean> tmpContents = commonService.batchCopyProperties(comments, CommentBean.class);
        //transform comment user id to nickname
        List<CommentBean> contents = userService.transformCommitUserId2Nickname(tmpContents, CommentBean.class);
        CommentVO commentVO = new CommentVO();
        commentVO.setContents(contents);
        commentVO.setLimit(limit);
        commentVO.setOffset(offset);
        commentVO.setTotal(page.getTotalElements());
        commentVO.setTotalPages(page.getTotalPages());
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(commentVO);
        return responseVO;
    }
}

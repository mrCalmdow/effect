package effect.effect.web.controller.pub;

import effect.effect.common.constants.CommonConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.entity.BaseVO;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.PageableVO;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Article;
import effect.effect.po.User;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.ArticleCategoryService;
import effect.effect.service.ArticleService;
import effect.effect.service.CommonService;
import effect.effect.service.UserService;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleSearchResultVO;
import effect.effect.web.vo.ArticleSearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author feilongchen
 * @create 2018-01-31 11:35 PM
 */
@Api(tags = "article public methods")
@RestController
@RequestMapping(URLs.Pub.ARTICLE)
public class ArticlepubController extends BaseController {

    @Autowired
    private ArticleAutoRepo articleAutoRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommonService commonService;

    /**
     * 只在某个博客下面搜索
     * @param account
     * @param offset
     * @param limit
     * @param query
     * @return
     */
    @ApiOperation("list all articles of one user")
    @GetMapping
    public ResponseVO listArticle(@PathVariable @NotBlank String account, Long offset, Integer limit, String query) {
        User user;
        if(null == (user = userService.getUserByAccount(account))) {
            log.info("user account: {} not exists.", account);
            return ResponseUtil.error(MessageConstants.ERROR_USER_NOT_EXISTS);
        }
        Page<Article> page;
        Pageable pageable = commonService.generatePageRequest(offset, limit);
        if(StringUtils.isEmpty(query)) {
            log.info("query without condition");
            page = articleAutoRepo.findByUidOrderByUpdateTimeDesc(user.getUid(), pageable);
        } else {
            log.info("query with condition: " + query);
            page = articleAutoRepo.findByUidAndTitleContainingOrContentContainingOrderByUpdateTimeDesc(user.getUid(), query, query, pageable);
        }
        List<Article> articles = page.getContent();
        //transform category id to name
        ArticleSearchResultVO result = articleService.wrapArticlesBean(articles);
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(result);
        return responseVO;
    }

    @ApiOperation("get article details")
    @GetMapping("/{id}")
    public ResponseVO detail(@PathVariable @NotBlank String account, @PathVariable @NotBlank String id) {
        if(StringUtils.isEmpty(id)) {
            return ResponseUtil.error("article id invalid");
        }
        Article article = articleAutoRepo.findOne(id);
        if(null == article) {
            return ResponseUtil.error("article id not correctly");
        }
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(article);
        return responseVO;
    }
}

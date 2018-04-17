package effect.effect.web.controller.member;

import effect.effect.common.constants.CommonConstants;
import effect.effect.common.constants.JwtConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.util.StringUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Article;
import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.ArticleCategoryService;
import effect.effect.service.ArticleService;
import effect.effect.web.URLs;
import effect.effect.web.controller.pub.ArticlepubController;
import effect.effect.web.vo.ArticleAddVO;
import effect.effect.web.vo.ArticleSearchResultVO;
import effect.effect.web.vo.ArticleSearchVO;
import effect.effect.web.vo.ArticleUpdateVO;
import effect.effect.web.vo.bean.ArticleSearchResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @author feilongchen
 * @create 2018-01-31 10:37 PM
 */
@Api(tags = "article management methods")
@RestController
@RequestMapping(URLs.Member.ARTICLE)
public class ArticleMemberController extends BaseController {

    @Autowired
    private ArticleCategoryAutoRepo articleCategoryAutoRepo;

    @Autowired
    private ArticleAutoRepo articleAutoRepo;

    @Autowired
    private UserAutoRepo userAutoRepo;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("add a new article")
    @PostMapping
    public ResponseVO newArticle(@PathVariable @NotBlank String account, @RequestBody @Valid ArticleAddVO articleAddVO, HttpServletRequest request) {
        ArticleCategory articleCategory = articleCategoryAutoRepo.findOne(articleAddVO.getCatId());
        if(null == articleCategory) {
            return ResponseUtil.error(MessageConstants.ERROR_CATEGORY_NOT_EXSITS);
        }
        String uid = (String) request.getAttribute(JwtConstants.USER_ID);
        Article article = new Article();
        BeanUtils.copyProperties(articleAddVO, article);
        article.setUid(uid);
        article.setCreTime(new Date());
        article.setUpdateTime(new Date());
        article.setViews(0L);
        articleAutoRepo.save(article);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(article);
        return responseVO;
    }

    @ApiOperation("delete a article")
    @DeleteMapping("/{id}")
    public ResponseVO deleteArticle(@PathVariable @NotBlank String account, @PathVariable @NotBlank String id) {
        Article article = articleAutoRepo.findOne(id);
        if(null == article) {
            return ResponseUtil.error("article not exists");
        }
        log.info("delete article. id = : " + id);
        articleAutoRepo.delete(article);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(article);
        return responseVO;
    }

    @ApiOperation("update a article")
    @PutMapping
    public ResponseVO updateArticle(@PathVariable @NotBlank String account, @RequestBody @NotBlank ArticleUpdateVO articleUpdateVO) {
        if(StringUtils.isEmpty(articleUpdateVO.getId())) {
            return ResponseUtil.error("article id not correctly");
        }
        Article article = articleAutoRepo.findOne(articleUpdateVO.getId());
        if(null == article) {
            log.info("article not exists. id : " + articleUpdateVO.getId());
            return ResponseUtil.error("article not exists");
        }
        BeanUtils.copyProperties(articleUpdateVO, article);
        article.setUpdateTime(new Date());
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(articleAutoRepo.save(article));
        return responseVO;
    }
}

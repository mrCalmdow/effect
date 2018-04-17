package effect.effect.web.controller.member;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.service.UserService;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleCategoryAddVO;
import effect.effect.web.vo.ArticleCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-01-29 6:50 PM
 */
@Api(tags = "article category management methods")
@RestController
@RequestMapping(URLs.Member.CATEGORY)
public class ArticleCategoryMemberController extends BaseController {

    @Autowired
    private ArticleCategoryAutoRepo articleCategoryAutoRepo;

    @ApiOperation("add a category")
    @PostMapping
    public ResponseVO addArticleCategory(@PathVariable @NotBlank String account, @RequestBody @Valid ArticleCategoryAddVO articleCategoryAddVO,
                                         HttpServletRequest httpServletRequest) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategoryAddVO, articleCategory);
        articleCategory.setCreTime(new Date());
        articleCategory.setBelongTo((String) httpServletRequest.getAttribute(JwtConstants.USER_ID));
        articleCategory.setSort(0);
        //TODO deal sorting logic
        Object obj = articleCategoryAutoRepo.save(articleCategory);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(obj);
        return responseVO;
    }

    @ApiOperation("delete a category")
    @DeleteMapping("/{id}")
    public ResponseVO deleteArticleCategory(@PathVariable @NotBlank String account, @PathVariable @NotBlank String id) {
        ArticleCategory articleCategory = articleCategoryAutoRepo.findById(id);
        if(null == articleCategory) {
            return ResponseUtil.success("resource not exists");
        }
        articleCategoryAutoRepo.delete(articleCategory);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(articleCategory);
        return responseVO;
    }

    @ApiOperation("update article category")
    @PutMapping
    public ResponseVO updateArticleCategory(@PathVariable @NotBlank String account, @RequestBody @NotBlank ArticleCategoryVO articleCategoryVO) {
        ArticleCategory articleCategory = articleCategoryAutoRepo.findById(articleCategoryVO.getId());
        if(null == articleCategory) {
            return ResponseUtil.error("articleCategory not exists");
        }
        BeanUtils.copyProperties(articleCategoryVO, articleCategory);
        articleCategoryAutoRepo.save(articleCategory);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(articleCategory);
        return responseVO;
    }
}

package effect.effect.web.controller.pub;

import effect.effect.common.constants.MessageConstants;
import effect.effect.common.controller.BaseController;
import effect.effect.common.util.ResponseUtil;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.UserService;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feilongchen
 * @create 2018-01-29 6:15 PM
 */
@Api(tags = "Article category")
@RestController
@RequestMapping(URLs.Pub.CATEGORY)
public class ArticleCategoryPubController extends BaseController {

    @Autowired
    private ArticleCategoryAutoRepo articleCategoryAutoRepo;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "list article categories belongs to someone")
    @GetMapping
    public ResponseVO getArticleCategoryByAccount(@PathVariable @NotBlank String account) {
        ResponseVO responseVO = new ResponseVO();
        User user = userService.getUserByAccount(account);
        if(null == user || StringUtils.isEmpty(user.getUid())) {
            log.info("user account: [" + account + "] not exist.");
            return ResponseUtil.error(MessageConstants.ERROR_USER_NOT_EXISTS);
        }
        List<ArticleCategory> articleCategories = articleCategoryAutoRepo.findByBelongTo(user.getUid());
        if(CollectionUtils.isEmpty(articleCategories)) {
            return ResponseUtil.success(MessageConstants.CATEGORY_NONE_CONTENT);
        }
        List<ArticleCategoryVO> articleCategoryVOList = new ArrayList<>();
        for(ArticleCategory articleCategory : articleCategories) {
            ArticleCategoryVO articleCategoryVO = new ArticleCategoryVO();
            BeanUtils.copyProperties(articleCategory, articleCategoryVO);
            articleCategoryVOList.add(articleCategoryVO);
        }
        responseVO.setData(articleCategoryVOList);
        return responseVO;
    }
}

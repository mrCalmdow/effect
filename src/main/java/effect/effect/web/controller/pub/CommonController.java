package effect.effect.web.controller.pub;

import effect.effect.common.controller.BaseController;
import effect.effect.common.vo.ResponseVO;
import effect.effect.po.Article;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.service.ArticleService;
import effect.effect.service.CommonService;
import effect.effect.web.URLs;
import effect.effect.web.vo.ArticleSearchResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author feilongchen
 * @create 2018-02-07 6:11 PM
 */
@Api(tags = "Common methods")
@RestController
@RequestMapping(URLs.COMMON)
public class CommonController extends BaseController {
    @Autowired
    private ArticleAutoRepo articleAutoRepo;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommonService commonService;

    /**
     * 整个博客站下所有博主的文章内容中搜索
     * @param query
     * @param offset
     * @param limit
     * @return
     */
    @ApiOperation("fuzzy search article by title or content")
    @GetMapping("/search.do")
    public ResponseVO queryArticle(String query, Long offset, Integer limit) {
        Page<Article> page;
        Pageable pageable = commonService.generatePageRequest(offset, limit);
        if(StringUtils.isEmpty(query)) {
            log.info("Common query without condition");
            page = articleAutoRepo.findAll(pageable);
        } else {
            log.info("Common query with condition: " + query);
            page = articleAutoRepo.findByTitleContainingOrContentContainingOrderByUpdateTimeDesc(query, query, pageable);
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
}

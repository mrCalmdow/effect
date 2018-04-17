package effect.effect.web;

import effect.effect.common.constants.JwtConstants;
import effect.effect.common.security.JwtTokenService;
import effect.effect.common.util.StringUtil;
import effect.effect.po.Article;
import effect.effect.po.ArticleCategory;
import effect.effect.po.User;
import effect.effect.repo.ArticleAutoRepo;
import effect.effect.repo.ArticleCategoryAutoRepo;
import effect.effect.repo.CommentAutoRepo;
import effect.effect.repo.UserAutoRepo;
import effect.effect.service.UserService;
import effect.effect.web.vo.ArticleAddVO;
import effect.effect.web.vo.LoginVO;
import effect.effect.web.vo.RegisterVO;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseCommonControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserAutoRepo userAutoRepo;

    @Autowired
    protected ArticleAutoRepo articleAutoRepo;

    @Autowired
    protected ArticleCategoryAutoRepo articleCategoryAutoRepo;

    @Autowired
    protected CommentAutoRepo commentAutoRepo;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected UserService userService;

    @Value("${jwt.header}")
    protected String authenticHeader;

    protected final String account = "18688888888";
    protected final String password = "111111";
    protected final String nickname = "tester";
    protected final String email = "tester@mail.com";


    protected String CreateToken(String account) {
        return jwtTokenService.generateToken(account, JwtConstants.WEB);

    }

    protected int getCurrentPageSize(int total, int pageSize, int offset) {
        return total - offset > pageSize ? pageSize : total - offset;
    }


    protected RegisterVO generateRegisterVO() {
        RegisterVO registerVO = new RegisterVO();
        registerVO.setAccount(account);
        registerVO.setNickname(nickname);
        registerVO.setPassword(password);
        registerVO.setEmail(email);
        return registerVO;
    }

    protected LoginVO generateLoginVO() {
        LoginVO loginVO = new LoginVO();
        loginVO.setAccount(account);
        loginVO.setPassword(password);
        return loginVO;
    }
    protected LoginVO generateLoginVO(String account, String password) {
        LoginVO loginVO = new LoginVO();
        loginVO.setAccount(account);
        loginVO.setPassword(password);
        return loginVO;
    }

    protected User createUser() {
        return createUser(null, null, null, null);
    }
    protected User createUser(String account) {
        return createUser(account, null, null, null);
    }

    /**
     * create users unique by account
     * @param account
     * @param password
     * @param nickname
     * @param email
     * @return
     */
    protected User createUser(String account, String password, String nickname, String email) {
        User user = new User();
        if(StringUtils.isEmpty(account)) {
            user.setAccount(this.account);
        } else {
            User tmp = userAutoRepo.findByAccount(account);
            if(null != tmp) {
                return tmp;
            }
            user.setAccount(account);
        }
        if(StringUtils.isEmpty(nickname)) {
            user.setPassword(this.password);
        } else {
            user.setPassword(password);
        }
        if(StringUtils.isEmpty(nickname)) {
            user.setNickname(this.nickname);
        } else {
            user.setNickname(nickname);
        }
        if(StringUtils.isEmpty(email)) {
            user.setEmail(this.email);
        } else {
            user.setEmail(email);
        }
        return userAutoRepo.save(user);
    }

    protected String createUrlTemplate(String url, String account) {
        return StringUtils.replace(url, "{account}", account);
    }


    protected ArticleAddVO generateArticleAddVO() {
        return null;
    }

    /**
     * create a category and user
     * @param
     * @param userAccount
     * @return category's id
     */
    protected String ready2AddArticle(String userAccount, String categoryName) {
        User user = createUser(userAccount);
        if(StringUtils.isEmpty(categoryName)) {
            //create user
            return createArtileCategroy(user.getUid(), "test category");
        }
        return createArtileCategroy(user.getUid(), categoryName);
    }

    private String createArtileCategroy(String uid, String name) {
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setBelongTo(uid);
        articleCategory.setName(name);
        articleCategory.setSort(0);
        return articleCategoryAutoRepo.save(articleCategory).getId();
    }

    /**
     * create article for follow-up steps
     * @param userAcount
     * @param articleTitle
     * @return article object
     */
    protected Article ready2Comment(String userAcount, String articleTitle) {
        String cId = ready2AddArticle(userAcount, null);
        Article article = new Article();
        article.setCatId(cId);
        article.setUid(userService.getUserByAccount(userAcount).getUid());
        article.setTitle(articleTitle);
        article.setContent(articleTitle + userAcount);
        article.setCreTime(new Date());
        return articleAutoRepo.save(article);
    }

    //工具 比较source，target里面 相同名字属性的值是否一样
    // （排除ID与List 集合比较）
    // (包含有除LIST外其他集合类型请勿使用)
    public Boolean objectEqual(Object source, Object target) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Class sClass = source.getClass();
        Class tClass = target.getClass();
        Field[] files = sClass.getDeclaredFields();
        Field[] tfiles = tClass.getDeclaredFields();
        for (Field sField : files) {
            String sName = sField.getName();
            Class type = sField.getType();

            if (sName == "id" || type.getSimpleName().equals("List") || type.getSimpleName().equals("Map")) {
                continue;
            }
            String sMethodName = sName.substring(0, 1).toUpperCase() + sName.substring(1);
            Method sGetMethod = sClass.getMethod("get" + sMethodName);
            Object value = sGetMethod.invoke(source);
            if (null == value) {
                continue;
            }
            //若为TRUE则 为 有值相等的字段或者 没有找到对应字段。
            int counter = 1;
            for (Field tField : tfiles) {
                String tName = tField.getName();
                Class tType = tField.getType();
                String tMethodName = tName.substring(0, 1).toUpperCase() + tName.substring(1);
                Method tGetMethod = null;
                try {
                    tGetMethod = tClass.getMethod("get" + tMethodName);
                } catch (Exception e) {
                    counter = counter + 1;
                    continue;
                }
                Object tValue = tGetMethod.invoke(target);
                if (sName.equals(tName) && type.equals(tType)) {
                    if (value.equals(tValue)) {
                        break;
                    } else {
                        Assert.assertTrue(sName + "不匹配", false);
                        return false;
                    }
                }
                if (counter >= tfiles.length) {
                    //若没有一个字段能够与之匹配则为错误
                    break;
                }
                counter = counter + 1;
            }

        }
        return true;

    }

    /**
     * 随机生成用户id
     *
     * @return
     */
    protected String generateUserId() {
        return StringUtil.getUUID();
    }
}
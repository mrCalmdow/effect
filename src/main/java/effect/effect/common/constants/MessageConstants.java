package effect.effect.common.constants;

/**
 * @outhor feilongchen
 * @create 2018-01-22 8:07 PM
 */
public interface MessageConstants {

    String ERROR_USER_EXISTS = "user has exists";
    String ERROR_USER_NOT_EXISTS = "user not exists";
    String ERROR_INVALID_PASSWORD = "invalid password";
    String ERROR_OLD_PASSWORD_INCORRECT = "old password incorrect";

    String TOKEN_INVALID = "token is invalid";
    String RESET_PASSWORD_SUCCESS = "reset password success";
    String RESET_PASSWORD_FAILURE = "reset password failure";

    String CATEGORY_NONE_CONTENT = "category have nothing";
    String ERROR_CATEGORY_NAME_EMPTY = "article category name can't be empty";
    String ERROR_CATEGORY_NOT_EXSITS = "article category not exists";

    String ERROR_ARTICLE_NOT_EXSITS = "article not exists";

    String ERROR_COMMENT_INVALID = "comment content invalid.";
    String ERROR_COMMENT_NOT_EXSITS = "comment not exits";
    String ERROR_COMMENT_NOT_ALLOW = "article not allow to comment";

    String COMMENTS_IS_EMPTY = "article has no comment";
}

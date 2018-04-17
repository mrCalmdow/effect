package effect.effect.web;

/**
 * @author feilongchen
 * @create 2018-02-08 6:47 PM
 */
public interface URLs {

    String V1 = "/v1";
    String V2 = "/v2";
    String PUBLIC = "/pub";
    String MEMBER = "/member";
    String ACCOUNT = "/{account}";

    String COMMON = V1 + PUBLIC;

    interface User {
        String PUB_USER = V1 + PUBLIC + "/users";
        String MEMBER_USER = V1 + MEMBER + "/users";
    }

    //公共API
    interface Pub {
        String ROOT = V1 + PUBLIC + ACCOUNT;

        String ARTICLE = ROOT + "/articles";
        String CATEGORY = ROOT + "/categories";
        String COMMENT = ROOT + "/comments";
        String REPLY = ROOT + "/replies";
    }

    //需要鉴权的API
    interface Member {
        String ROOT = V1 + MEMBER + ACCOUNT;

        String ARTICLE = ROOT + "/articles";
        String CATEGORY = ROOT + "/categories";
        String COMMENT = ROOT + "/comments";
        String REPLY = ROOT + "/replies";
    }
}

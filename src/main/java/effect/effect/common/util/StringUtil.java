package effect.effect.common.util;

import java.util.Random;
import java.util.UUID;

public class StringUtil {

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String generateCode() {
        return generateCode(6);
    }

    public static String generateCode(int b) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < b; i++) {
            code.append(random.nextInt(9));
        }
        return code.toString();
    }

    /**
     * 处理短信模板
     *
     * @param content
     * @param replaceCode
     * @param params
     * @return
     */
    public static String dealSmsContent(String content, String replaceCode, String... params) {
        for (int i = 0; i < params.length; i++) {
            content = content.replaceFirst(replaceCode, params[i]);
        }
        return content;
    }

    /**
     * 将String... 转换成String,String,String
     *
     * @param params
     * @return
     */
    public static String getParamsToString(String... params) {
        int length = params.length;
        if (length > 0) {
            if (length > 1) {
                String param = "";
                for (int i = 0; i < length; i++) {
                    String p = "";
                    if (i == length - 1) {
                        p = params[i];
                    } else {
                        p = params[i] + ",";
                    }
                    param += p;
                }
                return param;
            }
            return params[0];
        }
        return "";
    }
}

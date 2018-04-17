package effect.effect.domain;

import effect.effect.common.annonation.Domain;
import effect.effect.common.util.MD5Util;
import effect.effect.po.User;
import effect.effect.repo.UserAutoRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author feilongchen
 * @create 2018-01-23 11:26 PM
 */
@Domain
@Getter
@Setter
public class UserDomain {

    @Autowired
    private UserAutoRepo userAutoRepo;
    private User entity;

    /**
     * check if user exists
     * @return
     */
    public boolean checkValid() {
        return null != entity;
    }

    /**
     * check if password is correctly
     * @param rawPassword
     * @return
     */
    public boolean checkPassword(String rawPassword) {
        String encryptPassword = MD5Util.MD5Encrypt(rawPassword);
        return encryptPassword.equals(entity.getPassword());
    }

    /**
     * check if user have exists
     * @param account
     * @return
     */
    public boolean checkUserExists(String account) {
        User user = userAutoRepo.findByAccount(account);
        if(null == user) {
            return false;
        }
        return true;
    }
}

package effect.effect.repo;

import effect.effect.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Beldon
 * @create 2018-01-22 下午3:34
 */
public interface UserAutoRepo extends JpaRepository<User, String> {

    User findByAccount(String account);
    User findByUid(String userId);
}

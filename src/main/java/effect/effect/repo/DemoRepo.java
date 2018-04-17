package effect.effect.repo;

import effect.effect.po.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Beldon
 * @create 2018-01-22 下午2:14
 */
public interface DemoRepo extends JpaRepository<Demo,String> {
}

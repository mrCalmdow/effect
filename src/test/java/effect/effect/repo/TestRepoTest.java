package effect.effect.repo;

import effect.effect.domain.DomainFactory;
import effect.effect.domain.DemoDomain;
import effect.effect.po.Demo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Beldon
 * @create 2018-01-22 下午2:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRepoTest {

    @Autowired
    private DemoRepo demoRepo;


    @Before
    public void init() {
        demoRepo.deleteAll();
    }


    @Test
    public void save() throws Exception {
        Demo demo = new Demo();
        demo.setName("name");
        demo.setAge(10);
        demoRepo.save(demo);

        List<Demo> demoList = demoRepo.findAll();
        Assert.assertNotNull(demoList);
        Assert.assertEquals(1, demoList.size());
        Demo temp = demoList.get(0);
        Assert.assertEquals(temp.getName(), demo.getName());
        Assert.assertEquals(temp.getAge(), demo.getAge());

    }


    @Test
    public void domainTest() {
        DemoDomain demoDomain = DomainFactory.createDomain(DemoDomain.class);
        demoDomain.setEntity(new Demo());
    }
}
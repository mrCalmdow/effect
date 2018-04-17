package effect.effect.domain;

import effect.effect.common.annonation.Domain;
import effect.effect.po.Demo;
import effect.effect.repo.DemoRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Domain
public class DemoDomain {
    @Autowired
    private DemoRepo demoRepo;

    @Getter
    @Setter
    private Demo entity;

}

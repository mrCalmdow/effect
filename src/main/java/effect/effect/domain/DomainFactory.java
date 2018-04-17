package effect.effect.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DomainFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static  <T> T createDomain(Class<T> clazz) {
        return getBean(clazz);
    }

    public static  <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DomainFactory.applicationContext = applicationContext;
    }
}
package org.burgeon.sbd.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanFactory.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <Entity, Key> DomainRepository<Entity, Key> getDomainRepository(Class<Entity> entityClass,
                                                                                  Class<Key> keyClass) {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(
                ResolvableType.forClassWithGenerics(DomainRepository.class, entityClass, keyClass));
        DomainRepository<Entity, Key> repository = (DomainRepository<Entity, Key>)
                applicationContext.getBean(beanNamesForType[0]);
        return repository;
    }

}

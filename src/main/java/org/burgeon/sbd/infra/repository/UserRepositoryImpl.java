package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.core.AbstractDomainRepository;
import org.burgeon.sbd.domain.user.UserAggregate;
import org.burgeon.sbd.infra.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class UserRepositoryImpl extends AbstractDomainRepository<UserAggregate, String> {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserAggregate load(String productNo) {
        Optional<UserEntity> optional = userEntityRepository.findById(productNo);
        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();
            UserAggregate productAggregate = userEntity.to(UserAggregate.class);
            return productAggregate;
        }
        return null;
    }

    @Override
    public void save(UserAggregate product) {
        UserEntity userEntity = product.to(UserEntity.class);
        userEntityRepository.save(userEntity);
    }

}

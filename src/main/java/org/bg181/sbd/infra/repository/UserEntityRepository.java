package org.bg181.sbd.infra.repository;

import org.bg181.sbd.infra.repository.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
public interface UserEntityRepository extends PagingAndSortingRepository<UserEntity, String> {

    /**
     * 根据username查询用户
     *
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

}

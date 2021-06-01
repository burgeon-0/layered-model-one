package org.burgeon.sbd.infra.repository.entity;

import org.burgeon.sbd.core.base.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Entity(name = "user")
public class UserEntity extends Base {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

}

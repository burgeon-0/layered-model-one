package org.burgeon.sbd.infra.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@Entity
@Table(name = "t_user")
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseModel {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private Boolean isAdmin;

}

package org.bg181.sbd.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBaseModel extends BaseModel {

    private String username;
    private String password;
    private Boolean isAdmin;

}

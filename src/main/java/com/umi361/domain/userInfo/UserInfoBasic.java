package com.umi361.domain.userInfo;

import org.icesea.genericDAO.orm.*;

@Table(nameStyle = NameStyle.SNAKE, masterField = "openid", updatePolicy = UpdatePolicy.OVERWRITE_NOT_DEFAULT)
public class UserInfoBasic {
    @Field(fieldPolicy = FieldPolicy.USE_DEFAULT)
    private Long user_id;
    @Field(fieldPolicy = FieldPolicy.MASTER_FIELD)
    private String openid;
    private String unionid;
    private Boolean registered;

    @Override
    public String toString() {
        return "UserInfoBasic{" +
                "user_id=" + user_id +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", registered=" + registered +
                '}';
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public UserInfoBasic(Long user_id, String openid, String unionid, Boolean registered) {

        this.user_id = user_id;
        this.openid = openid;
        this.unionid = unionid;
        this.registered = registered;
    }

    public UserInfoBasic() {}
}

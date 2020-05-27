package com.umi361.domain.userInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.icesea.genericDAO.orm.ForeignKey;
import org.icesea.genericDAO.orm.NameStyle;
import org.icesea.genericDAO.orm.Table;
import org.icesea.genericDAO.orm.UpdatePolicy;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


/*
{
  "subscribe": 1,
  "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
  "nickname": "Band",
  "sex": 1,
  "language": "zh_CN",
  "city": "广州",
  "province": "广东",
  "country": "中国",
  "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
eMsv84eavHiaiceqxibJxCfHe/0",
  "subscribe_time": 1382694957,
  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
  "remark": "",
  "groupid": 0,
  "tagid_list":[128,2]
}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@Table(nameStyle = NameStyle.SNAKE, updatePolicy = UpdatePolicy.UPDATE_NOT_NULL_NOR_DEFAULT, masterField = "openid")
public class UserInfoWechat {
    @ForeignKey(ReferencedTable = "user_info_basic", FilterFieldsSelf = "openid")
    private Long user_id;
    private String openid;
    private String unionid;
    private String nickname;
    /**
     * 性别：0 - 未知；1 - 男性；2 - 女性
     */
    private Integer sex;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
    private Timestamp subscribe_time;
    private String remark;

    @Override
    public String toString() {
        return "UserInfoWechat{" +
                "user_id=" + user_id +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribe_time=" + subscribe_time +
                ", remark='" + remark + '\'' +
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Timestamp getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Timestamp subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.umi361.domain.userInfo;

import org.icesea.genericDAO.orm.*;

@Table(nameStyle = NameStyle.SNAKE, updatePolicy = UpdatePolicy.UPDATE_NOT_NULL_NOR_DEFAULT)
public class UserInfoMain {
    @Field(nameStyle = NameStyle.SNAKE, fieldPolicy = FieldPolicy.MASTER_FIELD)
    private Long userId;
    @Field(nameStyle = NameStyle.SNAKE)
    private String studentId;
    @Field(nameStyle = NameStyle.SNAKE)
    private String legalName;
    @Field(nameStyle = NameStyle.SNAKE)
    private Long collegeId;
    @Field(nameStyle = NameStyle.SNAKE)
    private Long professionId;

    @Override
    public String toString() {
        return "UserInfoMain{" +
                "userId=" + userId +
                ", studentId='" + studentId + '\'' +
                ", legalName='" + legalName + '\'' +
                ", collegeId=" + collegeId +
                ", professionId=" + professionId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Long professionId) {
        this.professionId = professionId;
    }

    public UserInfoMain(Long userId, String studentId, String legalName, Long collegeId, Long professionId) {

        this.userId = userId;
        this.studentId = studentId;
        this.legalName = legalName;
        this.collegeId = collegeId;
        this.professionId = professionId;
    }

    public UserInfoMain() {}
}

package com.fancy.learncenter.bean;

import java.util.List;

/**
 * Created by hyy on 2017/12/26.
 * describe as
 */

public class RegistDataBean {
    /**
     * id : 4
     * username : 123
     * password : null
     * avatar : /assets/images/ava/default.png
     * name : null
     * email : null
     * mobile : 18792112781
     * posts : 0
     * comments : 0
     * follows : 0
     * fans : 0
     * favors : 0
     * created : 2017-12-26
     * source : 0
     * lastLogin : null
     * signature : null
     * status : 0
     * activeEmail : 0
     * roles : []
     */

    private int id;
    private String username;
    private Object password;
    private String avatar;
    private Object name;
    private Object email;
    private String mobile;
    private int posts;
    private int comments;
    private int follows;
    private int fans;
    private int favors;
    private String created;
    private int source;
    private Object lastLogin;
    private Object signature;
    private int status;
    private int activeEmail;
    private List<?> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFavors() {
        return favors;
    }

    public void setFavors(int favors) {
        this.favors = favors;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) {
        this.signature = signature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getActiveEmail() {
        return activeEmail;
    }

    public void setActiveEmail(int activeEmail) {
        this.activeEmail = activeEmail;
    }

    public List<?> getRoles() {
        return roles;
    }

    public void setRoles(List<?> roles) {
        this.roles = roles;
    }
}

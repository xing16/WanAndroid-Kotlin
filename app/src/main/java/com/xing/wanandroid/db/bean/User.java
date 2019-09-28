package com.xing.wanandroid.db.bean;

import com.xing.wanandroid.db.IntConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/***
 *
 *  Kotlin 实体类不支持生成 dao
 */

@Entity
public class User {
    private boolean admin;
    @Convert(columnType = String.class, converter = IntConverter.class)
    private List<Integer> collectionIds;
    private String email;
    private String icon;
    private int id;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    @Id
    private String username;

    @Generated(hash = 1304374846)
    public User(boolean admin, List<Integer> collectionIds, String email,
                String icon, int id, String nickname, String password,
                String publicName, String token, int type, String username) {
        this.admin = admin;
        this.collectionIds = collectionIds;
        this.email = email;
        this.icon = icon;
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.publicName = publicName;
        this.token = token;
        this.type = type;
        this.username = username;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Integer> getCollectionIds() {
        return this.collectionIds;
    }

    public void setCollectionIds(List<Integer> collectionIds) {
        this.collectionIds = collectionIds;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return this.publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "admin=" + admin +
                ", collectionIds=" + collectionIds +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", publicName='" + publicName + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}

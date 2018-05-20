package com.xiv.gearplanner.models;

import javax.persistence.*;

@Table
@Entity
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private  String userId;
    @Column
    private  String providerId;
    @Column
    private  String providerUserId;
    @Column
    private  int rank;
    @Column
    private  String displayName;
    @Column
    private  String profileUrl;
    @Column
    private  String imageUrl;
    @Column
    private  String accessToken;
    @Column
    private  String secret;
    @Column
    private  String refreshToken;
    @Column
    private  Long expireTime;

    public UserConnection() {}

    public UserConnection(String userId, String providerId, String providerUserId, int rank, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime) {
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return
                "userId = " + userId +
                        ", providerId = " + providerId +
                        ", providerUserId = " + providerUserId +
                        ", rank = " + rank +
                        ", displayName = " + displayName +
                        ", profileUrl = " + profileUrl +
                        ", imageUrl = " + imageUrl +
                        ", accessToken = " + accessToken +
                        ", secret = " + secret +
                        ", refreshToken = " + refreshToken +
                        ", expireTime = " + expireTime;
    }

}
package com.mmall.pojo;

public class QrCode {
    private Integer id;

    private String uuid;

    private String username;

    private String password;

    private String qrcodeImage;

    public QrCode(Integer id, String uuid, String username, String password, String qrcodeImage) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.qrcodeImage = qrcodeImage;
    }

    public QrCode() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getQrcodeImage() {
        return qrcodeImage;
    }

    public void setQrcodeImage(String qrcodeImage) {
        this.qrcodeImage = qrcodeImage == null ? null : qrcodeImage.trim();
    }
}
package com.fenghuang.caipiaobao.widget.gift;


public class SendGiftBean extends BaseGiftBean {

    /**
     * 用户id
     */
    private int userId;
    /**
     * 礼物id
     */
    private int giftId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userPhoto;
    /**
     * 礼物名称
     */
    private String giftName;
    /**
     * 礼物本地图片也可以定义为远程url
     */
    private String giftImg;
    /**
     * 礼物持续时间
     */
    private long giftStayTime;

    /**
     * 单次礼物数目
     */
    private int giftSendSize = 1;

    public SendGiftBean() {
    }

    public SendGiftBean(int userId, int giftId, String userName, String userPhoto, String giftName, String giftImg, long time) {
        this.userId = userId;
        this.giftId = giftId;
        this.userName = userName;
        this.giftName = giftName;
        this.giftImg = giftImg;
        this.giftStayTime = time;
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Override
    public int getTheGiftId() {
        return giftId;
    }

    @Override
    public void setTheGiftId(int gid) {
        this.giftId = gid;
    }

    @Override
    public int getTheUserId() {
        return userId;
    }

    @Override
    public void setTheUserId(int uid) {
        this.userId = uid;
    }

    @Override
    public int getTheSendGiftSize() {
        return giftSendSize;
    }

    @Override
    public void setTheSendGiftSize(int size) {
        giftSendSize = size;
    }

    @Override
    public long getTheGiftStay() {
        return giftStayTime;
    }

    @Override
    public void setTheGiftStay(long stay) {
        giftStayTime = stay;
    }

}

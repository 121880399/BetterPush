package org.zzy.libpush.entity;


import android.os.Parcel;

/**
 * @作者 ZhouZhengyi
 * @创建日期 2019/5/30
 */
public class PushInfo implements android.os.Parcelable {
    /**
     * 信息唯一标识
     */
    private String messageId;
    /**
     * 信息标题
     */
    private String title;
    /**
     * 信息内容体
     */
    private String message;
    /**
     * 收到时间毫秒数
     */
    private long receiveMills;
    /**
     * 是否进入消息中心 1.进入
     */
    private int isIntoMC;
    /**
     * 消息来源
     * 1.Socket,2.GCM,3.小米,4.APNS
     */
    private int channel;
    /**
     * 推送要打开的行为,首约协议
     */
    private String action;
    /**
     * 扩展消息内容
     */
    private String extraMsg;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getReceiveMills() {
        return receiveMills;
    }

    public void setReceiveMills(long receiveMills) {
        this.receiveMills = receiveMills;
    }

    public int getIsIntoMC() {
        return isIntoMC;
    }

    public void setIsIntoMC(int isIntoMC) {
        this.isIntoMC = isIntoMC;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.messageId);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeLong(this.receiveMills);
        dest.writeInt(this.isIntoMC);
        dest.writeInt(this.channel);
        dest.writeString(this.action);
        dest.writeString(this.extraMsg);
    }

    public PushInfo() {}

    protected PushInfo(Parcel in) {
        this.messageId = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.receiveMills = in.readLong();
        this.isIntoMC = in.readInt();
        this.channel = in.readInt();
        this.action = in.readString();
        this.extraMsg = in.readString();
    }

    public static final Creator<PushInfo> CREATOR = new Creator<PushInfo>() {
        @Override
        public PushInfo createFromParcel(Parcel source) {return new PushInfo(source);}

        @Override
        public PushInfo[] newArray(int size) {return new PushInfo[size];}
    };
}

package com.ezorm.model;

/**
 * Created by Li Yu on 2017/6/6.
 */
public class Package {
    Long pkgId;
    String pkgName;
    Long voiceLimit;
    Long msgLimit;
    Long dataLimit;

    public Long getPkgId() {
        return pkgId;
    }

    public void setPkgId(Long pkgId) {
        this.pkgId = pkgId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Long getVoiceLimit() {
        return voiceLimit;
    }

    public void setVoiceLimit(Long voiceLimit) {
        this.voiceLimit = voiceLimit;
    }

    public Long getMsgLimit() {
        return msgLimit;
    }

    public void setMsgLimit(Long msgLimit) {
        this.msgLimit = msgLimit;
    }

    public Long getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(Long dataLimit) {
        this.dataLimit = dataLimit;
    }

    @Override
    public String toString() {
        return "Package:[ id="+pkgId
                +"; pkgName="+pkgName
                +", voiceLimit="+voiceLimit
                +", msgLimit=" +msgLimit
                +", dataLimit="+dataLimit
                +"]";
    }
}

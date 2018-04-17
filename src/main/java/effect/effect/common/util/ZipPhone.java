package effect.effect.common.util;

/**
 * @author Beldon.
 * Copyright (c)  2017/11/7, All Rights Reserved.
 */
public class ZipPhone {
    private String zip;
    private String phone;

    public ZipPhone() {

    }

    public ZipPhone(String phone) {
        this.phone = phone;
    }

    public ZipPhone(String zip, String phone) {
        this.zip = zip;
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

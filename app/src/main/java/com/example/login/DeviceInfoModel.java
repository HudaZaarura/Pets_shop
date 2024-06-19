package com.example.login;

public class DeviceInfoModel {

    public String deviceName, deviceHardwareAddress;

    // البناء الافتراضي لنموذج معلومات الجهاز
    public DeviceInfoModel(){}

    // بناء نموذج معلومات الجهاز مع استخدام اسم الجهاز وعنوان الأجهزة
    public DeviceInfoModel(String deviceName, String deviceHardwareAddress){
        this.deviceName = deviceName;
        this.deviceHardwareAddress = deviceHardwareAddress;
    }

    // الحصول على اسم الجهاز
    public String getDeviceName(){return deviceName;}

    // الحصول على عنوان الجهاز
    public String getDeviceHardwareAddress(){return deviceHardwareAddress;}

}
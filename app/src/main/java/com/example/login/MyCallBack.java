package com.example.login;

import java.util.List;

// واجهة تستخدم كمنبثق للاستجابة بعد الانتهاء من استرداد البيانات من قاعدة البيانات
public interface MyCallBack {
    void onCallback(List<Pet> attractionsList);    // الوظيفة التي سيتم استدعاؤها بعد الانتهاء من استرداد البيانات

}

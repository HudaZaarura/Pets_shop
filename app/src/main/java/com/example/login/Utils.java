package com.example.login;

import android.content.Context;

public class Utils {

    public static Utils instance;
    private BtControllerActivity mainAct;

    public BtControllerActivity getMainAct() {
        return mainAct;
    }

    public Utils(BtControllerActivity mainAct)
    {
        this.mainAct = mainAct;
    }

    public Utils()
    {
    }
    // الحصول على مثيل من Utils مع تمرير النشاط الرئيسي
    public static Utils getInstance(BtControllerActivity mainAct)
    {
        if (instance == null)
            instance = new Utils(mainAct);

        return instance;
    }

    public static Utils getInstance()
    {
        if (instance == null)
            instance = new Utils();

        return instance;
    }
}

package com.example.login;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// الرموز للأخطاء المحتملة
enum ErrorCodes
{
    IncorrectAuth, FieldsEmpty, True, False
}

enum etCat
{
    Cat, Dog, Horse
}

public class Utilities {
    private static Utilities instance;

    // البناء
    public Utilities()
    {}

    // الحصول على مثيل من الفئة
    public static Utilities getInstance()
    {
        if (instance == null)
            instance = new Utilities();

        return instance;
    }

    // التحقق من صحة البريد الإلكتروني
    public boolean validateEmail(String username)
    {
        return true;
    }

    // التحقق من صحة كلمة المرور
    public boolean validatePassword(String password)
    {
        return true;
    }

    // التحقق مما إذا كانت السلسلة فارغة بعد إزالة الفراغات
    public boolean checkTrimEmpty(String text)
    {
        return text.trim().isEmpty();
    }
}



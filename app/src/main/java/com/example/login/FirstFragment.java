package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.login.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding; // كائن لربط واجهة المستخدم بالشفرة

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // استخدام البيانات المرتبطة بالربط لإنشاء واجهة المستخدم
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot(); // إعادة العرض الجذري لواجهة المستخدم

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // تعيين مستمع للزر
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // التنقل إلى الفراغ الثاني عند النقر على الزر
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        // تحرير موارد الربط عند تدمير واجهة المستخدم
        super.onDestroyView();
        binding = null;
    }

}
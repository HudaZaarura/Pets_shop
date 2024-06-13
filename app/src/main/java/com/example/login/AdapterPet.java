package com.example.login;
// تعريف الحزمة التي تحتوي على هذا الكود

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.List;

// بداية تعريف صف AdapterPet الذي يرث من RecyclerView.Adapter
public class AdapterPet extends RecyclerView.Adapter<AdapterPet.ViewHolder> {

    // المتغيرات الخاصة بالبيانات والمحول والسياق
    private List<Pet> mData;  // قائمة البيانات من نوع Pet
    private LayoutInflater mInflater;  // محول لتوسيع التخطيط
    private Context context;  // السياق الحالي

    // تعريف مستمع النقر على العناصر
    private final AdapterPet.ItemClickListener mClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Pet pet = mData.get(position);  // الحصول على العنصر الذي تم النقر عليه
            Intent i = new Intent(context, PetDetailsActivity.class);  // إنشاء نية للانتقال إلى النشاط PetDetailsActivity
            i.putExtra("pet", pet);  // إضافة البيانات إلى النية
            context.startActivity(i);  // بدء النشاط الجديد
        }
    };

    // المُنشئ الذي يستقبل السياق وقائمة البيانات
    AdapterPet(Context context, List<Pet> data) {
        this.mInflater = LayoutInflater.from(context);  // الحصول على المحول من السياق
        this.mData = data;  // تخزين البيانات في المتغير الخاص
        this.context = context;  // تخزين السياق في المتغير الخاص
    }

    // إنشاء وتوسيع تخطيط الصف من ملف XML عند الحاجة
    @Override
    public AdapterPet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row, parent, false);  // توسيع التخطيط من ملف XML
        return new AdapterPet.ViewHolder(view);  // إنشاء كائن ViewHolder جديد
    }

    // ربط البيانات بعناصر الواجهة في كل صف
    @Override
    public void onBindViewHolder(AdapterPet.ViewHolder holder, int position) {
        Pet pet = mData.get(position);  // الحصول على العنصر في الموقع المحدد
        holder.tvName.setText(pet.getDescription());  // تعيين الوصف في TextView
        holder.txCatg.setText(pet.getCategory());  // تعيين الفئة في TextView
        holder.txAge.setText(pet.getAge());  // تعيين العمر في TextView
        if (pet.getPhoto() == null || pet.getPhoto().isEmpty()) {
            Picasso.get().load(R.drawable.img).into(holder.ivPhoto);  // تحميل صورة افتراضية إذا لم يكن هناك صورة
        } else {
            Picasso.get().load(pet.getPhoto()).into(holder.ivPhoto);  // تحميل الصورة من الرابط
        }
    }

    // إرجاع العدد الإجمالي للعناصر
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // تعريف فئة ViewHolder التي تخزن وتعيد تدوير المشاهد
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;  // عنصر TextView لاسم الحيوان الأليف
        ImageView ivPhoto;  // عنصر ImageView لعرض صورة الحيوان الأليف
        TextView txAge;  // عنصر TextView للعمر
        TextView txCatg;  // عنصر TextView للفئة
        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);  // العثور على TextView الاسم في تخطيط الصف
            ivPhoto = itemView.findViewById(R.id.imageViewrow);  // العثور على ImageView في تخطيط الصف
            txAge = itemView.findViewById(R.id.age);  // العثور على TextView العمر في تخطيط الصف
            txCatg = itemView.findViewById(R.id.detailsrow);  // العثور على TextView الفئة في تخطيط الصف
            itemView.setOnClickListener(this);  // تعيين مستمع النقر على العنصر الحالي
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());  // استدعاء مستمع النقر عند النقر على العنصر
        }
    }

    // طريقة للحصول على العنصر عند موقع معين
    Pet getItem(int id) {
        return mData.get(id);
    }

    // تعريف واجهة لمستمع النقرات (يمكن أن تنفذها النشاطات أو الشظايا)
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

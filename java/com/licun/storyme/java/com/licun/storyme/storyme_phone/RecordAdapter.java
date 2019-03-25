package com.licun.storyme.storyme_phone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter {
    private final int resourceId;
    private ArrayList<File> record;

    public RecordAdapter(Context context, int recordViewResourceId, ArrayList<File> record) {
        super(context, recordViewResourceId, record);
        this.resourceId = recordViewResourceId;
        this.record = record;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Question question = (Question) getItem(position); // 获取当前项的Fruit实例
            File cur_record = record.get(position);


            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
            //ImageView imageView = (ImageView) view.findViewById(R.id.fruit_image);//获取该布局内的图片视图
            TextView textView = (TextView) view.findViewById(R.id.text);//获取该布局内的文本视图
            //imageView.setVisibility(View.INVISIBLE);

            textView.setText("Record" + position);//为文本视图设置文本内容
            textView.setVisibility(View.VISIBLE);
            return view;
        }


    }

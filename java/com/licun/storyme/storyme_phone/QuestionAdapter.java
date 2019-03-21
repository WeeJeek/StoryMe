package com.licun.storyme.storyme_phone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class QuestionAdapter extends ArrayAdapter {
    private final int resourceId;
    private File[] questions;

    public QuestionAdapter(Context context, int textViewResourceId, File[] questions) {
        super(context, textViewResourceId, questions);
        resourceId = textViewResourceId;
        this.questions = questions;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Question question = (Question) getItem(position); // 获取当前项的Fruit实例
        File cur_question = questions[position];

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(cur_question));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView imageView = (ImageView) view.findViewById(R.id.fruit_image);//获取该布局内的图片视图
        TextView textView = (TextView) view.findViewById(R.id.fruit_name);//获取该布局内的文本视图
        imageView.setVisibility(View.INVISIBLE);
        //Record record_if_exist = PublicVariables.mFirebaseManager.check_question_has_record(PublicVariables.mQuestions.get(position));
        //if( record_if_exist != null){
        //    imageView.setVisibility(View.VISIBLE);
            //try {
                //PublicVariables.mFirebaseManager.download_audio(getContext(), record_if_exist);
            //} catch (IOException e) {
                //e.printStackTrace();
            //}
        //}else{
            //playView.setLayoutParams(new ViewGroup.LinearLayoutParams(Math.round(imageView.getX()+320-playView.getWidth()),
            //                                                    Math.round(imageView.getY()+320-playView.getHeight())));
        //}

        textView.setText(text);//为文本视图设置文本内容
        return view;
    }
}
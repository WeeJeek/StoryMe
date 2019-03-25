package com.licun.storyme.storyme_phone;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCache {

    private View baseView;
    private TextView textView;
    private ImageView imageView;
    private ImageView imageView2;

    public ViewCache(View baseView) {
        this.baseView = baseView;
    }

    public TextView getTextView() {
        if (textView == null) {
            textView = (TextView) baseView.findViewById(R.id.text);
        }
        return textView;
    }

    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.image);
        }
        return imageView;
    }

    public ImageView getImageView2() {
        if (imageView2 == null) {
            imageView2 = (ImageView) baseView.findViewById(R.id.iv_play);
        }
        return imageView2;
    }


}


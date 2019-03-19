package com.licun.storyme.storyme_phone;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<File> uris;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<File> photos;
    private LayoutInflater layoutInflater;
    private View view;
    private File image_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    public PhotoAdapter(Context c) {
        mContext = c;
        uris = new ArrayList<File>();
        photos = new ArrayList<File>();
        bitmaps = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return uris.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        photos = new ArrayList<File>(Arrays.asList(image_path.listFiles()));
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            view = new View(mContext);
            view = layoutInflater.inflate(R.layout.item_grid, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            ImageView playView = (ImageView)view.findViewById(R.id.iv_play);
            TextView textView = (TextView)view.findViewById(R.id.tv_title);
            if(!photos.isEmpty()){
                playView.setVisibility(View.INVISIBLE);
                playView.bringToFront();
            }else{
                //playView.setLayoutParams(new ViewGroup.LinearLayoutParams(Math.round(imageView.getX()+320-playView.getWidth()),
                //                                                    Math.round(imageView.getY()+320-playView.getHeight())));
            }

            view.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setPadding(8, 8, 8, 8);
            File current_image = uris.get(position);
            Picasso.get().load(current_image).resize(400,400).into(imageView);
        }
        return view;
    }

    public void set_resource_images(File[] photo_list, Context context){
        for (File photo: photo_list) {
            this.uris.add(photo);
        }
    }
}
package com.licun.storyme.storyme_phone;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Toolbox {

    public void writetext(String data, String name, File path)
    {
        final File towrite = new File(path, name + ".txt");
        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            towrite.createNewFile();
            FileOutputStream fOut = new FileOutputStream(towrite);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writeimage(Bitmap bitmap, String name, File path)
    {
        final File towrite = new File(path, name + ".jpg");
        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            towrite.createNewFile();
            FileOutputStream fOut = new FileOutputStream(towrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public HashMap get_map(File[] keys, File[] records){
        HashMap map = new HashMap();
        for (File key : keys) {
            ArrayList<File> values = new ArrayList<File>();
            String key_realname = key.getName();
            key_realname = key_realname.replace(".jpg", "");
            key_realname = key_realname.replace(".txt", "");
            for (File record : records) {
                String record_name = record.getName();
                if (record_name.contains(key_realname)) {
                    values.add(record);
                }
                map.put(key, values);
            }
        }
        return map;
    }



}

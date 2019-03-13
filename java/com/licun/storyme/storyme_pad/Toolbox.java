package com.licun.storyme.storyme_pad;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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




}

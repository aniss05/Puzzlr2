package tools;

import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by aniss on 09/03/16.
 */
public class Encoding {


    public String encodeImage(byte[] imageByteArray){
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }


    public byte[] decodeImage(String imageDataString){

        return Base64.decode(imageDataString, Base64.DEFAULT);

    }




    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    /*public byte[] hexStringToByteArray(String hex){
        byte[] b = new byte[hex.length() / 2];
        for(int i = 0; i < b.length; i++){
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }

        return b;
    }*/
}

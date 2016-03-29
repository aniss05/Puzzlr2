package tools;

import android.util.Base64;

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



    public String bytesToHex(byte[] data){
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        StringBuffer buffer = new StringBuffer();

        for(int j = 0; j < data.length; j++){
            buffer.append(hexDigits[(data[j] >> 4) & 0x0f]);
            buffer.append(hexDigits[(data[j] & 0x0f)]);


        }

        return buffer.toString();
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

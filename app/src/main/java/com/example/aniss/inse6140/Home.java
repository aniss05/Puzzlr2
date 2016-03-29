package com.example.aniss.inse6140;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.SecretKeySpec;

import BlockChain.DataBlockchain;
import security.Asymmetric;
import security.Symmetric;
import tools.Encoding;


public class Home extends Activity {

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect;
    Button btnConfirm;
    ImageView ivImage;
    Bitmap image;
    BitmapDrawable drawable;
    Bundle bundle;
    String publicKeyofUser;
    String username;
    String myusername;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnConfirm = (Button) findViewById(R.id.btnConfirm);


        final Encoding encoding = new Encoding();

        bundle = getIntent().getExtras();

        publicKeyofUser = bundle.getString("Public key of user");
        username = bundle.getString("Username");
        myusername = bundle.getString("My username");


        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    if (drawable == null) {


                        Toast.makeText(getApplicationContext(), "No picture selected, please select one.", Toast.LENGTH_LONG).show();


                    } else {

                        try{


                            image = drawable.getBitmap();

                            Symmetric symmetric = new Symmetric();

                            SecretKeySpec aesKey = symmetric.generateAESKey();

                            byte[] iv = symmetric.generateIV();


                            String ivHex = encoding.encodeImage(iv);

                            int width = image.getWidth();

                            int height = image.getHeight();

                            Bitmap.Config configBmp = Bitmap.Config.valueOf(image.getConfig().name());




                            byte[] imageByteArray = bitmapToByteArray(image, width, height);

                            byte[] encryptedByteArrayImage = symmetric.encrypt(aesKey, iv, imageByteArray);

                            String encryptedImageHex = encoding.encodeImage(encryptedByteArrayImage);


                            byte[] publicKeyofUserByteArray = encoding.decodeImage(publicKeyofUser);

                            PublicKey publicKeyOfUser = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyofUserByteArray));


                            Asymmetric asymmetric = new Asymmetric();




                            SecretKeySpec macKey = symmetric.generateMacKey();







                            byte[] tag = symmetric.computeMac(encryptedByteArrayImage, macKey, iv);

                            String tagHex = encoding.encodeImage(tag);




                            byte[] usernameByteArray = username.getBytes();



                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(aesKey.getEncoded().length + macKey.getEncoded().length + usernameByteArray.length);

                            byteArrayOutputStream.write(aesKey.getEncoded());
                            byteArrayOutputStream.write(macKey.getEncoded());
                            byteArrayOutputStream.write(usernameByteArray);

                            byte[] rsaPlaintext = byteArrayOutputStream.toByteArray();

                            byte[] rsaCipherText = asymmetric.rsaEncryptKey(rsaPlaintext, publicKeyOfUser);


                            String message = "RSA Ciphertext:" + encoding.encodeImage(rsaCipherText) +"RSA Ciphertext," + "IV:" + ivHex + "IV," + "AES Ciphertext:" + encryptedImageHex + "AES Ciphertext," + "Tag:" + tagHex +"Tag";

                            DataBlockchain dataBlockchain = new DataBlockchain("132.205.23.211", 3000);

























                        }catch(Exception ex){


                            ex.printStackTrace();

                        }







                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
        drawable = (BitmapDrawable)ivImage.getDrawable();



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()){
            case R.id.contactsItem:
                Intent intent1 = new Intent(Home.this, null);
                startActivity(intent1);
        }

        return true;

    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);

    }



    private byte[] bitmapToByteArray(Bitmap image, int width, int height){


        int size = image.getRowBytes() * image.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        image.copyPixelsToBuffer(byteBuffer);
        return byteBuffer.array();
    }


    private void byteArrayToBitmap(byte[] byteArrayImage, Bitmap.Config configBmp, int width, int height){
        Bitmap image = Bitmap.createBitmap(width, height, configBmp);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayImage);
        image.copyPixelsFromBuffer(buffer);

    }







}
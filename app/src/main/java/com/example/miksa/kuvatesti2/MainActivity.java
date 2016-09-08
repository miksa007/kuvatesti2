package com.example.miksa.kuvatesti2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Idea varastettu lahteesta
 * http://www.tutorialspoint.com/android/android_camera.htm
 */
public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    ImageView iv;
    File tempFile;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        iv=(ImageView)findViewById(R.id.imageView);

        //tiedoston nimeeminen
        try {
            tempFile = File.createTempFile("my_app", ".jpg");
            String fileName = tempFile.getAbsolutePath();
            uri = Uri.fromFile(tempFile);
        }catch (IOException e){
            Log.e("tiedosto", "virhe tuli");
        }

        //Nappulalle toiminnallisuus
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //tiedostonnimi talteen
                //seuraava rivi Kaata kameran
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, 0);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("tietoo: ", (" "+ requestCode+"  "+resultCode+"  "+ data.getExtras())); //data.getExtras().get(EXTERNAL_CONTENT_URI));

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        tallennus("testi", bp);
        iv.setImageBitmap(bp);
    }
    //yrittaa tallentaa ja annettu permission to write
    public void tallennus(String nimi, Bitmap bmp){
    Log.i("tallennus", "Tallennus alkoi");
        String path = Environment.getExternalStorageDirectory().toString();
        FileOutputStream out = null;
        try {
            File file = new File(path, "FitnessGirl.jpg");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("tallennus", "Tallennus loppui");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ei loyda menu :a kayttoliittymasta
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //action_settings uupuuu
        /*{
        if(id==R.id.action_settings){
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}

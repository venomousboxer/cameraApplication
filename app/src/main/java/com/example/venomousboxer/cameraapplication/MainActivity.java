package com.example.venomousboxer.cameraapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Build.VERSION_CODES.M;
import static com.example.venomousboxer.cameraapplication.R.attr.alpha;

public class MainActivity extends AppCompatActivity {

    ImageView document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        document = (ImageView) findViewById(R.id.imageView);

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;


    public void dispatchTakeImageIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {           //we protect the function startActivityForResult
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);         //so that if no app can handle our intent our app
        }                                                                            //does not crashes
    }

    private Context context;

    int color = ContextCompat.getColor(context,R.color.colorAccent);

    public static Bitmap mark(Bitmap src, String watermark,Point location, int color, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }

    Point location = new Point(0,0);

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = mark(imageBitmap, "www.collegeSpace.com", location , color, 100, 300, true);
            document.setImageBitmap(imageBitmap);
        }
    }

    public void capture(View view) {
        dispatchTakeImageIntent();
    }

}

package com.example.tetianapriadko.people;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tetianapriadko.people.application.App;
import com.isseiaoki.simplecropview.CropImageView;


public class ActCrop extends AppCompatActivity {

    private static final float ASPECT_RATIO = 9f / 16f;
    public static final int MAX_BITMAP_WIDTH = 1080;

    private CropImageView cropView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_act_crop);

        Uri selectedImage = getIntent().getParcelableExtra("Path");
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(
                selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        cropView = ((CropImageView) findViewById(R.id.cropImageView));
        cropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
        cropView.setImageBitmap(BitmapFactory.decodeFile(filePath));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_crop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                Bitmap croppedBitmap = cropView.getCroppedBitmap();
                if (croppedBitmap.getWidth() > MAX_BITMAP_WIDTH) {
                    croppedBitmap = Bitmap.createScaledBitmap(croppedBitmap,
                            MAX_BITMAP_WIDTH,
                            (int) (MAX_BITMAP_WIDTH * ASPECT_RATIO),
                            false);
                }
                App.setCroppedBitmap(croppedBitmap);
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
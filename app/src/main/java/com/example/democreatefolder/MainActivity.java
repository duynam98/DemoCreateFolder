package com.example.democreatefolder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean isCreateFolder;
    private AddImage addImage;
    private Button btn_addImage;
    private ConstraintLayout constraintLayout;
    private Matrix matrix = new Matrix();
    private ConstraintLayout parent;
    private ImageView imgContainer;
    private Button btnAddImage;
    private float degree = 1;
    private SeekBar progress;
    private HorizontalProgressWheelView progress1;
    private TextView tvDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initPermission();
        btn_addImage = findViewById(R.id.btn_addImage);
        constraintLayout = findViewById(R.id.parent);

        initView();

        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddImage addImage = new AddImage(MainActivity.this);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.youtube);
//                addImage.setBitmap(bitmap);
//                ConstraintLayout.LayoutParams layoutParam = new ConstraintLayout.LayoutParams(bitmap.getWidth(),
//                        bitmap.getHeight());
//                constraintLayout.addView(addImage, layoutParam);
//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainActivity.this);
                degree = degree + 1;
                rotateBitmap(degree);
            }
        });

        //progress.setProgress(50);
        //progress.setMax(100);

//        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        progress1.setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            @Override
            public void onScrollStart() {

            }

            @Override
            public void onScroll(float delta, float totalDistance) {
                Log.e("delta/42", "onScroll: " + String.format(Locale.getDefault(), "%.1f°", delta / 42));
                tvDegree.setText(String.format(Locale.getDefault(), "%.1f°", delta/42));
            }

            @Override
            public void onScrollEnd() {

            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                createFolder();
            } else if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createFolder();
            } else {
                initPermission();
            }
        }
    }

    private void createFolder() {
        String newFile = Environment.getExternalStorageDirectory().toString() + File.separator;
        File folder = new File(newFile, "Nam");
        if (!folder.exists()) {
            isCreateFolder = folder.mkdirs();
        }
        if (isCreateFolder) {
            Toast.makeText(this, "Tạo folder thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lại đéo tạo đc folder", Toast.LENGTH_SHORT).show();
        }
    }

    private void rotateBitmap(float degree) {
        imgContainer.setRotation(degree);
    }

    private void initView() {
        parent = (ConstraintLayout) findViewById(R.id.parent);
        imgContainer = (ImageView) findViewById(R.id.img_container);
        btnAddImage = (Button) findViewById(R.id.btn_addImage);
        progress = (SeekBar) findViewById(R.id.progress);
        progress1 = (HorizontalProgressWheelView) findViewById(R.id.progress1);
        tvDegree = (TextView) findViewById(R.id.tv_degree);
    }
}
package com.example.mydoctor2.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mydoctor2.BuildConfig;
import com.example.mydoctor2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int GALLERY_PICTURE = 1;
    public static final int REQUEST_PERMISSIONS = 1;
    private static final int CAPTURE_PHOTO = 2;
    private static final String IMAGE_CAPTURE_FOLDER = "scanner";
    File file;
    private static Uri _imagefileUri;
    Button btn_select, btn_convert;
    ImageView iv_image;
    boolean boolean_permission;
    boolean boolean_save = false;
    Bitmap bitmap;
    String _imageFileName;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        init();
        listener();
        fn_permission();
    }

    private void init() {
        btn_select = findViewById(R.id.btn_select);
        btn_convert = findViewById(R.id.btn_convert);
        iv_image = findViewById(R.id.iv_image);
    }

    private void listener() {
        btn_select.setOnClickListener(this);
        btn_convert.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                new MaterialDialog.Builder(this)
                        .title("Select image type")
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, GALLERY_PICTURE);
                                        break;
                                    case 1:
                                        genRandom();
                                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        _imagefileUri = FileProvider.getUriForFile(ScanActivity.this, BuildConfig.APPLICATION_ID + ".provider", getFile());

                                        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, _imagefileUri);
                                        startActivityForResult(intent2, CAPTURE_PHOTO);
                                        break;
                                }
                            }
                        })
                        .show();
                break;

            case R.id.btn_convert:
                if (boolean_save) {
                    Intent intent1 = new Intent(getApplicationContext(), PDFViewActivity.class);
                    intent1.putExtra("fileName", ""+fileName+".pdf");
                    startActivity(intent1);
                } else {
                    createPdf();
                }
                break;
        }
    }

    private void createPdf() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);


        // write the document content
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PDF Folder");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        fileName = "picture5";
        File file = new File(filePath, fileName + ".pdf");
        try {
            document.writeTo(new FileOutputStream(file));
            btn_convert.setText("Deschide PDF");
            boolean_save = true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e, Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICTURE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();


                bitmap = BitmapFactory.decodeFile(filePath);
                iv_image.setImageBitmap(bitmap);


                btn_convert.setClickable(true);
            }
        } else if (requestCode == CAPTURE_PHOTO) {
            if (resultCode == RESULT_OK) {

                bitmap = BitmapFactory.decodeFile(_imagefileUri.getPath());
                iv_image.setImageBitmap(bitmap);

                btn_convert.setClickable(true);

            }
        }
    }


    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

            if (!(ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
                ActivityCompat.requestPermissions(ScanActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }

            if (!(ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }

            if (!(ActivityCompat.shouldShowRequestPermissionRationale(ScanActivity.this, Manifest.permission.CAMERA))) {
                ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
            }
        }
    }

    private File getFile() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        file = new File(filepath, IMAGE_CAPTURE_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }

        return new File(file + File.separator + _imageFileName
                + ".jpg");
    }

    public void genRandom() {
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        final int N = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        _imageFileName = sb.toString();
    }

}

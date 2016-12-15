package jp.techacademy.takafumi.matsushima.slideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    //private int counter = 1;
    private int counter = 0;
    private int cnt = 0;//MAX COUNTER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
             //   getContentsInfo();
                getNextContentsInfo(1);

            } else {
                // 許可されていないので許可ダイアログ
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
         //   getContentsInfo();
            getNextContentsInfo(1);

        }
    }
    public void BT01(View v){
        int bt01counter = counter;
        bt01counter++;
        getNextContentsInfo(bt01counter);


    }

    public void BT02(View v){
        int bt02counter = counter;
        if( bt02counter == 1 ){
            bt02counter = cnt;
        }else if (bt02counter == 0) {
            bt02counter = cnt-1;
        } else {

            bt02counter--;
        }


        getBackContentsInfo(bt02counter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
     //               getContentsInfo();
                    getNextContentsInfo(1);
                }
                break;
            default:
                break;
        }
    }

     private void getNextContentsInfo(int i) {
        int number= 0;

        // 画像の情報を取得する
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
                null, // 項目(null = 全項目)
                null, // フィルタ条件(null = フィルタなし)
                null, // フィルタ用パラメータ
                null // ソート (null ソートなし)
        );
        cnt = cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                number++;
                if(i <= number){
                    int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    Long id = cursor.getLong(fieldIndex);
                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
                    imageVIew.setImageURI(imageUri);
                    counter = i;
                    break;

                 }
             } while (cursor.moveToNext());
            if (cnt == number){
                counter = 0;
            }


        }

        cursor.close();
    }

    private void getBackContentsInfo(int i) {
        int number= cnt;

        // 画像の情報を取得する
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
                null, // 項目(null = 全項目)
                null, // フィルタ条件(null = フィルタなし)
                null, // フィルタ用パラメータ
                null // ソート (null ソートなし)
        );
 //
        if (cursor.moveToLast()) {
            do {
          //      number--;
                if(i == number){
                    int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    Long id = cursor.getLong(fieldIndex);
                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
                    imageVIew.setImageURI(imageUri);
                    counter = i;
                    break;

                }
                number--;
            } while (cursor.moveToPrevious());
            if (cnt == number){
                counter = 0;
            }


        }

        cursor.close();
    }
}

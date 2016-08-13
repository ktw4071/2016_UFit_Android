package kr.co.team.LKLH.ufit;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.util.Log.e;

public class UFitImageUploadHelper extends AppCompatActivity {
    private static final String TAG = "UFitImageUploadHelper";
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private ImageView mPhotoImageView;
    private ArrayList<UpLoadValueObject> upLoadfiles = new ArrayList<>();

    class UpLoadValueObject {
        File file; //업로드할 파일
        boolean tempFiles; //임시파일 유무

        public UpLoadValueObject(File file, boolean tempFiles) {
            this.file = file;
            this.tempFiles = tempFiles;
        }
    }

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setLayout(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT);



        AlertDialog dialog = null;
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
        dialog = new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("앨범선택", albumListener)
                .setNegativeButton("사진촬영", cameraListener)
                .setNeutralButton("취소", cancelListener).create();

        dialog.show();


    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isSDCardAvailable()) {
            Toast.makeText(this, "SD 카드가 없어 종료 합니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String currentAppPackage = getPackageName();

        myImageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), currentAppPackage);

        checkPermission();

        if (!myImageDir.exists()) {
            if (myImageDir.mkdirs()) {
                Toast.makeText(getApplication(), " 저장할 디렉토리가 생성 됨", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);

            } else {
                //사용자가 언제나 허락
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    //사용자가 퍼미션을 OK했을 경우

                } else {

                    Log.d(TAG, "Permission always deny");

                    //사용자가 퍼미션을 거절했을 경우
                }
                break;
        }
    }

    public boolean isSDCardAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 카메라에서 이미지 가져오기
     */
    Uri currentSelectedUri; //업로드할 현재 이미지에 대한 Uri
    File myImageDir; //카메라로 찍은 사진을 저장할 디렉토리
    String currentFileName;  //파일이름

    private void doTakePhotoAction() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //업로드할 파일의 이름
        currentFileName = "upload_" + String.valueOf(System.currentTimeMillis() / 1000) + ".jpg";
        currentSelectedUri = Uri.fromFile(new File(myImageDir, currentFileName));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentSelectedUri);
        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (resultCode != RESULT_OK) {
            return;
        }*/

        Intent mIntent = getIntent();
        switch (requestCode) {
            /*case CROP_FROM_CAMERA: {

                // 크롭된 이미지를 세팅
                final Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    mPhotoImageView.setImageBitmap(photo);
                }
                (new AsyncTPImage()).execute();
                break;
            }*/
            case PICK_FROM_ALBUM: {

                currentSelectedUri = data.getData();
                if (currentSelectedUri != null) {
                    //실제 Image의 full path name을 얻어온다.
                    if (findImageFileNameFromUri(currentSelectedUri)) {
                        //ArrayList에 업로드할  객체를 추가한다.
                        upLoadfiles.add(new UpLoadValueObject(new File(currentFileName), false));
                    }
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap returedBitmap = (Bitmap) extras.get("data");
                    if (tempSavedBitmapFile(returedBitmap)) {
                        Log.e("임시이미지파일저장", "저장됨");
                    } else {
                        Log.e("임시이미지파일저장", "실패");
                    }
                }
//                cropIntent(currentSelectedUri);
                (new AsyncTPImage()).execute(upLoadfiles);
                break;
            }

            case PICK_FROM_CAMERA: {

                //카메라캡쳐를 이용해 가져온 이미지
                upLoadfiles.add(new UpLoadValueObject(new File(myImageDir, currentFileName), false));
//                cropIntent(currentSelectedUri);
                break;
            }
        }
    }
    /*private  void  cropIntent(Uri cropUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(cropUri, "image*//*");

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_FROM_CAMERA);
    }*/
    private boolean tempSavedBitmapFile(Bitmap tempBitmap) {
        boolean flag = false;
        try {
            currentFileName = "upload_" + (System.currentTimeMillis() / 1000);
            String fileSuffix = ".jpg";
            //임시파일을 실행한다.
            File tempFile = File.createTempFile(
                    currentFileName,            // prefix
                    fileSuffix,                   // suffix
                    myImageDir                   // directory
            );
            final FileOutputStream bitmapStream = new FileOutputStream(tempFile);
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 0, bitmapStream);
            upLoadfiles.add(new UpLoadValueObject(tempFile, true));
            if (bitmapStream != null) {
                bitmapStream.close();
            }
            currentSelectedUri = Uri.fromFile(tempFile);
            flag = true;
        } catch (IOException i) {
            Log.e("저장중 문제발생", i.toString(), i);
        }
        return flag;
    }

    private boolean findImageFileNameFromUri(Uri tempUri) {
        boolean flag = false;

        //실제 Image Uri의 절대이름
        String[] IMAGE_DB_COLUMN = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = null;
        try {
            //Primary Key값을 추출
            String imagePK = String.valueOf(ContentUris.parseId(tempUri));
            //Image DB에 쿼리를 날린다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_DB_COLUMN,
                        MediaStore.Images.Media._ID + "=?",
                        new String[]{imagePK}, null, null);
            }
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                currentFileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                Log.e("fileName", String.valueOf(currentFileName));
                flag = true;
            }
        } catch (SQLiteException sqle) {
            Log.e("findImage....", sqle.toString(), sqle);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return flag;
    }

    private class AsyncTPImage extends AsyncTask<ArrayList<UFitImageUploadHelper.UpLoadValueObject>,Void,String> {
        @Override
        protected String doInBackground(ArrayList<UFitImageUploadHelper.UpLoadValueObject>... arrayLists) {
            Intent asdintent = getIntent();
            Log.e(TAG, asdintent.getStringExtra("url"));
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_MPFD_JSON
                    (asdintent.getIntExtra("code",1), asdintent.getStringExtra("url"), arrayLists);
        }


    @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                Toast.makeText(UFitImageUploadHelper.this, "파일업로드에 성공했습니다", Toast.LENGTH_LONG).show();
                int  fileSize = upLoadfiles.size();

                for(int i = 0 ; i < fileSize ; i++){
                    UpLoadValueObject fileValue = upLoadfiles.get(i);
                    if(fileValue.tempFiles){
                        fileValue.file.deleteOnExit(); //임시파일을 삭제한다
                    }
                }
            }else{
                Toast.makeText(UFitImageUploadHelper.this, "파일업로드에 실패했습니다", Toast.LENGTH_LONG).show();
            }
        finish();
        startActivity(new Intent(UFitImageUploadHelper.this, UFitTranerProfileActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
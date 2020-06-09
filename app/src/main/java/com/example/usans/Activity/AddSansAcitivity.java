package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.usans.Data.FacilityList;
import com.example.usans.GpsTracker;
import com.example.usans.JSONParser;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.skt.Tmap.TMapPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddSansAcitivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView imageView;
    private Button addImageButton;
    private TextView sansAddress;
    EditText sansName;
    TextView sansMachines;
    EditText sansAddMachine;
    Button sansAddMachineButton;
    RatingBar sansRatingBar;
    EditText editText;
    private Button saveButton;

    private FacilityList facilityList;
    private TMapPoint centerPoint;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sans);
        Intent intent = getIntent();
        centerPoint = new TMapPoint((Double)intent.getExtras().get("lat"),(Double)intent.getExtras().get("lng"));

        facilityList = (FacilityList) getApplication();
        addImageButton = findViewById(R.id.button3);
        imageView = findViewById(R.id.sans_image_view);
        sansAddress = findViewById(R.id.sans_address);

        sansName = findViewById(R.id.sans_name);
        sansMachines = findViewById(R.id.sans_machines);
        sansAddMachine = findViewById(R.id.sans_add_machine);
        sansAddMachineButton = findViewById(R.id.sans_add_machine_button);
        sansRatingBar = findViewById(R.id.sans_ratingBar);
        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.save_button);

        String address = getCurrentAddress(centerPoint.getLatitude() , centerPoint.getLongitude());
        address = address.substring(5,address.length()-1);
        sansAddress.setText(address);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGallery();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGallery();
            }
        });
        sansAddMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sansMachines.append(sansAddMachine.getText()+" ");
                sansAddMachine.setText("");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ImageUploadURL = "http://192.168.0.100/upload/upload.php";
                new ImageUploadTask().execute(ImageUploadURL, imagePath);
            }
        });
    }

    private void getGallery() {
        // File System.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
        startActivityForResult(chooserIntent, PICK_FROM_ALBUM);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                addImageButton.setVisibility(View.INVISIBLE);
                mImageCaptureUri = data.getData();
                imageView.setImageURI(mImageCaptureUri);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(mImageCaptureUri, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    imagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));

                }
            }
        }
    }

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREAN);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    public boolean save() {
        String contents = editText.getText().toString();
        if (contents.length() == 0) {
            setResult(200, null);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", sansName.getText().toString());
            contentValues.put("address", sansAddress.getText().toString());
            contentValues.put("lat", centerPoint.getLatitude());
            contentValues.put("lon", centerPoint.getLongitude());
            contentValues.put("rating", sansRatingBar.getRating());
            String sansuzang = "http://3.34.18.171.nip.io:8000/ssz/write/?name="+sansName.getText().toString()+"&address="+sansAddress.getText().toString()+"&lat="+centerPoint.getLatitude()+"&lon="+centerPoint.getLongitude();
            NetworkTask networkTask = new NetworkTask(sansuzang, contentValues);
            networkTask.execute();

            //코맨트 추가
//            String url = "http://3.34.18.171.nip.io:8000/review/?user="+facilityList.getUser().getId()+"&loc="+facilityID+"&rating="+Math.round(sansRatingBar.getRating())+"&text="+contents;
//            networkTask = new NetworkTask(url, null);
//            networkTask.execute();

            setResult(10101, null);
        }
        finish();
        return false;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            return requestHttpURLConnection.request(url,values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private  class ImageUploadTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog progressDialog; // API 26에서 deprecated

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddSansAcitivity.this);
            progressDialog.setMessage("이미지 업로드중....");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                JSONObject jsonObject = JSONParser.uploadImage(params[0],params[1],facilityList);
                if (jsonObject != null){
                    return jsonObject.getString("result").equals("success");
                }
            } catch (JSONException e) {
                Log.i("TAG", "Error : " + e.getLocalizedMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog != null)
                progressDialog.dismiss();

            if (aBoolean){
                Toast.makeText(getApplicationContext(), "파일 업로드 성공", Toast.LENGTH_LONG).show();
            }  else{
                Toast.makeText(getApplicationContext(), "파일 업로드 실패", Toast.LENGTH_LONG).show();
            }

            if(mImageCaptureUri != null){
                File file = new File(mImageCaptureUri.getPath());
                if(file.exists()) {
                    file.delete();
                }
                mImageCaptureUri = null;
            }
            imagePath = "";
        }
    }

}

package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usans.AppHelper;
import com.example.usans.Data.FacilityList;
import com.example.usans.GpsTracker;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

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
    private Button gpsToAddress;
    private GpsTracker gpsTracker;
    private Geocoder geocoder;
    private TextView sansAddress;
    EditText sansName;
    TextView sansMachines;
    EditText sansAddMachine;
    Button sansAddMachineButton;
    RatingBar sansRatingBar;
    EditText editText;
    private Button saveButton;

    private FacilityList facilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sans);

        facilityList = (FacilityList) getApplication();
        addImageButton = findViewById(R.id.button3);
        imageView = findViewById(R.id.sans_image_view);
        gpsToAddress = findViewById(R.id.gps_to_address);
        sansAddress = findViewById(R.id.sans_address);
        geocoder = new Geocoder(this);
        sansName = findViewById(R.id.sans_name);
        sansMachines = findViewById(R.id.sans_machines);
        sansAddMachine = findViewById(R.id.sans_add_machine);
        sansAddMachineButton = findViewById(R.id.sans_add_machine_button);
        sansRatingBar = findViewById(R.id.sans_ratingBar);
        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.save_button);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });
        gpsToAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getApplicationContext());
                String address = getCurrentAddress(gpsTracker.getLatitude() , gpsTracker.getLongitude());
                Log.e("address다 이씨발", address);
                sansAddress.setText(address.substring(5));
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
                save();
            }
        });
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
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
            contentValues.put("lat", gpsTracker.getLatitude());
            contentValues.put("lon", gpsTracker.getLongitude());
            contentValues.put("rating", sansRatingBar.getRating());
            NetworkTask networkTask = new NetworkTask(AppHelper.Sansuzang, contentValues);
            networkTask.execute();

//            String url = "http://3.34.18.171.nip.io:8000/review/?user="+facilityList.getUser().getId()+"&loc="+facilityID+"&rating="+Math.round(sansRatingBar.getRating())+"&text="+contents;
//            networkTask = new NetworkTask(url, null);
//            networkTask.execute();

            setResult(101, null);
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
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}

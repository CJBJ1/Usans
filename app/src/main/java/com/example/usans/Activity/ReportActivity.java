package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.volobot.stringchooser.StringChooser;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    Button saveButton, cancelButton;
    EditText contentsInput;
    String userID;
    String facilityID;
    String picked = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        List<String> strings = new ArrayList<>();

        Intent intent = getIntent();
        String machinesString = intent.getStringExtra("machines");
        for (String machine : machinesString.split(" "))
            strings.add(machine);

        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        contentsInput = findViewById(R.id.contents_input);

        userID = intent.getStringExtra("userID");
        facilityID = intent.getStringExtra("facilityID");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save()) onBackPressed();
                else Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        StringChooser stringChooser = findViewById(R.id.stringChooser);
        stringChooser.setStrings(strings);
        stringChooser.setStringChooserCallback(new StringChooser.StringChooserCallback() {
            @Override
            public void onStringPickerValueChange(String s, int position) {
                picked = s;
                Log.d("TAG", picked);
            }
        });

    }

    public boolean save() {
        String contents = contentsInput.getText().toString();
        Intent intent = new Intent();
        if (contents.length() == 0) {
            setResult(200, intent);
            finish();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("user", userID);
            contentValues.put("loc", facilityID);
            contentValues.put("rating", -1);
            contentValues.put("text", contents);
            contentValues.put("mach", "testmachine");
            contentValues.put("stat", picked);

            String url = "http://3.34.18.171.nip.io:8000/review/?user="+userID+"&loc="+facilityID+"&rating=-1&text="+contents+"&mach=testmachine"+
                    "&stat="+picked;
            NetworkTask networkTask = new NetworkTask(url, null);
            networkTask.execute();
            setResult(101, intent);
            finish();
            return true;
        }
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
            Log.d("POST","POST");
        }
    }

}

package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usans.R;
import com.volobot.stringchooser.StringChooser;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    Button saveButton, cancelButton;
    EditText contentsInput;

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

        if (contents.length() == 0) {
            return false;
        } else {
            //writeCommentActivity에 contents 넘겨줘야 함
            return true;
        }
    }

}

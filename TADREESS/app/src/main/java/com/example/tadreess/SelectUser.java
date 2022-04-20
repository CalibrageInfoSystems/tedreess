package com.example.tadreess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tadreess.Student.StudentRegisterActivity;

public class SelectUser extends AppCompatActivity {


    TextView txtTeacher,txtStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        txtTeacher = findViewById(R.id.txtTeacher);
        txtStudent = findViewById(R.id.txtStudent);

        txtTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectUser.this, TeacherRegisterActivity.class);
                startActivity(intent);
            }
        });

        txtStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUser.this, StudentRegisterActivity.class));
            }
        });

    }
}

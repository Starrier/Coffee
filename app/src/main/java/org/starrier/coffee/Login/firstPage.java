package org.starrier.coffee.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.starrier.coffee.R;

public class firstPage extends AppCompatActivity {
    private TextView userName;
    private TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);
        userName=(TextView)findViewById(R.id.userNameShow);
        password=(TextView)findViewById(R.id.pwdShow);
        Intent intent=getIntent();
        userName.setText("用户名："+intent.getStringExtra("userName"));
        password.setText("密  码："+intent.getStringExtra("password"));

    }
}


package org.starrier.coffee.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.starrier.coffee.MainActivity;
import org.starrier.coffee.R;
import org.starrier.coffee.fragment.*;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity  extends Activity implements View.OnClickListener{

    private Button loadButton;
    private Button registerButton;
    private EditText userName;
    private EditText password;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase database;
    private String qurySql="select * from userInfo";
    private String userName1;
    private String password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText3);
        loadButton = (Button) findViewById(R.id.button);
        registerButton = (Button) findViewById(R.id.button2);
        loadButton.setTag(1);
        loadButton.setOnClickListener(this);
        registerButton.setTag(2);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 1:
                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                intent1.putExtra("id",1);
                intent1.putExtra("userName",userName.getText().toString());
                intent1.putExtra("password",password.getText().toString());
                if (idCorrect()){
                    startActivity(intent1);
                }
                break;

            case 2:
                Intent intent=new Intent(this,registerActivity.class);
                startActivity(intent);
                break;
        }

    }

    public boolean idCorrect(){
        myOpenHelper=new MyOpenHelper(this,"mydb.db",null,1);
        database=myOpenHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(qurySql,null);
        while(cursor.moveToNext()){
            userName1=cursor.getString(cursor.getColumnIndex("userId"));
            password1=cursor.getString(cursor.getColumnIndex("password"));
            if (userName1.equals(userName.getText().toString())&&password1.equals(password.getText().toString())){
                return true;
            }
        }
        Toast.makeText(this,"账号或密码错误，请重新输入",Toast.LENGTH_LONG).show();
        return false;

    }

}


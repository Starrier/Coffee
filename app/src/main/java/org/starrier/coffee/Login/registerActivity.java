package org.starrier.coffee.Login;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.starrier.coffee.R;

public class registerActivity extends Activity implements View.OnClickListener{
    private Button register;
    private Button back;
    private String userName;
    private String password;
    private MyOpenHelper myOpenHelper;
    public static final String createTableSql="create table if not exists userInfo (userId varchar,password varchar)";
    private int Tag=0;
    private SQLiteDatabase database;
    private EditText editUser;
    private EditText pwd;
    private EditText pwdAgain;
    private LinearLayout registerbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbackground = (LinearLayout) findViewById(R.id.register);
        registerbackground.getBackground().setAlpha(80);
        editUser=(EditText)findViewById(R.id.reg_userText);
        pwd=(EditText)findViewById(R.id.reg_pwdText);
        pwdAgain=(EditText)findViewById(R.id.reg_pwdagText);
        register= (Button) findViewById(R.id.button3);
        register.setTag(1);
        register.setOnClickListener(this);
        back=(Button)findViewById(R.id.button4);
        back.setTag(2);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int Tag=(int)v.getTag();
        switch (Tag){
            case 1:
                createDatabase();
                createTable();
                insertData();
                Toast.makeText(registerActivity.this,"注册成功,返回登录界面",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent();
                intent1.setClass(this, LoginActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(registerActivity.this,"注册失败，请再试一次",Toast.LENGTH_LONG).show();
                break;

        }

    }

    public void createDatabase(){
        myOpenHelper=new MyOpenHelper(this,"mydb.db",null,1);
        database=myOpenHelper.getWritableDatabase();

    }

    public void createTable(){
        createDatabase();
        database.execSQL(createTableSql);

    }

    public void insertData(){
        pwdCorrect();
        createDatabase();
        String insertSql="insert into userInfo (userId,password) values ('"+editUser.getText().toString()+"','"+pwd.getText().toString()+"');";
        database.execSQL(insertSql);
//        ContentValues cv=new ContentValues();
//        cv.put(userName,editUser.getText().toString());
//        cv.put(password,pwd.getText().toString());
//        database.insert("userInfo", null, cv);



    }

    public boolean pwdCorrect(){
        if(pwd.getText().toString().equals(pwdAgain.getText().toString())){
            return true;
        }
        Toast.makeText(registerActivity.this,"两次密码输入不一致，请重新输入。",Toast.LENGTH_LONG).show();
        return false;


    }
}

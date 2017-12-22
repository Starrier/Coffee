package org.starrier.coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.starrier.coffee.Login.LoginActivity;
import org.starrier.coffee.R;
import org.starrier.coffee.personal.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends  Fragment {

    public CircleImageView loginView;

    public boolean isFirstLogin=true;

    private TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        userName=(TextView)getView().findViewById(R.id.Users);
        loginView = (CircleImageView) getView().findViewById(R.id.head);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFirstLogin) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    isFirstLogin=false;
                }else{
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        Intent intent=getActivity().getIntent();
        if (intent.getStringExtra("userName") == null) {
            isFirstLogin=true;
        } else {
            userName.setText("用户名："+intent.getStringExtra("userName"));
        }

    }

}
package org.starrier.coffee.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import org.starrier.coffee.R;

import java.io.File;

public class LoveFragment extends  Fragment {

    private VideoView videoView;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love, null);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        videoView = (VideoView) getView().findViewById(R.id.videoView2);
        textView = (TextView) getView().findViewById(R.id.videoView2Button);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File videoView2=new File("/res/raw/guide1.mp4");
                videoView.setVideoPath(videoView2.getAbsolutePath());

            }
        });

    }
}
package org.starrier.coffee.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.starrier.coffee.ItemNewsActivity;
import org.starrier.coffee.R;

public class MapFragment extends  Fragment {

    private TextView ItemNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ItemNews = (TextView) getView().findViewById(R.id.itemNews1);
        ItemNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ItemNewsActivity.class);
                startActivity(intent);

            }
        });
    }

}
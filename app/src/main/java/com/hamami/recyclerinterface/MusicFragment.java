package com.hamami.recyclerinterface;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MusicFragment extends Fragment
{
    private OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onRssItemSelected(String link);
    }
    public static  TextView PlayOrPause;
    public static  TextView moveNext;
    public static TextView moveBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment,container,false);

        PlayOrPause =  view.findViewById(R.id.resumeOrPause);
        moveNext =  view.findViewById(R.id.moveNext);
        moveBack =  view.findViewById(R.id.moveBackword);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        TextView PlayOrPause = (TextView) view.findViewById(R.id.resumeOrPause);
//        TextView moveBackword = (TextView) view.findViewById(R.id.moveBackword);
//        TextView moveNext = (TextView) view.findViewById(R.id.moveNext);
    }
}


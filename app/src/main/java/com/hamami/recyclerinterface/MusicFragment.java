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
    private static TextView PlayOrPause;
    private static TextView moveNext;
    private static TextView moveBack;

    Fragmentlistener infoToActivity;

    public void SetFragmentlistener (Activity activity)
    {
        infoToActivity = (Fragmentlistener) activity;
    }

    public interface Fragmentlistener {
        public void ClickedNext(int currentPostion);
    }



//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//
//
//        } catch (ClassCastException e)
//        {
//            throw new ClassCastException(context.toString());
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment,container,false);
        PlayOrPause =  view.findViewById(R.id.PlayOrPause);
        moveNext =  view.findViewById(R.id.moveNext);
        moveBack =  view.findViewById(R.id.moveBackword);


        return view;
    }

}


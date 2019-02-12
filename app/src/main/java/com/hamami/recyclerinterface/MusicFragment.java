package com.hamami.recyclerinterface;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MusicFragment extends Fragment
{
    private MusicFragmentListener listener;

    // Define the events that the fragment will use to communicate
    public interface MusicFragmentListener {
        // This can be any number of events to be sent to the activity
        public void playOrPause();
        public void moveNext();
        public void moveBack();
    }
    private   TextView PlayOrPause;
    private   TextView moveNext;
    private  TextView moveBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment,container,false);

        PlayOrPause =  view.findViewById(R.id.resumeOrPause);
        moveNext =  view.findViewById(R.id.moveNext);
        moveBack =  view.findViewById(R.id.moveBackword);
        PlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playOrPause();
            }
        });
        moveNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.moveNext();
            }
        });
        moveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.moveBack();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof MusicFragmentListener)
        {
            listener = (MusicFragmentListener) context;
        }
        else
        {
            throw  new RuntimeException(context.toString() + "must implement MusicFragmentListener listner");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}


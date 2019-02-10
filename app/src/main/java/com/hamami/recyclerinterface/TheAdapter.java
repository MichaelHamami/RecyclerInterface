package com.hamami.recyclerinterface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TheAdapter extends RecyclerView.Adapter<TheAdapter.SongHolder> {

    ArrayList<Song> songsList;
    Context context;
    OnItemClickListener onItemClickListener;

    public TheAdapter(ArrayList<Song> songsList,Context context) {
        this.context = context;
        this.songsList = songsList;
    }

    public interface OnItemClickListener
    {
         void onItemClick(View v,Song s,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        public TextView textViewSongName;
        public TextView textTimeSong;
        public TextView buttonViewOption;
        LinearLayout linearLayoutOfRecycler;

        public SongHolder(View itemView) {
            super(itemView);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            textTimeSong = itemView.findViewById(R.id.textTimeSong);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
            linearLayoutOfRecycler = itemView.findViewById(R.id.linearLayoutOfRecycler);
        }
    }



    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new SongHolder(v);
    }

    @Override
    public void onBindViewHolder(SongHolder songholder, final int position) {
        final Song song = songsList.get(position);
        songholder.textViewSongName.setText(song.getNameSong());
        songholder.textTimeSong.setText(song.getSongLength());

        songholder.linearLayoutOfRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // play the music
//                Toast.makeText(context,song.getFileSong().toString(),Toast.LENGTH_LONG).show();
                onItemClickListener.onItemClick(v,song,position);
            }
        });
//        songholder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onItemClickListener != null)
//                {
//                    onItemClickListener.onItemClick(v,song,position);
//                }
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return songsList.size();
    }
}

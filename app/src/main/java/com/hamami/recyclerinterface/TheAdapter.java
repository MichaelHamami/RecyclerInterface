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
    AdapterListener adapterListener;

    public TheAdapter(ArrayList<Song> songsList,Context context) {
        this.context = context;
        this.songsList = songsList;
    }

    public interface AdapterListener
    {
         void onItemClick(View v,Song s,int position);
         void onMenuDeleteClick(Song s,int position);
    }
    public void setOnItemClickListener(AdapterListener adapterListener)
    {
        this.adapterListener = adapterListener;
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
    public void onBindViewHolder(final SongHolder songholder, final int position) {
        final Song song = songsList.get(position);
        songholder.textViewSongName.setText(song.getNameSong());
        songholder.textTimeSong.setText(song.getSongLength());

        songholder.linearLayoutOfRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // play the music
//                Toast.makeText(context,song.getFileSong().toString(),Toast.LENGTH_LONG).show();
                adapterListener.onItemClick(v,song,position);
            }
        });
        songholder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, songholder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.playMenu:
                                //handle menu1 click
                                adapterListener.onItemClick(item.getActionView(),song,position);
                                break;
                            case R.id.deleteMenu:
                                //handle menu2 click
                                adapterListener.onMenuDeleteClick(song,position);
                                break;
                            case R.id.addToPlaylist:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return songsList.size();
    }
}

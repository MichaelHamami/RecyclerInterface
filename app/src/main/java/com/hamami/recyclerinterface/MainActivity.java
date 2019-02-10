package com.hamami.recyclerinterface;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    //recyclerview objects
     RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
     TheAdapter songAdapter;
     ArrayList<File> mySongs;
     MusicFragment musicFragment;

    //Other

    private String rootis;
    private MediaPlayer mediaPlayer;

    public static int currentPosition;
    public static int resumePosition;

    ArrayList<Song> songsList = new ArrayList<>();

    // fragment objects

    TextView resumeOrPause;
    TextView moveNext;
    TextView moveBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPosition = 0;
        resumePosition =0;

//        //initializing views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get song files from Emulator
        rootis = " " + Environment.getExternalStorageDirectory().getName();
        mySongs = findSongs(Environment.getExternalStorageDirectory());

        //loading list view item with this function
        loadRecyclerViewItem();


        musicFragment = new MusicFragment();

    //        Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.fragmentContainer, musicFragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
//Complete the changes added above
       ft.commit();

        resumeOrPause = findViewById(R.id.resumeOrPause);
        moveNext = findViewById(R.id.moveNext);
        moveBack = findViewById(R.id.moveBackword);

        songAdapter.setOnItemClickListener(new TheAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, Song s, int position) {
                stopPlayer();
                playMusic(s.getFileSong());
                currentPosition = position;
            }
        });
        moveNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopPlayer();
                ClickedNext(currentPosition);
            }
        });

        moveBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopPlayer();
                ClickedBack(currentPosition);
            }
        });

        resumeOrPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying())
                {
                    resumePosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }

                else
                {
                    mediaPlayer.seekTo(resumePosition);
                    mediaPlayer.start();
                }
            }
        });

    }


    public ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findSongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    al.add(singleFile);
                }
            }
        }
        return al;
    }

    private void loadRecyclerViewItem() {
//            you can fetch the data from server or some apis
//            for this tutorial I am adding some dummy data directly
        for (int i = 0; i < mySongs.size(); i++) {
            Song song = new Song(
                    mySongs.get(i),
                    mySongs.get(i).getName(),
                    getTimeSong(mySongs.get(i))
            );
            songsList.add(song);
        }
        songAdapter = new TheAdapter(songsList,this);
        recyclerView.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();

    }

    public String getTimeSong(File file) {
        // load data file
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(file.getAbsolutePath());

        String time = "";
        // get mp3 info

        // convert duration to minute:seconds
        String duration =
                metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);

        String minutes = String.valueOf(dur / 60000);
        if (seconds.length() == 1) {
            time = "0" + minutes + ":0" + seconds;
        } else {
            time = "0" + minutes + ":" + seconds;
        }
//        Toast.makeText(this,time,Toast.LENGTH_LONG).show();
        // close object
        metaRetriever.release();
        return time;
    }
    public void playMusic(File songFile)
    {
        Toast.makeText(this, "playMusic func running in main", Toast.LENGTH_SHORT).show();
        if (mediaPlayer != null)
        {
            stopPlayer();
        }

        mediaPlayer = MediaPlayer.create(this, Uri.parse(songFile.toString()));
        Toast.makeText(this, "Media Player.create working? in main", Toast.LENGTH_SHORT).show();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                stopPlayer();
            }
        });

        mediaPlayer.start();

    }
    public void stopPlayer()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this,"MediaPlayer released in main",Toast.LENGTH_SHORT).show();
        }
    }
    public void ClickedNext(int position)
    {
        Toast.makeText(this, "ClickedNext func running", Toast.LENGTH_SHORT).show();
        if (mediaPlayer != null)
        {
            stopPlayer();
        }
        if (position >= songsList.size()-1) position = -1;
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songsList.get(position+1).getFileSong().toString()));
        currentPosition = position+1;
        Toast.makeText(this, "Media Player.create working?", Toast.LENGTH_SHORT).show();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                stopPlayer();
            }
        });

        mediaPlayer.start();
    }
    public void ClickedBack(int position)
    {
        Toast.makeText(this, "ClickedNext func running", Toast.LENGTH_SHORT).show();
        if (mediaPlayer != null)
        {
            stopPlayer();
        }
        if (position <= 0) position = 1;
        mediaPlayer = MediaPlayer.create(this, Uri.parse(songsList.get(position-1).getFileSong().toString()));
        currentPosition = position-1;
        Toast.makeText(this, "Media Player.create working?", Toast.LENGTH_SHORT).show();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                stopPlayer();
            }
        });

        mediaPlayer.start();
    }
}

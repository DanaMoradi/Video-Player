package com.example.videoplayer;

import static com.example.videoplayer.R.string.app_name;

import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.videoplayer.databinding.ActivityMainBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ForwardingPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private VideoView videoView;

    ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PlayerView view = binding.playerViewMain;

        player = new ExoPlayer.Builder(this).build();

        ForwardingPlayer forwardingPlayer = new ForwardingPlayer(player) {
            @Override
            public long getSeekBackIncrement() {
                return 10 * 1000;
            }
            @Override
            public long getSeekForwardIncrement() {
                return 10 * 1000;
            }
        };


        view.setPlayer(forwardingPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(app_name)));
        MediaItem mediaItem = MediaItem.fromUri("https://hajifirouz22.asset.aparat.com/aparat-video/d99c62532f380a8c68e08e1d51c3373952101247-480p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjgwNTE0MTIyYjRkOTY5NjFiODA3ZjM0Y2NiMTA0Yjc0IiwiZXhwIjoxNjg0MDc5OTY3LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.us2ctfNoTSD4UdxnuqdYyqxVOZ27CebHf4J__79rjeE");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        view.setPlayer(player);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


}
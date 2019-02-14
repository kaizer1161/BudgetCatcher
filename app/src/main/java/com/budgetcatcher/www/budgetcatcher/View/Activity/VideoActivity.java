package com.budgetcatcher.www.budgetcatcher.View.Activity;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.budgetcatcher.www.budgetcatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    @BindView(R.id.video_view)
    VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        ButterKnife.bind(this);

        /*webView.loadUrl("http://biniyogbondhu.com/storage/videos/reconciliation.mp4");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());*/

        /*Uri uri=Uri.parse("http://biniyogbondhu.com/storage/videos/reconciliation.mp4");

        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(VideoActivity.this));
        videoView.start();*/

        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        Uri video = Uri.parse("http://biniyogbondhu.com/storage/videos/reconciliation.mp4");
        videoView.setMediaController(mc);
        videoView.setVideoURI(video);
        videoView.start();


    }


}

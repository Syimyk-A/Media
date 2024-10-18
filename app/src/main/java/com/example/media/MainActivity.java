package com.example.media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView songTitle;
    private ImageView imageView;
    private int currentSongIndex = 0;
    private int[] songs = {R.raw.song1, R.raw.song2, R.raw.song3}; // Убедитесь, что файлы песен находятся в res/raw
    private int currentImageIndex = 0;
    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3}; // Убедитесь, что файлы изображений находятся в res/drawable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songTitle = findViewById(R.id.songTitle);
        imageView = findViewById(R.id.imageView);
        Button playButton = findViewById(R.id.playButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button nextSongButton = findViewById(R.id.nextSongButton);
        Button prevSongButton = findViewById(R.id.prevSongButton);
        Button nextImageButton = findViewById(R.id.nextImageButton);
        Button prevImageButton = findViewById(R.id.prevImageButton);

        mediaPlayer = new MediaPlayer();
        playSong();

        playButton.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        pauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        nextSongButton.setOnClickListener(v -> {
            mediaPlayer.reset();
            currentSongIndex = (currentSongIndex + 1) % songs.length;
            playSong();
        });

        prevSongButton.setOnClickListener(v -> {
            mediaPlayer.reset();
            currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
            playSong();
        });

        nextImageButton.setOnClickListener(v -> {
            currentImageIndex = (currentImageIndex + 1) % images.length; // Следующее изображение
            updateImage();
        });

        prevImageButton.setOnClickListener(v -> {
            currentImageIndex = (currentImageIndex - 1 + images.length) % images.length; // Предыдущее изображение
            updateImage();
        });
    }

    private void playSong() {
        try {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
            updateSongTitle();
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибок
        }
    }

    private void updateSongTitle() {
        songTitle.setText("Song " + (currentSongIndex + 1));
    }

    private void updateImage() {
        imageView.setImageResource(images[currentImageIndex]); // Обновление изображения
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

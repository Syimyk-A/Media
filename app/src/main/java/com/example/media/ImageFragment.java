package com.example.media;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3}; // Замените на ваши изображения
    private int currentIndex = 0;
    private ImageView imageView;
    private Handler handler = new Handler();
    private Runnable imageSwitcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        imageView = view.findViewById(R.id.imageView);
        Button prevButton = view.findViewById(R.id.prevButton);
        Button nextButton = view.findViewById(R.id.nextButton);

        updateImage();

        prevButton.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + images.length) % images.length;
            updateImage();
        });

        nextButton.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % images.length;
            updateImage();
        });

        imageSwitcher = new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % images.length;
                updateImage();
                handler.postDelayed(this, 3000); // Меняем изображение каждые 3 секунды
            }
        };

        handler.postDelayed(imageSwitcher, 3000);

        return view;
    }

    private void updateImage() {
        imageView.setImageResource(images[currentIndex]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(imageSwitcher);
    }
}

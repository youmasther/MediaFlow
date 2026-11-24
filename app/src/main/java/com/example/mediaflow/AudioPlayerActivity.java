package com.example.mediaflow;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class AudioPlayerActivity extends AppCompatActivity {

    private boolean isPlaying = true;
    private boolean isRepeatOn = true;
    private boolean isScreenOn = false;
    private int totalSeconds = 272;

    private ImageButton btnPlayPause;
    private TextView tvPlaybackState, tvCurrentTime, tvRemainingTime;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_audio_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        totalSeconds = getIntent().getIntExtra("durationSeconds", 272);

        TextView tvTitle = findViewById(R.id.tvPlayerAudioTitle);
        TextView tvArtist = findViewById(R.id.tvPlayerAudioArtist);
        tvPlaybackState = findViewById(R.id.tvAudioPlaybackState);
        tvCurrentTime = findViewById(R.id.tvAudioCurrentTime);
        tvRemainingTime = findViewById(R.id.tvAudioRemainingTime);
        seekBar = findViewById(R.id.seekBarAudio);
        btnPlayPause = findViewById(R.id.btnAudioPlayPause);

        if (title != null) tvTitle.setText(title);
        if (subtitle != null) tvArtist.setText(subtitle);

        seekBar.setMax(totalSeconds);
        int initialProgress = Math.min(102, totalSeconds / 2);
        seekBar.setProgress(initialProgress);
        updateTimeLabels(initialProgress);

        // Retour
        findViewById(R.id.btnAudioBack).setOnClickListener(v -> finish());

        // Info piste
        findViewById(R.id.btnAudioInfo).setOnClickListener(v ->
                Toast.makeText(this, tvTitle.getText() + " — " + tvArtist.getText() + " · MP3 320 kbps", Toast.LENGTH_SHORT).show());

        // Menu contextuel
        ImageButton btnMenu = findViewById(R.id.btnAudioPlayerMenu);
        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnMenu);
            popup.getMenu().add("Ajouter aux favoris");
            popup.getMenu().add("Égaliseur");
            popup.getMenu().add("Minuterie de veille");
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });

        // Play / Pause
        btnPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            btnPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play);
            tvPlaybackState.setText(isPlaying ? "En cours de lecture" : "En pause");
        });

        // Stop
        findViewById(R.id.btnAudioStop).setOnClickListener(v -> {
            isPlaying = false;
            seekBar.setProgress(0);
            updateTimeLabels(0);
            btnPlayPause.setImageResource(R.drawable.ic_play);
            tvPlaybackState.setText("Lecture arrêtée");
        });

        // Recul / Avance 10s
        findViewById(R.id.btnAudioRewind).setOnClickListener(v -> shiftProgress(-10));
        findViewById(R.id.btnAudioForward).setOnClickListener(v -> shiftProgress(10));

        // SeekBar interactive
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                updateTimeLabels(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar bar) {}
            @Override public void onStopTrackingTouch(SeekBar bar) {}
        });

        // Répéter
        ImageView ivRepeat = findViewById(R.id.ivAudioRepeat);
        TextView tvRepeatTitle = findViewById(R.id.tvAudioRepeatTitle);
        TextView tvRepeatState = findViewById(R.id.tvAudioRepeatState);
        findViewById(R.id.cardAudioRepeat).setOnClickListener(v -> {
            isRepeatOn = !isRepeatOn;
            int color = ContextCompat.getColor(this, isRepeatOn ? R.color.accent_blue_light : R.color.text_primary);
            ivRepeat.setColorFilter(ContextCompat.getColor(this, isRepeatOn ? R.color.accent_blue_light : R.color.text_secondary));
            tvRepeatTitle.setTextColor(color);
            tvRepeatState.setText(isRepeatOn ? "Activé" : "Désactivé");
        });

        // Écran allumé
        ImageView ivScreen = findViewById(R.id.ivAudioScreenOn);
        TextView tvScreenTitle = findViewById(R.id.tvAudioScreenOnTitle);
        TextView tvScreenState = findViewById(R.id.tvAudioScreenOnState);
        findViewById(R.id.cardAudioScreenOn).setOnClickListener(v -> {
            isScreenOn = !isScreenOn;
            if (isScreenOn) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            int color = ContextCompat.getColor(this, isScreenOn ? R.color.accent_blue_light : R.color.text_primary);
            ivScreen.setColorFilter(ContextCompat.getColor(this, isScreenOn ? R.color.accent_blue_light : R.color.text_secondary));
            tvScreenTitle.setTextColor(color);
            tvScreenState.setText(isScreenOn ? "Activé" : "Désactivé");
        });
    }

    private void shiftProgress(int deltaSeconds) {
        int next = Math.max(0, Math.min(totalSeconds, seekBar.getProgress() + deltaSeconds));
        seekBar.setProgress(next);
        updateTimeLabels(next);
    }

    private void updateTimeLabels(int currentSec) {
        int remaining = Math.max(0, totalSeconds - currentSec);
        tvCurrentTime.setText(formatTime(currentSec));
        tvRemainingTime.setText("-" + formatTime(remaining));
    }

    private String formatTime(int seconds) {
        return String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
    }
}
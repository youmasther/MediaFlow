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

public class VideoPlayerActivity extends AppCompatActivity {

    private boolean isPlaying = true;
    private boolean isRepeatOn = true;
    private boolean isScreenOn = false;
    private int totalSeconds = 768;

    private ImageButton btnCenterPlayPause;
    private TextView tvStatusBadge, tvCurrentTime, tvTotalTime;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_video_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        totalSeconds = getIntent().getIntExtra("durationSeconds", 768);

        TextView tvFileName = findViewById(R.id.tvVideoFileName);
        TextView tvMainTitle = findViewById(R.id.tvVideoMainTitle);
        TextView tvSub = findViewById(R.id.tvVideoSubtitle);
        tvStatusBadge = findViewById(R.id.tvVideoStatusBadge);
        tvCurrentTime = findViewById(R.id.tvVideoCurrentTime);
        tvTotalTime = findViewById(R.id.tvVideoTotalTime);
        seekBar = findViewById(R.id.seekBarVideo);
        btnCenterPlayPause = findViewById(R.id.btnVideoCenterPlayPause);

        if (title != null) {
            tvFileName.setText(title.endsWith(".mp4") ? title : title + ".mp4");
            tvMainTitle.setText(title.replace(".mp4", ""));
        }
        if (subtitle != null) tvSub.setText(subtitle);

        seekBar.setMax(totalSeconds);
        int initialProgress = totalSeconds / 3;
        seekBar.setProgress(initialProgress);
        tvCurrentTime.setText(formatTime(initialProgress));
        tvTotalTime.setText(formatTime(totalSeconds));

        // Retour
        findViewById(R.id.btnVideoBack).setOnClickListener(v -> finish());

        // Menu contextuel
        ImageButton btnMenu = findViewById(R.id.btnVideoPlayerMenu);
        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnMenu);
            popup.getMenu().add("Piste audio : Français");
            popup.getMenu().add("Sous-titres : Désactivés");
            popup.getMenu().add("Vitesse : 1.0x");
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });

        // Play / Pause
        btnCenterPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            updatePlayStateUI();
        });

        // Stop (remet la barre à 00:00 et passe en Pause)
        findViewById(R.id.btnVideoStop).setOnClickListener(v -> {
            isPlaying = false;
            seekBar.setProgress(0);
            tvCurrentTime.setText(formatTime(0));
            btnCenterPlayPause.setImageResource(R.drawable.ic_play);
            tvStatusBadge.setText("ARRÊTÉ");
        });

        // Recul / Avance de 10 secondes
        findViewById(R.id.btnVideoRewind).setOnClickListener(v -> shiftProgress(-10));
        findViewById(R.id.btnVideoForward).setOnClickListener(v -> shiftProgress(10));

        // SeekBar interactive
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                tvCurrentTime.setText(formatTime(progress));
            }
            @Override public void onStartTrackingTouch(SeekBar bar) {}
            @Override public void onStopTrackingTouch(SeekBar bar) {}
        });

        // Option Répéter
        ImageView ivRepeat = findViewById(R.id.ivVideoRepeat);
        TextView tvRepeatTitle = findViewById(R.id.tvVideoRepeatTitle);
        TextView tvRepeatState = findViewById(R.id.tvVideoRepeatState);
        findViewById(R.id.cardVideoRepeat).setOnClickListener(v -> {
            isRepeatOn = !isRepeatOn;
            int color = ContextCompat.getColor(this, isRepeatOn ? R.color.accent_blue_light : R.color.text_primary);
            ivRepeat.setColorFilter(ContextCompat.getColor(this, isRepeatOn ? R.color.accent_blue_light : R.color.text_secondary));
            tvRepeatTitle.setTextColor(color);
            tvRepeatState.setText(isRepeatOn ? "Activé" : "Désactivé");
        });

        // Option Écran allumé (FLAG_KEEP_SCREEN_ON)
        ImageView ivScreen = findViewById(R.id.ivVideoScreenOn);
        TextView tvScreenTitle = findViewById(R.id.tvVideoScreenOnTitle);
        TextView tvScreenState = findViewById(R.id.tvVideoScreenOnState);
        findViewById(R.id.cardVideoScreenOn).setOnClickListener(v -> {
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

    private void updatePlayStateUI() {
        btnCenterPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play);
        tvStatusBadge.setText(isPlaying ? "EN LECTURE" : "EN PAUSE");
    }

    private void shiftProgress(int deltaSeconds) {
        int next = Math.max(0, Math.min(totalSeconds, seekBar.getProgress() + deltaSeconds));
        seekBar.setProgress(next);
        tvCurrentTime.setText(formatTime(next));
    }

    private String formatTime(int seconds) {
        return String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
    }
}
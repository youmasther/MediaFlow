package com.example.mediaflow;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public abstract class BaseTabActivity extends AppCompatActivity {

    protected boolean isMiniPlaying = true;

    protected void setupTopNavigation(int activeTabIndex) {
        TextView tvRecents = findViewById(R.id.tvTabRecents);
        TextView tvVideos = findViewById(R.id.tvTabVideos);
        TextView tvAudios = findViewById(R.id.tvTabAudios);

        View indRecents = findViewById(R.id.indRecents);
        View indVideos = findViewById(R.id.indVideos);
        View indAudios = findViewById(R.id.indAudios);

        int white = ContextCompat.getColor(this, R.color.text_primary);
        int grey = ContextCompat.getColor(this, R.color.text_secondary);

        tvRecents.setTextColor(activeTabIndex == 0 ? white : grey);
        tvVideos.setTextColor(activeTabIndex == 1 ? white : grey);
        tvAudios.setTextColor(activeTabIndex == 2 ? white : grey);

        indRecents.setVisibility(activeTabIndex == 0 ? View.VISIBLE : View.INVISIBLE);
        indVideos.setVisibility(activeTabIndex == 1 ? View.VISIBLE : View.INVISIBLE);
        indAudios.setVisibility(activeTabIndex == 2 ? View.VISIBLE : View.INVISIBLE);

        findViewById(R.id.tabRecents).setOnClickListener(v -> {
            if (activeTabIndex != 0) switchTab(RecentActivity.class);
        });
        findViewById(R.id.tabVideos).setOnClickListener(v -> {
            if (activeTabIndex != 1) switchTab(VideoActivity.class);
        });
        findViewById(R.id.tabAudios).setOnClickListener(v -> {
            if (activeTabIndex != 2) switchTab(AudioActivity.class);
        });

        findViewById(R.id.btnSearch).setOnClickListener(v ->
                Toast.makeText(this, "Recherche rapide", Toast.LENGTH_SHORT).show());

        View btnHeaderMenu = findViewById(R.id.btnHeaderMenu);
        btnHeaderMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, btnHeaderMenu);
            popup.getMenu().add("Actualiser la bibliothèque");
            popup.getMenu().add("Paramètres");
            popup.setOnMenuItemClickListener(item -> {
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });
    }

    private void switchTab(Class<?> target) {
        Intent intent = new Intent(this, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    protected void setupMiniPlayer() {
        View miniContainer = findViewById(R.id.miniPlayerContainer);
        ImageButton btnPlayPause = findViewById(R.id.btnMiniPlayPause);
        ImageButton btnClose = findViewById(R.id.btnMiniClose);

        if (miniContainer == null) {
            return;
        }

        miniContainer.setOnClickListener(v -> {
            Intent intent = new Intent(this, AudioPlayerActivity.class);
            intent.putExtra("title", "Midnight echoes");
            intent.putExtra("subtitle", "Aether");
            intent.putExtra("durationSeconds", 272);
            startActivity(intent);
        });

        if (btnPlayPause != null) {
            btnPlayPause.setOnClickListener(v -> {
                isMiniPlaying = !isMiniPlaying;
                btnPlayPause.setImageResource(isMiniPlaying ? R.drawable.ic_pause : R.drawable.ic_play);
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> miniContainer.setVisibility(View.GONE));
        }
    }

    protected void openPlayerForMedia(MediaItem item) {
        Intent intent = new Intent(this,
                item.getType() == MediaItem.TYPE_VIDEO ? VideoPlayerActivity.class : AudioPlayerActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("subtitle", item.getSubtitle());
        intent.putExtra("durationSeconds", item.getDurationSeconds());
        startActivity(intent);
    }

    protected void showItemContextMenu(View anchor, MediaItem item, Runnable onRemove) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_media_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_play) {
                openPlayerForMedia(item);
                return true;
            } else if (id == R.id.action_info) {
                Toast.makeText(this, item.getTitle() + " (" + item.getMetaInfo() + ")", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_remove) {
                onRemove.run();
                return true;
            }
            return false;
        });
        popup.show();
    }
}
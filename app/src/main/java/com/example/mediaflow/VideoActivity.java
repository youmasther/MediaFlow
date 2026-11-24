package com.example.mediaflow;

import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoActivity extends BaseTabActivity {

    private MediaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupTopNavigation(1);
        setupMiniPlayer();

        RecyclerView rv = findViewById(R.id.rvVideos);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        List<MediaItem> data = MockMediaRepository.getVideoItems();

        adapter = new MediaAdapter(data, MediaAdapter.MODE_VIDEO_GRID, new MediaAdapter.OnMediaInteractionListener() {
            @Override
            public void onMediaClick(MediaItem item) {
                openPlayerForMedia(item);
            }

            @Override
            public void onMoreClick(View anchor, MediaItem item, int position) {
                showItemContextMenu(anchor, item, () -> adapter.removeAt(position));
            }
        });
        rv.setAdapter(adapter);
    }
}
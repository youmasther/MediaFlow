package com.example.mediaflow;

import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AudioActivity extends BaseTabActivity {

    private MediaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_audio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupTopNavigation(2);
        setupMiniPlayer();

        RecyclerView rv = findViewById(R.id.rvAudios);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<MediaItem> data = MockMediaRepository.getAudioItems();

        adapter = new MediaAdapter(data, MediaAdapter.MODE_AUDIO_LIST, new MediaAdapter.OnMediaInteractionListener() {
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
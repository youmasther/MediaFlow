package com.example.mediaflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    public static final int MODE_RECENT = 0;
    public static final int MODE_VIDEO_GRID = 1;
    public static final int MODE_AUDIO_LIST = 2;

    public interface OnMediaInteractionListener {
        void onMediaClick(MediaItem item);
        void onMoreClick(View anchor, MediaItem item, int position);
    }

    private final List<MediaItem> items;
    private final int mode;
    private final OnMediaInteractionListener listener;

    public MediaAdapter(List<MediaItem> items, int mode, OnMediaInteractionListener listener) {
        this.items = items;
        this.mode = mode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mode == MODE_VIDEO_GRID ? R.layout.item_video_grid
                : (mode == MODE_AUDIO_LIST ? R.layout.item_audio : R.layout.item_recent);
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        MediaItem item = items.get(position);

        if (mode == MODE_VIDEO_GRID) {
            holder.tvTitle.setText(item.getTitle());
            holder.tvSubtitle.setText(item.getMetaInfo());
            holder.tvBadge.setText(item.getDurationText());
            holder.ivThumb.setImageResource(item.getImageResId());
        } else if (mode == MODE_AUDIO_LIST) {
            holder.tvTitle.setText(item.getTitle());
            holder.tvSubtitle.setText(item.getSubtitle());
            holder.tvBadge.setText(item.getDurationText());
            holder.ivThumb.setImageResource(item.getImageResId());
            if (item.isActive()) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_selected);
                holder.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.accent_blue_light));
            } else {
                holder.itemView.setBackground(null);
                holder.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary));
            }
        } else {
            holder.tvTitle.setText(item.getTitle());
            holder.tvSubtitle.setText(item.getMetaInfo());
            holder.ivThumb.setImageResource(item.getImageResId());
            if (item.isActive()) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_selected);
                holder.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.accent_blue_light));
            } else {
                holder.itemView.setBackground(null);
                holder.tvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_primary));
            }
        }

        holder.itemView.setOnClickListener(v -> listener.onMediaClick(item));
        holder.btnMore.setOnClickListener(v -> listener.onMoreClick(v, item, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeAt(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle, tvSubtitle, tvBadge;
        ImageButton btnMore;

        MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView.findViewById(R.id.ivVideoThumb) != null) {
                ivThumb = itemView.findViewById(R.id.ivVideoThumb);
                tvTitle = itemView.findViewById(R.id.tvVideoTitle);
                tvSubtitle = itemView.findViewById(R.id.tvVideoMeta);
                tvBadge = itemView.findViewById(R.id.tvDurationBadge);
                btnMore = itemView.findViewById(R.id.btnVideoMore);
            } else if (itemView.findViewById(R.id.ivAudioCover) != null) {
                ivThumb = itemView.findViewById(R.id.ivAudioCover);
                tvTitle = itemView.findViewById(R.id.tvAudioTitle);
                tvSubtitle = itemView.findViewById(R.id.tvAudioArtist);
                tvBadge = itemView.findViewById(R.id.tvAudioDuration);
                btnMore = itemView.findViewById(R.id.btnAudioMore);
            } else {
                ivThumb = itemView.findViewById(R.id.ivThumb);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
                btnMore = itemView.findViewById(R.id.btnMore);
            }
        }
    }
}
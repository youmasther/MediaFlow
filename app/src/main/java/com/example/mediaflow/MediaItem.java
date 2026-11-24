package com.example.mediaflow;

public class MediaItem {
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_AUDIO = 1;

    private final String title;
    private final String subtitle;      // Artiste ou description
    private final String metaInfo;      // Ex: "Vidéo · 12:48 · Aujourd'hui" ou "1,2 Go · 4K"
    private final String durationText;  // Ex: "12:48"
    private final int durationSeconds;
    private final int type;
    private final int imageResId;
    private final boolean active;

    public MediaItem(String title, String subtitle, String metaInfo, String durationText,
                     int durationSeconds, int type, int imageResId, boolean active) {
        this.title = title;
        this.subtitle = subtitle;
        this.metaInfo = metaInfo;
        this.durationText = durationText;
        this.durationSeconds = durationSeconds;
        this.type = type;
        this.imageResId = imageResId;
        this.active = active;
    }

    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getMetaInfo() { return metaInfo; }
    public String getDurationText() { return durationText; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getType() { return type; }
    public int getImageResId() { return imageResId; }
    public boolean isActive() { return active; }
}
package com.example.mediaflow;

import java.util.ArrayList;
import java.util.List;

public class MockMediaRepository {

    public static List<MediaItem> getRecentItems() {
        List<MediaItem> list = new ArrayList<>();
        list.add(new MediaItem("Dolomites — escapade.mp4", "Un instant, loin de tout.", "Vidéo · 12:48 · Aujourd'hui", "12:48", 768, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Midnight echoes", "Aether", "Audio · 4:32 · Aujourd'hui", "04:32", 272, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, true));
        list.add(new MediaItem("Côte sauvage.mp4", "Falaises atlantiques", "Vidéo · 3:24 · Hier", "03:24", 204, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Quiet mornings", "Nora Sol", "Audio · 3:48 · Hier", "03:48", 228, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("Tokyo after dark.mp4", "Rues néon sous la pluie", "Vidéo · 8:16 · Hier", "08:16", 496, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Forêt de brume.mp4", "Sentier matinal", "Vidéo · 5:42 · 18 nov.", "05:42", 342, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Blue horizon", "Aether", "Audio · 5:06 · 18 nov.", "05:06", 306, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        return list;
    }

    public static List<MediaItem> getVideoItems() {
        List<MediaItem> list = new ArrayList<>();
        list.add(new MediaItem("Dolomites — escapade", "Un instant, loin de tout.", "1,2 Go · 4K", "12:48", 768, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Côte sauvage", "Falaises atlantiques", "286 Mo · 1080p", "03:24", 204, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Tokyo after dark", "Rues néon sous la pluie", "748 Mo · 4K", "08:16", 496, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Forêt de brume", "Sentier matinal", "412 Mo · 1080p", "05:42", 342, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Carnet de voyage", "Souvenirs d'été", "530 Mo · 1080p", "06:10", 370, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        list.add(new MediaItem("Un matin dans les Alpes", "Sommets enneigés", "390 Mo · 1080p", "04:36", 276, MediaItem.TYPE_VIDEO, R.drawable.ic_launcher_background, false));
        return list;
    }

    public static List<MediaItem> getAudioItems() {
        List<MediaItem> list = new ArrayList<>();
        list.add(new MediaItem("Midnight echoes", "Aether", "04:32", "04:32", 272, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, true));
        list.add(new MediaItem("Quiet mornings", "Nora Sol", "03:48", "03:48", 228, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("Blue horizon", "Aether", "05:06", "05:06", 306, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("Slow motion", "Lune", "03:24", "03:24", 204, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("After hours", "Studio North", "04:18", "04:18", 258, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("Enregistrement_01.mp3", "Fichier audio", "02:18", "02:18", 138, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("First light", "Nora Sol", "03:52", "03:52", 232, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        list.add(new MediaItem("Into the woods", "Lune", "04:08", "04:08", 248, MediaItem.TYPE_AUDIO, R.drawable.ic_music_note, false));
        return list;
    }
}
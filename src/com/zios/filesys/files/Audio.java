package com.zios.filesys.files;

import com.zios.filesys.codecs.audio.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Audio extends FileSYS {

    private String duration;
    private String quality;
    private String hasCustomTag;
    private String sampleRate;
    private String track;
    private String artist;
    private String title;
    private String comment;
    private String year;
    private String genre;

    private FileInputStream stream;
    private BufferedInputStream bufferStream;

    public Audio(String path) {
        super(path);

        Mp3File mp3file = null;
        try {
            mp3file = new Mp3File(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

        if (mp3file.hasId3v1Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();

            this.track = id3v2Tag.getTrack();
            this.artist = id3v2Tag.getArtist();
            this.title = id3v2Tag.getTitle();
            this.comment = id3v2Tag.getComment();
            this.year = id3v2Tag.getYear();
            this.genre = String.valueOf(id3v2Tag.getGenre()).concat(" (").concat(id3v2Tag.getGenreDescription().concat(")"));
        } else {
            this.track = "No track information.";
            this.artist = "No artist information.";
            this.title = "No title information.";
            this.comment = "No comment information.";
            this.year = "No year information.";
            this.genre = "No genre information.";
        }


        this.duration = String.valueOf(mp3file.getLengthInSeconds());
        this.quality =  mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)");
        this.hasCustomTag = (mp3file.hasId3v1Tag() ? "YES" : "NO");
        this.sampleRate = mp3file.getSampleRate() + " Hz";
    }


    public String getDuration() {
        return duration;
    }

    public String getQuality() {
        return quality;
    }

    public String getHasCustomTag() {
        return hasCustomTag;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public String getTrack() {
        return track;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

}

package kr.hhplus.be.server.domain.concert.model;


import java.time.LocalDateTime;

public class Concert {
    private Long id;

    private String name;

    private String artist;

    private LocalDateTime date;

    private Concert(Long id, String name, String artist, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public static Concert reconstitute(Long id, String name, String artist, LocalDateTime date) {
        return new Concert(
                id,
                name,
                artist,
                date
        );
    }

    public void assignId(Long id) {
        this.id = id;
    }
}

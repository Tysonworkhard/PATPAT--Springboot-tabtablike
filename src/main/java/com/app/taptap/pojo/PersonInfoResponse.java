package com.app.taptap.pojo;

import java.util.List;

public class PersonInfoResponse {
    private User user;
    private List<Game> games;
    private List<Tag> tags;

    public PersonInfoResponse(User user, List<Game> games, List<Tag> tags) {
        this.user = user;
        this.games = games;
        this.tags = tags;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

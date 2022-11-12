package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Session data model
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public class Session {

    /**
     *  Movie id.
     */
    private int sessionId;

    /**
     *  Movie name.
     */
    private String name;

    /**
     *  Movie's year of release.
     */
    private int year;

    /**
     *  Movie's description.
     */
    private String description;

    public Session() {
    }

    public Session(String name) {
        this.name = name;
    }

    public Session(int sessionId) {
        this.sessionId = sessionId;
    }

    public Session(int sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public Session(int sessionId, String name, int year, String description) {
        this.sessionId = sessionId;
        this.name = name;
        this.year = year;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return sessionId == session.sessionId && Objects.equals(name, session.name) && Objects.equals(year, session.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, name, year);
    }

    @Override
    public String toString() {
        return "Session{"
                + "id=" + sessionId
                + ", name='" + name + '\''
                + ", year='" + year + '\''
                + ", desc='" + description + '\''
                + '}';
    }
}

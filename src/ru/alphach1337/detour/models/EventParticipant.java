package ru.alphach1337.detour.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.alphach1337.detour.Settings;
import ru.alphach1337.detour.helpers.LocationHelper;

public class EventParticipant {
    public EventParticipant(ResultSet rs) {
        try {
            this.setUUID(UUID.fromString(rs.getString("uuid")));
            this.setEvent(rs.getInt("event"));
            this.setLocation(LocationHelper.deserialize(rs.getString("location")));
            this.setIsReviewer(rs.getBoolean("reviewer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventParticipant(UUID uuid, int event, Location location, boolean reviewer, boolean ignore) {
        this.uuid = uuid;
        this.event = event;
        this.location = location;
        this.reviewer = reviewer;
        this.ignore = ignore;
    }

    public EventParticipant(int event, Player player) {
        this.uuid = player.getUniqueId();
        this.event = event;
        this.location = player.getLocation();
        this.reviewer = false;
        this.ignore = false;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Location getLocation() {
        return location;
    }

    public int getEvent() {
        return event;
    }

    public boolean getIsReviewer() {
        return reviewer;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public void setIsReviewer(boolean reviewer) {
        this.reviewer = reviewer;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    private UUID uuid;
    private Location location;
    private int event = -1;
    private boolean reviewer = false;
    private boolean ignore = false;
    //тут поменять всё на INSERT INTO Name (uuid, location, event, reviewer, ignore) VALUES(?,?,?,?,?) .set()...
    public String getSQLUpdateQuery() {
        return "UPDATE " + Settings.joinsTable + " SET " +
                "uuid = '" + this.getUUID() + "', " +
                "location = '" + LocationHelper.serialize(this.getLocation()) + "', " +
                "event = " + this.getEvent() + ", " +
                "reviewer = " + this.getIsReviewer() + ", " +
                "ignore = " + this.isIgnore() + " " +
                "WHERE event = " + this.getEvent() + " " +
                "AND uuid = '" + this.getUUID() + "'";
    }

    public String getSQLInsertQuery() {
        return "INSERT INTO " + Settings.joinsTable + " (uuid, location, event, reviewer, ignore)" +
                "VALUES(" +
                "'" + this.getUUID() + "'," +
                "'" + LocationHelper.serialize(this.getLocation()) + "'," +
                this.getEvent() + "," +
                this.getIsReviewer() + "," +
                this.isIgnore() + ")";

    }

    public String getSQLDeleteQuery() {
        return "DELETE FROM " + Settings.joinsTable + " WHERE " +
                "event = " + this.getEvent() + " AND " +
                "uuid = '" + this.getUUID() + "'";
    }
}

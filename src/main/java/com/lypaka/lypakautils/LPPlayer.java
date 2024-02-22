package com.lypaka.lypakautils;

import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.UUID;

public class LPPlayer {

    private final UUID uuid;
    private List<String> groups;
    private List<String> permissions;

    public LPPlayer (UUID uuid, List<String> groups, List<String> permissions) {

        this.uuid = uuid;
        this.groups = groups;
        this.permissions = permissions;

    }

    public UUID getUUID() {

        return this.uuid;

    }

    public List<String> getGroups() {

        return this.groups;

    }

    public void setGroups (List<String> groups) {

        this.groups = groups;

    }

    public List<String> getPermissions() {

        return this.permissions;

    }

    public void setPermissions (List<String> permissions) {

        this.permissions = permissions;

    }

    public void removePermission (String permission) {

        this.permissions.removeIf(entry -> entry.equalsIgnoreCase(permission));

    }

    public void addPermission (String permission) {

        if (!this.permissions.contains(permission)) this.permissions.add(permission);

    }

    public void removeGroup (String group) {

        this.groups.removeIf(entry -> entry.equalsIgnoreCase(group));

    }

    public void addGroup (String group) {

        if (!this.groups.contains(group)) this.groups.add(group);

    }

    public void save (boolean remove) throws SerializationException {

        LypakaUtils.playerConfigManager.getPlayerConfigNode(this.uuid, "Groups").set(this.groups);
        LypakaUtils.playerConfigManager.getPlayerConfigNode(this.uuid, "Permissions").set(this.permissions);
        LypakaUtils.playerConfigManager.savePlayer(this.uuid);

        if (remove) {

            LypakaUtils.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(this.uuid.toString()));

        }

    }

}

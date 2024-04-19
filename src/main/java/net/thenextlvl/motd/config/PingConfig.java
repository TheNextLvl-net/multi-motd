package net.thenextlvl.motd.config;

import com.google.gson.annotations.SerializedName;
import com.velocitypowered.api.proxy.server.ServerPing;

public record PingConfig(
        @SerializedName("max-players") int maxPlayers,
        @SerializedName("mod-type") String modType,
        @SerializedName("version") ServerPing.Version version,
        @SerializedName("description") String description
) {
}

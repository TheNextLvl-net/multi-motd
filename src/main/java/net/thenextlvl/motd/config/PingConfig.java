package net.thenextlvl.motd.config;

import com.google.gson.annotations.SerializedName;
import com.velocitypowered.api.proxy.server.ServerPing;
import org.jetbrains.annotations.Nullable;

public record PingConfig(
        @SerializedName("max-players") int maxPlayers,
        @SerializedName("mod-type") @Nullable String modType,
        @SerializedName("version") ServerPing.Version version,
        @SerializedName("description") String description
) {
}

package net.thenextlvl.motd.config;

import com.google.gson.annotations.SerializedName;

public record PluginConfig(
        @SerializedName("unreachable-server") PingConfig unreachableServer,
        @SerializedName("unknown-server") PingConfig unknownServer
) {
}

package net.thenextlvl.motd.config;

import com.google.gson.annotations.SerializedName;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record PluginConfig(
        @SerializedName("unreachable-server") PingConfig unreachableServer,
        @SerializedName("unknown-server") PingConfig unknownServer
) {
}

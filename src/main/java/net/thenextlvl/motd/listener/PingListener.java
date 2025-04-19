package net.thenextlvl.motd.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolState;
import com.velocitypowered.api.proxy.server.PingOptions;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.thenextlvl.motd.MultiMOTDPlugin;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PingListener {
    private final MultiMOTDPlugin plugin;

    public PingListener(MultiMOTDPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        if (!event.getConnection().getProtocolState().equals(ProtocolState.STATUS)) return;
        event.getConnection().getVirtualHost().ifPresent(address -> {
            var hosts = plugin.server().getConfiguration().getForcedHosts();
            var servers = hosts.get(address.getHostString());
            if (servers != null && !servers.isEmpty()) for (var server : servers) {
                try {
                    var config = plugin.config().unknownServer();
                    event.setPing(plugin.server().getServer(server).map(info -> {
                        var options = PingOptions.builder()
                                .version(event.getConnection().getProtocolVersion())
                                .build();
                        return info.ping(options).join();
                    }).orElseGet(() -> {
                        var builder = ServerPing.builder().version(config.version())
                                .maximumPlayers(config.maxPlayers())
                                .description(MiniMessage.miniMessage().deserialize(
                                        config.description())
                                );
                        if (config.modType() != null) builder.modType(config.modType());
                        else builder.notModCompatible();
                        return builder.build();
                    }));
                    return;
                } catch (Exception e) {
                    var config = plugin.config().unreachableServer();
                    var builder = ServerPing.builder().version(config.version())
                            .maximumPlayers(config.maxPlayers())
                            .description(MiniMessage.miniMessage().deserialize(
                                    config.description())
                            );
                    if (config.modType() != null) builder.modType(config.modType());
                    else builder.notModCompatible();
                    event.setPing(builder.build());
                }
            }
        });
    }
}

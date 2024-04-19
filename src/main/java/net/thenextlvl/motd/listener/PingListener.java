package net.thenextlvl.motd.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolState;
import com.velocitypowered.api.proxy.server.PingOptions;
import com.velocitypowered.api.proxy.server.ServerPing;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.thenextlvl.motd.MultiMOTDPlugin;

@RequiredArgsConstructor
public class PingListener {
    private final MultiMOTDPlugin plugin;

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        if (!event.getConnection().getProtocolState().equals(ProtocolState.STATUS)) return;
        event.getConnection().getVirtualHost().ifPresent(address -> {
            var hosts = plugin.getServer().getConfiguration().getForcedHosts();
            var servers = hosts.get(address.getHostString());
            if (servers != null && !servers.isEmpty()) for (var server : servers) {
                try {
                    var config = plugin.getConfig().unknownServer();
                    event.setPing(plugin.getServer().getServer(server).map(info -> {
                        var options = PingOptions.builder()
                                .version(event.getConnection().getProtocolVersion())
                                .build();
                        return info.ping(options).join();
                    }).orElse(ServerPing.builder().version(config.version())
                            .modType(config.modType()).maximumPlayers(config.maxPlayers())
                            .description(MiniMessage.miniMessage().deserialize(
                                    config.description())
                            ).build()));
                    return;
                } catch (Exception e) {
                    var config = plugin.getConfig().unreachableServer();
                    event.setPing(ServerPing.builder().version(config.version())
                            .modType(config.modType()).maximumPlayers(config.maxPlayers())
                            .description(MiniMessage.miniMessage().deserialize(
                                    config.description())
                            ).build());
                }
            }
        });
    }
}

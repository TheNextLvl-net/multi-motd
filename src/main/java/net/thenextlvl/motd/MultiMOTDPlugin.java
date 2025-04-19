package net.thenextlvl.motd;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import core.file.format.GsonFile;
import core.io.IO;
import net.thenextlvl.motd.config.PingConfig;
import net.thenextlvl.motd.config.PluginConfig;
import net.thenextlvl.motd.listener.PingListener;
import org.jspecify.annotations.NullMarked;

import java.nio.file.Path;

@Plugin(
        id = "multi-motd",
        name = "MultiMOTD",
        url = "https://thenextlvl.net",
        authors = "NonSwag",
        version = "1.0.0",
        description = "A MOTD plugin to ping forced hosts"
)
@NullMarked
public class MultiMOTDPlugin {
    private final PluginConfig config;
    private final ProxyServer server;

    @Inject
    public MultiMOTDPlugin(ProxyServer server, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.config = new GsonFile<>(IO.of(dataDirectory.toFile(), "config.json"), new PluginConfig(
                new PingConfig(0, "FML", new ServerPing.Version(-1, ""),
                        "<#FF0000>Can't connect to backend server"),
                new PingConfig(0, "FML", new ServerPing.Version(-1, ""),
                        "<#FF0000>Requested server does not exist")
        )).saveIfAbsent().getRoot();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PingListener(this));
    }

    public PluginConfig config() {
        return config;
    }

    public ProxyServer server() {
        return server;
    }
}

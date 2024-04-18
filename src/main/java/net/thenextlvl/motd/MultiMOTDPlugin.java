package net.thenextlvl.motd;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.thenextlvl.motd.listener.PingListener;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "multi-motd",
        name = "MultiMOTD",
        url = "https://thenextlvl.net",
        authors = "NonSwag",
        version = "1.0.0",
        description = "A MOTD plugin to ping forced hosts"
)
@Getter
public class MultiMOTDPlugin {
    private final ProxyServer server;
    private final Path dataDirectory;
    private final Logger logger;

    @Inject
    public MultiMOTDPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PingListener(this));
    }
}

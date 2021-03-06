package github.scarsz.discordsrv.hooks.permissions;

import github.scarsz.discordsrv.DiscordSRV;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.event.EventBus;
import me.lucko.luckperms.api.event.EventHandler;
import me.lucko.luckperms.api.event.user.track.UserPromoteEvent;
import me.lucko.luckperms.api.event.user.track.UserTrackEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @on 3/5/2017
 * @at 2:07 PM
 */
public class LuckPermsHook implements PermissionSystemHook, Listener {

    private final EventBus eventBus;
    private final EventHandler eventHandler;

    public LuckPermsHook() {
        Bukkit.getPluginManager().registerEvents(this, DiscordSRV.getPlugin());
        eventBus = LuckPerms.getApi().getEventBus();
        eventHandler = eventBus.subscribe(UserPromoteEvent.class, this::on);
    }

    private void on(UserTrackEvent event) {
        DiscordSRV.getPlugin().getGroupSynchronizationManager().reSyncGroups(Bukkit.getPlayer(event.getUser().getUuid()), event.getUser().getGroupNames().toArray(new String[0]));
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void on(PlayerJoinEvent event) {
        LuckPermsApi api = LuckPerms.getApi();
        DiscordSRV.getPlugin().getGroupSynchronizationManager().reSyncGroups(event.getPlayer(), api.getUser(event.getPlayer().getUniqueId()).getGroupNames().toArray(new String[0]));
    }

}

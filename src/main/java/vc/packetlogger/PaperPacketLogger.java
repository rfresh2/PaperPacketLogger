package vc.packetlogger;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.papermc.lib.PaperLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import vc.packetlogger.monitor.PacketEventsPacketMonitor;

public class PaperPacketLogger extends JavaPlugin {

  private PacketEventsPacketMonitor packetEventsPacketMonitor = new PacketEventsPacketMonitor();
  private PacketListenerAbstract packetEventsPacketMonitorAbstract = packetEventsPacketMonitor.asAbstract(PacketListenerPriority.LOWEST);

  @Override
  public void onEnable() {
    PaperLib.suggestPaper(this);
    saveDefaultConfig();
  }

  private void registerPacketEventsMonitor() {
    PacketEvents.getAPI().getEventManager().registerListener(packetEventsPacketMonitorAbstract);
  }

  private void unregisterPacketEventsMonitor() {
    PacketEvents.getAPI().getEventManager().unregisterListener(packetEventsPacketMonitorAbstract);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("pls")) {
      registerPacketEventsMonitor();
      return true;
    } else if (command.getName().equals("ple")) {
      unregisterPacketEventsMonitor();
      this.packetEventsPacketMonitor.log();
      return true;
    }
    return false;
  }
}

package vc.packetlogger.monitor;

import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.MonitorAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ProtocolLibPacketMonitor extends MonitorAdapter {
    private static final Logger LOGGER = getLogger(ProtocolLibPacketMonitor.class);
    public ProtocolLibPacketMonitor(final Plugin plugin, final ConnectionSide side) {
        super(plugin, side);
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        LOGGER.info("Packet sent: {}", packetEvent.getPacketType());
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        LOGGER.info("Packet received: {}", packetEvent.getPacketType());
    }
}

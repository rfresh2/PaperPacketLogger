package vc.packetlogger.monitor;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPong;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPing;
import org.slf4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

import static org.slf4j.LoggerFactory.getLogger;

public class PacketEventsPacketMonitor implements PacketListener {
    private static final Logger LOGGER = getLogger(PacketEventsPacketMonitor.class);
    private final LinkedBlockingQueue<PacketEvent> events = new LinkedBlockingQueue<>();

    public PacketEventsPacketMonitor() {
        super();
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        Integer pingId = null;
        if (event.getPacketType() == PacketType.Play.Client.PONG) {
            WrapperPlayClientPong pong = new WrapperPlayClientPong(event);
            pingId = pong.getId();
        }
        events.add(new PacketEvent(event.getPacketType().getName(), System.currentTimeMillis(), true, pingId));
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Integer pingId = null;
        if (event.getPacketType() == PacketType.Play.Server.PING) {
            WrapperPlayServerPing ping = new WrapperPlayServerPing(event);
            pingId = ping.getId();
        }
        events.add(new PacketEvent(event.getPacketType().getName(), System.currentTimeMillis(), false, pingId));
    }

    public void log() {
        final StringBuilder sb = new StringBuilder();
        events.stream().sorted((a, b) -> Long.compare(a.time, b.time)).forEach(event -> {
            sb.append("[").append(event.time).append("] ").append(event.side ? "Received" : "Sent").append(": ").append(event.packetName);
            if (event.pingId != null)
                sb.append(" (").append(event.pingId).append(")");
            sb.append("\n");
        });
        LOGGER.info("Packet events:\n{}", sb.toString());
        this.events.clear();
    }

    public record PacketEvent(String packetName, long time, boolean side, Integer pingId) { }
}

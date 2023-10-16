# Paper/Spigot Packet Logger

A simple plugin that will capture and print all packets received and sent to the server console.

## Commands

* `/pls` -> start capturing
* `/ple` -> stop capturing and log

## Limitations

* Only tested on Paper 1.20.1
* Does not inspect the contents of packets
  * Logs only the packet name, time, and direction.
* Only intended to be used on a server with a single player
  * Does not associate packets with players
* To test with GrimAC you'll want to change the GrimAC `PingPacketListener` priority to `NORMAL` so you can actually capture ping packets.
  * Requires code changes in Grim and you building Grim yourself

# Requirements/Dependencies

* [PacketEvents](https://modrinth.com/plugin/packetevents/version/2.0.2)

# Support

None. Sorry. I don't intend to develop this much further unless I have a need for some other feature.

package be.alexandre01.limbo.connection.packet.out;

import be.alexandre01.limbo.connection.packet.Packet;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 21:43
*/
public class PacketOutPing extends Packet {
    String json;
    public PacketOutPing(String json) {
        super((byte)0x00);
        this.json = json;
        write(json);
    }
}

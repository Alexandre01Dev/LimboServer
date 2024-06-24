package be.alexandre01.limbo.connection.packet.in;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:57
*/

import be.alexandre01.limbo.connection.ProtocolEnum;
import be.alexandre01.limbo.connection.packet.Packet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PacketINHandshake extends Packet {
    private String serverName;
    private int serverPort;
    private int protocolVersion;
    private ProtocolEnum protocolEnum;
    private State state;

    public enum State {
        PING,
        LOGIN,
    }

    public PacketINHandshake(String serverName, int serverPort, int protocolVersion, State state) {
        super((byte)0x00);
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.protocolVersion = protocolVersion;
        this.state = state;
        this.protocolEnum = ProtocolEnum.getProtocol(protocolVersion).orElse(null);
    }
}

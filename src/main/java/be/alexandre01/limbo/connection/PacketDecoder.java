package be.alexandre01.limbo.connection;

import be.alexandre01.limbo.connection.packet.in.PacketINHandshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:39
*/
public class PacketDecoder extends ByteToMessageDecoder {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // MC packet format
        // Packet length (VarInt) + Packet ID (VarInt) + Packet Data

        // Check if the packet length is less than 1

        if (byteBuf == null) return;
        if (byteBuf.readableBytes() < 1) return;

        byteBuf.markReaderIndex();
        // Read the packet length
        int packetLenght = byteBuf.readByte();
        byte nextByte = byteBuf.readByte();

        if (nextByte == 0x00) {
            int protocolVersion = byteBuf.readByte();
            int length = byteBuf.readByte();
            // read length chars
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            String s = new String(bytes, Charset.defaultCharset());

            short port = byteBuf.readShort();
            int nextState = byteBuf.readByte();

            PacketINHandshake.State state = nextState == 1 ? PacketINHandshake.State.PING : PacketINHandshake.State.LOGIN;
            PacketINHandshake packetHandshake = new PacketINHandshake(s, port, protocolVersion, state);
            list.add(packetHandshake);
        }

        if (nextByte == 0x01) {
            System.out.println("Received a packet with ID 0x01 => Ping");
        }

        byteBuf.resetReaderIndex();

    }

    public int readVarInt(ByteBuf byteBuf) {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = byteBuf.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }
}

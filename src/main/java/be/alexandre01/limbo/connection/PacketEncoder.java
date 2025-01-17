package be.alexandre01.limbo.connection;

import be.alexandre01.limbo.Main;
import be.alexandre01.limbo.connection.packet.Packet;
import be.alexandre01.limbo.connection.packet.in.PacketINHandshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:39
*/
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        System.out.println("Encoding packet: " + (packet.getLength()+ Main.increment));
        writeVarInt(packet.getLength()+ Main.increment,byteBuf);
        writeVarInt(packet.getId(),byteBuf);
        packet.getDatas().forEach(o -> {
            if(o instanceof String){
                byteBuf.writeBytes(((String)o).getBytes());
            }else if(o instanceof Integer){
                System.out.println("Writing int: " + o);
                writeVarInt((Integer)o,byteBuf);
            }else if(o instanceof Short){
                byteBuf.writeShort((Short)o);
            }else if(o instanceof Long){
                writeVarLong((Long)o,byteBuf);
            }
        });
        System.out.println("Packet encoded: " + packet.getClass().getSimpleName() + " with datas: " + byteBuf.toString(StandardCharsets.ISO_8859_1));
    }

    public static void writeVarInt(int value,ByteBuf byteBuf) {
        while ((value & ~0x7F) != 0) {
            byteBuf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        byteBuf.writeByte(value);
    }
    public void writeVarLong(long value, ByteBuf byteBuf) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                byteBuf.writeByte((byte) value);
                return;
            }

            byteBuf.writeByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }
}

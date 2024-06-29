package be.alexandre01.limbo.connection;

import be.alexandre01.limbo.connection.packet.Packet;
import be.alexandre01.limbo.connection.packet.in.PacketINHandshake;
import be.alexandre01.limbo.connection.packet.out.PacketOutPing;
import be.alexandre01.limbo.connection.utils.ServerPacketPing;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:41
*/
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, Object o) throws Exception {
        System.out.println("Received: " + o);

        if(o instanceof String){
            String s = (String)o;
            if(s.equals("ping")){
                ctx.writeAndFlush("pong");
            }
        }

        if(o instanceof ByteBuf){
            ByteBuf buf = (ByteBuf)o;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            System.out.println("Received bytes: " + new String(bytes));
        }

        if(o instanceof Packet){
            if(o instanceof PacketINHandshake){
                PacketINHandshake handshake = (PacketINHandshake)o;
                System.out.println("Received a handshake packet with serverName: " + handshake.getServerName() + " and serverPort: " + handshake.getServerPort() + " and protocolVersion: " + handshake.getProtocolVersion() + "("+ handshake.getProtocolEnum().name()+") and state: " + handshake.getState());
                System.out.println("Sending a ping packet");
                /* ctx.writeAndFlush(new PacketOutPing("{\n" +
                        "    \"version\": {\n" +
                        "        \"name\": \"1.19.4\",\n" +
                        "        \"protocol\": 762\n" +
                        "    },\n" +
                        "    \"players\": {\n" +
                        "        \"max\": 100,\n" +
                        "        \"online\": 666,\n" +
                        "        \"sample\": [\n" +
                        "            {\n" +
                        "                \"name\": \"thinkofdeath\",\n" +
                        "                \"id\": \"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"description\": {\n" +
                        "        \"text\": \"§cLimbo server\"\n" +
                        "    },\n" +
                        "    \"enforcesSecureChat\": false,\n" +
                        "    \"previewsChat\": false\n" +
                        "}"));*/

                /*
                    "version": "{\"name\":\"1.19.4\",\"protocol\":762}",
  "players": "{\"max\":100,\"online\":10,\"sample\":[{\"name\":\"Alexandre01\",\"id\":\"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"}]}",
  "description": "{\"text\":\"§cLimbo server\"}",
  "enforcesSecureChat": false,
  "previewsChat": false
                 */
                // {"version":"{\"name\":\"1.19.4\",\"protocol\":762}","players":"{\"max\":100,\"online\":10,\"sample\":[{\"name\":\"Alexandre01\",\"id\":\"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"}]}","description":"{\"text\":\"§cLimbo server\"}","enforcesSecureChat":false,"previewsChat":false}
                String i = "";

                    String json = new ServerPacketPing.Builder()
                            .players(500,10,"Alexandre01","4566e69f-c907-48ee-8d71-d7ba5aa00d20")
                            .chat(null,null)
                            .description("§cLimbo server\n §eHello, world!")
                            .version(handshake.getProtocolEnum().name().substring(1),handshake.getProtocolVersion())
                         //   .favicon("iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAACxLAAAsSwGlPZapAAAV7klEQVR4nOWbabBlV3Xff2vtc+70hn49qLsldSMJ1BIgWURYCEsxSCgiFBiMbBxMgCpsV2I7qVS+JE5SFZJUueyyK6mkKvlgm8ExgQTbhe0YCBAHo5LkEkgyQkgyg5g0S3S3enh6wx3O3mvlw97n3vu6n6QWUuuLd9Xpc+95556z13//17xb3J2/zaM69cLmJz8IDtprSKNFvAeqjrug3SVcEh4FUcXFkdCjqgeY1DTJIBoSupjUSG+Bqh7QHD+Ohx4itbiHRYndKwn2djN9paWOiPM9l/r/xMa/mmJ6OqCempqlXs3GxiaWYEKimxKVwrBZZWPyNH3p0vM+3kB3VDFphM3UsDQcoJOAjitsWFEJWAoArHzi1c8OwFkeC8C/9pDe7B5eZqIrqIknvVrM34D4rcBvACdeqgm9VAAERN7vqv/Sk18CsXJDxBF3A6vOMYt7gssVqtU7zP2/Ah8GmrM9MT3bL0DkJ4C/EPf/5jFeRkodkimWxFOEaGBR8KhuVpPSxWrpt8FvBrn+bE/vbAJwQZqMPxKfPvFZ13C9uy9LSoIZbgbt4eV7yt/dk3jyxSDx2vF4809Ssk+AvELO0iTPhgrsRuQD4v7PdTLeZx56UIFF3DUL64qb4x7AHdxxc8DBFDcFTKPJ7hDCu8XlTRPjd4HfB374Yk72xQNAJCDyc+D/xOLkNXi1ggkg4BG3AObggruDUYRX3DUL7kV4V3Ah/946Tjy/5/qvVPRthn3I4Y+B8Ysx7RcOgCCOXW+T8T828zeJyT7cxN1wFzBwd8TB3HBTpKy4t4J6yPcWIDIYMn0BDuDLJlyLyMW1yjuC6O+b+xeB9EKm/wIBkFeb+S+n8finiH6hUFeZ1oZ7LIIZeMDcy6qWlS7CZ7qnORDIf6OoBCBS1ASQxN4u1U1S9V9nSb7gyT8E8vWXGoB9jrzXnXdZtCsxH4gLWMLxQmHPVHfKWbcAgCuOlt8AlgMV3Mlrb1MA2iFRwYTkqaqsvkBcfiG5X+nInyN8Anj87AIgUiP6bpf0XhI")
                           // .favicon(this.getClass().getResourceAsStream("/server-icon2.png"))
                            .build().toString();

                    //378+25=403
                    //1530+26=1555

                    System.out.println("Sending a ping packet with json: " + json);

                    ctx.channel().remoteAddress().toString();
                    ctx.writeAndFlush(new PacketOutPing(json));

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("erreur");
    }
}

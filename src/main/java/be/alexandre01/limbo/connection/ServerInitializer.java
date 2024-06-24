package be.alexandre01.limbo.connection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:30
*/
@ChannelHandler.Sharable
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("decoder",new PacketDecoder());
        channel.pipeline().addLast("encoder",new PacketEncoder());
        channel.pipeline().addLast(new ServerHandler());

    }
}

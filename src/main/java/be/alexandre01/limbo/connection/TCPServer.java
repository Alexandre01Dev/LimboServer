package be.alexandre01.limbo.connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:26
*/
public class TCPServer {
    Thread thread;

    public TCPServer(int port) {
        // netty
        thread = new Thread(() -> {
            EventLoopGroup worker = new NioEventLoopGroup();
            EventLoopGroup boss = new NioEventLoopGroup();

            // start the server

            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler())
                        .childHandler(new ServerInitializer());

                Channel channel = b.bind(port).sync().channel();
                channel.closeFuture().sync();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                worker.shutdownGracefully();
                boss.shutdownGracefully();
            }
        });

        thread.start();

    }
}

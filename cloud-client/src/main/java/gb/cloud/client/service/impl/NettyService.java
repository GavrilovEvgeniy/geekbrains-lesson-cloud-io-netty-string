package gb.cloud.client.service.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.IOException;

public class NettyService {

        private SocketChannel channel;

        private static final String HOST = "localhost";
        private static final int PORT = 8189;

        public static void main(String[] args) throws IOException, InterruptedException {

            NettyService nettyService = new NettyService();
            nettyService.start();

            Thread.sleep(2000);
            System.out.println("Start write");

            ChannelFuture future = nettyService.channel.writeAndFlush(new ChunkedFile(new File("BM_055_57.brd")));
            future.addListener((ChannelFutureListener) channelFuture -> System.out.println("Finish write"));
        }

        private void start() {
            Thread t = new Thread(() -> {
                NioEventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    Bootstrap b = new Bootstrap();
                    b.group(workerGroup)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) {
                                    channel = socketChannel;
                                    socketChannel.pipeline().addLast(
                                            new ChunkedWriteHandler()
                                    );
                                }
                            });
                    ChannelFuture future = b.connect(HOST, PORT).sync();
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    workerGroup.shutdownGracefully();
                }
            });
            t.start();
        }

    }

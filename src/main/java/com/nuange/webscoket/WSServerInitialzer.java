package com.nuange.webscoket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化对通道的协议
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取管道对象
        ChannelPipeline pipeline = channel.pipeline();
        //websocket基于http协议，所需要的http编解码器
        pipeline.addLast(new HttpServerCodec());
        //在http上有一些数据流产生，有大有小，我们要对其进行处理，所以我们需要使用netty对大数据流进行读提供支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合处理，聚合成request或response
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        /**
         * 处理一些繁重复杂的事情
         * 会帮助用户处理握手动作
         * 对于文本Socket来讲，都是以frams进行传输的，不同的数据类型对应的frams也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //自定义的Handler
        pipeline.addLast(new ChatHandler());

        //激活IdLeStateHandler
        pipeline.addLast(new IdleStateHandler(8,10,12));
        //自定义的空闲即心跳状态检测的Handler
        pipeline.addLast(new HearBeatHandler());
    }
}

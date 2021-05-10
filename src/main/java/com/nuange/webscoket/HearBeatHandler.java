package com.nuange.webscoket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用于检测channel的心跳Handler
 * 继承ChannelInboundHandlerAdapter
 * 目的是不需要实现ChannelRead0方法
 */
public class HearBeatHandler extends ChannelInboundHandlerAdapter {
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt; //强制类型转化
            if (event.state() == IdleState.READER_IDLE){
                System.out.println("进入读空闲。。。。");
            }else if (event.state() == IdleState.WRITER_IDLE){
                System.out.println("进入写空间。。。。");
            }else if (event.state() == IdleState.ALL_IDLE){
                System.out.println("channel 关闭之前users的数量为：" + ChatHandler.users.size());
                Channel channel = ctx.channel();
                channel.close();
                System.out.println("channel关闭之后的users的数量为：" + ChatHandler.users.size());
            }
        }
    }
}

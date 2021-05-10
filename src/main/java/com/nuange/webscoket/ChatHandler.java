package com.nuange.webscoket;

import com.nuange.SpringUtil;
import com.nuange.enums.MsgActionEnum;
import com.nuange.service.UsersService;
import com.nuange.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于处理消息的Handler
 * 由于它的传输数据的载体是frams，这个frams在netty中，是用于为WebSocket专门处理文本对象的，frams是消息的载体，此类叫：TextWebSocketFrame
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        //1.获取客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        //获取Channel
        Channel channel = ctx.channel();
        //2.判断消息的类型，根据不同类型来处理不同的业务
        if (action == MsgActionEnum.CONNECT.type) {
            //2.1 当websocket，第一次open的时候，初始化channel，把用的channel和userid 关联起来
            String sendId = dataContent.getChatMsg().getSenderId();
            UserChannel.put(sendId, channel);
            for (Channel c : users) {
                System.out.println(c.id().asLongText());
            }
            UserChannel.output();
        } else if (action == MsgActionEnum.CHAT.type) {
            //2.2 聊天类型的类型，把聊天记录保存到数据库同时标记消息的签收状态是未签收
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgContent = chatMsg.getMsg();
            String senderId = chatMsg.getSenderId();
            String receiverId = chatMsg.getReceiverId();
            //保存消息到数据库并且标题为未签收
            UsersService usersService = (UsersService) SpringUtil.getBean("usersService");
            String msgId = usersService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            //保存聊天内容
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            //发送消息
            Channel receiverChannel = UserChannel.get(receiverId);
            if (receiverChannel == null) {
                //离线用户
            } else {
                //当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    //表明用户是在线的
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContentMsg)
                    ));
                } else {
                    //用户处于离线用户
                }
            }

        } else if (action == MsgActionEnum.SIGNED.type) {
            //2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态【已签收】
            //获取名字为userService的Bean
            UsersService usersService = (UsersService) SpringUtil.getBean("usersService");
            //扩展字段在Signed类型消息中，代表需要去签收消息id，逗号间隔
            String msgIdStr = dataContent.getExtand();
            String[] msgsId = msgIdStr.split(",");

            List<String> msgIdList = new ArrayList<>();

            for (String mid : msgsId) {
                if (StringUtils.isNotBlank(mid)) {
                    msgIdList.add(mid);
                }
            }
            System.out.println(msgIdList.toString());
            if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {
                //批量签收
                usersService.updateMsgSinged(msgIdList);
            }

        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            //2.4 心跳类型的消息
        }
        /**
         *
         *
         *
         *
         */


    }

    /**
     * 用户建立连接的时候添加通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    /**
     * 连接断开的使用就移除管道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //获取断开连接的id
        String chanelId = ctx.channel().id().asShortText();
        System.out.println("断开的客户端id为：" + chanelId);
        //断开连接之后直接移除
        users.remove(ctx.channel());

    }

    /**
     * 客户端连接异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生了异常后关闭连接，同时从channelgroup移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}

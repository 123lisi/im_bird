package com.nuange.service.impl;

import com.n3r.idworker.Sid;
import com.nuange.dao.*;
import com.nuange.entity.FriendsRequest;
import com.nuange.entity.MyFriends;
import com.nuange.entity.Users;
import com.nuange.enums.MsgActionEnum;
import com.nuange.enums.MsgSignFlagEnum;
import com.nuange.enums.SearchFriendsStatusEnum;
import com.nuange.service.UsersService;
import com.nuange.utils.FastDFSClient;
import com.nuange.utils.FileUtils;
import com.nuange.utils.JsonUtils;
import com.nuange.utils.QRCodeUtils;
import com.nuange.vo.FriendRequestVo;
import com.nuange.vo.MyFriendVO;
import com.nuange.webscoket.ChatMsg;
import com.nuange.webscoket.DataContent;
import com.nuange.webscoket.UserChannel;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2020-09-23 18:38:15
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {
    @Resource
    private UsersDao usersDao;

    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private MyFriendsDao myFriendsDao;
    @Autowired
    private FriendsRequestDao friendsRequestDao;
    @Autowired
    private UsersDaoCustom usersDaoCustom;
    @Autowired
    private ChatMsgDao chatMsgDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Users queryById(String id) {
        return this.usersDao.queryById(id);
    }

    /**
     * 通过username获取用户
     *
     * @param username 主键
     * @return
     */
    @Override
    public Users queryByUsername(String username) {
        return this.usersDao.queryByUsername(username);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Users> queryAllByLimit(int offset, int limit) {
        return this.usersDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    @Override
    public Users insert(Users users) {
        String userId = sid.nextShort();
        users.setId(userId);
        //生成一个唯一的二维码
        String qrCodePath = "C:\\" + userId + "qrcode.png";
        //创建二维码对象信息
        qrCodeUtils.createQRCode(qrCodePath, "bird_qrcode" + users.getUsername());
        MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrcodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users.setQrcode(qrCodeUrl);
        this.usersDao.insert(users);
        return users;
    }

    /**
     * 修改数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    @Override
    public Users update(Users users) {
        this.usersDao.update(users);
        return this.queryById(users.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.usersDao.deleteById(id) > 0;
    }

    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUserName) {
        /**
         * 前置条件
         * 1、搜索的用户存在？如果不存在，则返回无此用户
         */
        Users user = usersDao.queryByUsername(friendUserName);
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //2、搜索的账号如果是你自己，则返回不能添加自己
        if (myUserId.equals(user.getId())) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        // 3、搜索的账号已经是好友，则返回该用户已经是你的好友
        MyFriends myfriend = myFriendsDao.isOrNotMyfriend(myUserId, user.getId());
        if (myfriend != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Override
    public void sendFriendRequest(String myUserId, String friendUserName) {
        Users user = usersDao.queryByUsername(friendUserName);
        MyFriends orNotMyfriend = myFriendsDao.isOrNotMyfriend(myUserId, user.getId());
        if (orNotMyfriend == null) {
            FriendsRequest friendsRequest = new FriendsRequest();
            String requestId = sid.nextShort();
            friendsRequest.setId(requestId);
            friendsRequest.setAcceptUserId(user.getId());
            friendsRequest.setSendUserId(myUserId);
            friendsRequest.setRequestDateTime(new Date());
            friendsRequestDao.insert(friendsRequest);
        }
    }

    @Override
    public List<FriendRequestVo> queryFriendRequestList(String acceptUserId) {
        return usersDaoCustom.queryFriendRequestList(acceptUserId);
    }

    /**
     * 删除请求表的请求
     *
     * @param friendsRequest
     */
    @Override
    public void deleteFriendRequest(FriendsRequest friendsRequest) {
        friendsRequestDao.deleteByFriendRequest(friendsRequest);
    }

    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        //进行双向好友数据保存
        saveFriend(sendUserId, acceptUserId);
        saveFriend(acceptUserId, sendUserId);

        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setAcceptUserId(acceptUserId);
        friendsRequest.setSendUserId(sendUserId);
        //删除请求表中的好友请求
        friendsRequestDao.deleteByFriendRequest(friendsRequest);

        //获取通道
        Channel channel = UserChannel.get(sendUserId);
        //用户在线然后推送消息
        if (channel != null) {
            //使用websocket主动推送消息到请求发起者，更新他的通讯录
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
            //消息推送
            channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
        }
    }

    //通过好友请求并保存数据到My_friend表中
    private void saveFriend(String sendUserId, String acceptUserId) {
        MyFriends myFriends = new MyFriends();
        myFriends.setId(sid.nextShort());
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        myFriendsDao.insert(myFriends);
    }

    //获取好友请求
    @Override
    public List<MyFriendVO> queryMyfriend(String userId) {
        List<MyFriendVO> myFriendVOS = usersDaoCustom.queryMyFriends(userId);
        return myFriendVOS;
    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.nuange.entity.ChatMsg msgDB = new com.nuange.entity.ChatMsg();
        String msgId = sid.nextShort();
        msgDB.setId(msgId);
        msgDB.setAcceptUserId(chatMsg.getReceiverId());
        msgDB.setSendUserId(chatMsg.getSenderId());
        msgDB.setCreateTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDB.setMsg(chatMsg.getMsg());
        chatMsgDao.insert(msgDB);
        return msgId;

    }

    @Override
    public void updateMsgSinged(List<String> msgList) {
        usersDaoCustom.batchUpdateMsgSigned(msgList);
    }

    @Override
    public List<com.nuange.entity.ChatMsg> getUnReadMsgList(String acceptUserId) {
        List<com.nuange.entity.ChatMsg> unReadMsgList = chatMsgDao.getUnReadMsgList(acceptUserId);
        return unReadMsgList;
    }
}
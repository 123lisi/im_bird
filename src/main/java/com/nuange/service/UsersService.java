package com.nuange.service;

import com.nuange.entity.FriendsRequest;
import com.nuange.entity.Users;
import com.nuange.vo.FriendRequestVo;
import com.nuange.vo.MyFriendVO;
import com.nuange.webscoket.ChatMsg;

import java.util.List;

/**
 * (Users)表服务接口
 *
 * @author makejava
 * @since 2020-09-23 18:38:15
 */
public interface UsersService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Users queryById(String id);
    /**
     * 通过ID查询单条数据
     *
     * @param username 主键
     * @return 实例对象
     */
    Users queryByUsername(String username);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Users> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    Users insert(Users users);

    /**
     * 修改数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    Users update(Users users);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
    /**
     * 搜索好友的前置条件
     */
    Integer preconditionSearchFriends(String myUserId, String friendUserName);
    /**
     * 发送请求好友的方法
     */
    void sendFriendRequest(String myUserId,String friendUserName);

    /**
     * 添加好友请求的方法
     * @param acceptUserId
     * @return
     */
    List<FriendRequestVo> queryFriendRequestList(String acceptUserId);
    //处理好友请求-忽略好友请求
    void deleteFriendRequest(FriendsRequest friendsRequest);
    //处理好友请求-通过好友请求
    void passFriendRequest(String sendUserId,String acceptUserId);
    //好友列表查询
    List<MyFriendVO> queryMyfriend(String userId);
    //保存用户聊天消息
    String saveMsg(ChatMsg chatMsg);
    //批量修改消息状态
    void updateMsgSinged(List<String> msgList);
    //查询未签收消息的列表
    List<com.nuange.entity.ChatMsg> getUnReadMsgList(String acceptUserId);

}
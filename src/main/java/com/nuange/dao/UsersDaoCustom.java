package com.nuange.dao;


import com.nuange.vo.FriendRequestVo;
import com.nuange.vo.MyFriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersDaoCustom {
    //请求好友
    List<FriendRequestVo> queryFriendRequestList(String acceptUserId);
    //获取好友
    List<MyFriendVO> queryMyFriends(String userId);

    //批量修改消息状态的方法
    void batchUpdateMsgSigned(@Param("msgIdList") List<String> msgIdList);

}

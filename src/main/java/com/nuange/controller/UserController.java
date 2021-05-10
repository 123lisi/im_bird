package com.nuange.controller;

import com.nuange.bo.UserBo;
import com.nuange.entity.ChatMsg;
import com.nuange.entity.FriendsRequest;
import com.nuange.entity.Users;
import com.nuange.enums.OperatorFriendRequestTypeEnum;
import com.nuange.enums.SearchFriendsStatusEnum;
import com.nuange.service.ChatMsgService;
import com.nuange.service.UsersService;
import com.nuange.utils.FastDFSClient;
import com.nuange.utils.FileUtils;
import com.nuange.utils.IWdzlJSONResult;
import com.nuange.utils.MD5Utils;
import com.nuange.vo.FriendRequestVo;
import com.nuange.vo.MyFriendVO;
import com.nuange.vo.UsersVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ChatMsgService chatMsgService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private FastDFSClient fastDFSClient;

    @CrossOrigin
    @RequestMapping(value = "/registerOrLogin",method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult registerOrLogin(Users user) {
        Users userResult = usersService.queryByUsername(user.getUsername());
        if (userResult != null) { //用户存在则可以登录
            if (!userResult.getPassword().equals(MD5Utils.getPwd(user.getPassword()))){
                return IWdzlJSONResult.errorMsg("密码不正确");
            }

        }else { //注册
            user.setNickname(user.getUsername());
            user.setQrcode("");
            user.setPassword(MD5Utils.getPwd(user.getPassword()));
            user.setFaceImage("");
            user.setFaceImageBig("");
            userResult = usersService.insert(user);
        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(userResult,usersVo);
        return IWdzlJSONResult.ok(usersVo);
    }

    /**
     * 上传头像
     * @param userBo
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @RequestMapping(value = "/uploadFaceBase64",method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult registerOrLogin(@RequestBody UserBo userBo) throws Exception {
        //获取前端传过来的base64的字符串，然后转化为文件对象再进行上传
        String base61Data = userBo.getFaceData();
        String userFacePath = "C:\\" +userBo.getUserId() +"userFaceBase64.png";
        //调用FileUtils类中的方法将base64字符串转为文件对象
        FileUtils.base64ToFile(userFacePath,base61Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        //获取对应的图片url
        String url = fastDFSClient.uploadBase64(multipartFile);
        System.out.println(url);
        String thump ="_150x150.";
        String[] arr = url.split("\\.");
        String thumpImgUrl = arr[0] + thump + arr[1];

        //更新用户头像
        Users user = new Users();
        user.setId(userBo.getUserId());
        user.setFaceImage(thumpImgUrl);
        user.setFaceImageBig(url);
        Users userResult = usersService.update(user);
        System.out.println(userResult.toString());
        return IWdzlJSONResult.ok(userResult);
    }

    /**
     * 获取用户未读取信息的消息列表
     * @return
     */
    @RequestMapping(value = "/getUnReadMsgList", method = RequestMethod.GET)
    @ResponseBody
    public IWdzlJSONResult getUnReadMsgList(String acceptUserId) {
       if (StringUtils.isBlank(acceptUserId)){
           return IWdzlJSONResult.errorMsg("接受者ID不能为空！");
       }
       //根据接受者ID未签收的消息列表
        List<ChatMsg> unReadMsgList = usersService.getUnReadMsgList(acceptUserId);
       return IWdzlJSONResult.ok(unReadMsgList);
    }
    /**
     * 查找好友
     */
    @RequestMapping(value = "/searchFriend", method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult searchFriend(String myUserId,String friendUserName ){
        /**
         * 前置条件
         * 1、搜索的用户存在？如果不存在，则返回无此用户
         * 2、搜索的账号如果是你自己，则返回不能添加自己
         * 3、搜索的账号已经是好友，则返回该用户已经是你的好友
         */
        Integer status = usersService.preconditionSearchFriends(myUserId, friendUserName);
        if (status== SearchFriendsStatusEnum.SUCCESS.status){
            Users user = usersService.queryByUsername(friendUserName);
            UsersVo usersVo = new UsersVo();
            BeanUtils.copyProperties(user,usersVo);
            return IWdzlJSONResult.ok(usersVo);
        }else {
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IWdzlJSONResult.errorMsg(msg);
        }
    }
    /**
     * 发送添加好友请求
     * addFriendRequest
     */
    @RequestMapping(value = "/addFriendRequest", method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult addFriendRequest(String myUserId,String friendUserName ){
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUserName)){
            return IWdzlJSONResult.errorMsg("好友信息为空");
        }
        Integer status = usersService.preconditionSearchFriends(myUserId, friendUserName);
        if (status == SearchFriendsStatusEnum.SUCCESS.status){
            usersService.sendFriendRequest(myUserId,friendUserName);
        }else {
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IWdzlJSONResult.errorMsg(msg);
        }
        return IWdzlJSONResult.ok();
    }
    /**
     * 好友请求列表
     */
    @RequestMapping(value = "/queryFriendRequest", method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult queryFriendRequest(String userId){
        List<FriendRequestVo> friendRequestVoList = usersService.queryFriendRequestList(userId);
        return IWdzlJSONResult.ok(friendRequestVoList);
    }
    /**
     * queryFriendRequest
     * 处理的好友请求
     */
    @RequestMapping(value = "/operFriendRequest", method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult queryFriendRequest(String acceptUserId,String sendUserId,Integer operType){
        if (acceptUserId==null||sendUserId==null||operType==null){
            return IWdzlJSONResult.errorMsg("参数不能为空！");
        }
        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setSendUserId(sendUserId);
        friendsRequest.setAcceptUserId(acceptUserId);
        if (operType== OperatorFriendRequestTypeEnum.IGNORE.type){
            //表示用户忽略改好友的请求，则是删除请求表中对应的数据
            usersService.deleteFriendRequest(friendsRequest);

        }else if (operType==OperatorFriendRequestTypeEnum.PASS.type){
            //表示用户通过该还有的请求，同时删除请求表中对应的数据
            usersService.passFriendRequest(sendUserId,acceptUserId);
        }
        //查询好友请求表中的数据
        List<MyFriendVO> myFriendVOS = usersService.queryMyfriend(acceptUserId);
        return IWdzlJSONResult.ok(myFriendVOS);
    }
    @RequestMapping(value = "/myFriends", method = RequestMethod.POST)
    @ResponseBody
    public IWdzlJSONResult queryFriends(String userId){
        //获取自己好友列表数据
        List<MyFriendVO> myfriends = usersService.queryMyfriend(userId);
        return IWdzlJSONResult.ok(myfriends);
    }

}

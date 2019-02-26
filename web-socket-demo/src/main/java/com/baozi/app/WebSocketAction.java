package com.baozi.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by baozi on 2018/6/11.
 */
@Controller
public class WebSocketAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //spring提供的发送消息模板
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @RequestMapping(value = "/test_user")
    @ResponseBody
    public String templateTest(@RequestParam String userId2) {
        logger.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            logger.info("用户" + i++ + "---" + user);
        }

        List<String> userIds = Arrays.asList("1001", "1002");

        //发送消息给指定用户
        try {
            for (String userId : userIds) {
                String message = "服务器主动推的数据" + new Date() + " userId:" + userId;
                messagingTemplate.convertAndSendToUser(userId, "/queue/message", message);
                logger.info("send message to user <{}>", message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value = "/test_all")
    @ResponseBody
    public String templateTestAll() {
        logger.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            logger.info("用户" + i++ + "---" + user);
        }

        try {
            String message = "服务器主动推的数据" + new Date();
            messagingTemplate.convertAndSend("/topic/message", message);
            logger.info("send message to all <{}>", message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "OK";
    }

}

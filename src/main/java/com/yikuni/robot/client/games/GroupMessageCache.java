package com.yikuni.robot.client.games;

import com.yikuni.robot.client.util.Pair;
import love.forte.simbot.ID;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.ReceivedMessageContent;

import java.util.HashMap;
import java.util.LinkedList;

public class GroupMessageCache {

    private final HashMap<ID, LinkedList<Pair<ID, ReceivedMessageContent>>> messages = new HashMap<>();
    private static final GroupMessageCache groupMessageCache = new GroupMessageCache();
    public static final int MAX_CACHE_MESSAGE = 8;

    public static GroupMessageCache getInstance(){
        return groupMessageCache;
    }

    /**
     * 判断是否要复读
     * @param groupId   组的id
     * @param message   消息和ID
     * @return  非空字符串, 要复读的信息, null, 不复读
     */
    public static Messages compareAndAdd(ID groupId, Pair<ID, ReceivedMessageContent> message, ID botId){
        LinkedList<Pair<ID,ReceivedMessageContent>> list = groupMessageCache.getMessages().get(groupId);

        if(list == null){
            // 如果群组没有加入缓存
            list = new LinkedList<>();
            list.push(message);
            groupMessageCache.getMessages().put(groupId, list);
        }else{
            // 如果群组已经加入缓存了
            if(list.size() > MAX_CACHE_MESSAGE){
                // 如果list比较长
                for(int i = MAX_CACHE_MESSAGE; i < list.size(); i++){
                    list.removeLast();
                }
            }

            boolean doRepeat = false;
            for(Pair<ID, ReceivedMessageContent> messagePair: list){
                if (messagePair.getValue().getMessages().equals(message.getValue().getMessages())){
                    // 如果发现有重复的话
                    if(messagePair.getKey().toString().equals(botId.toString())){
                        // 如果发现有自己的复读的话
                        doRepeat = false;
                        break;
                    }else{
                        doRepeat = true;
                    }
                }else{
                    // 到这个地方复读中断或者没复读过
                    break;
                }
            }

            // 将传进来的话加入list
            list.push(message);

            if(doRepeat){
                // 如果要复读
                return message.getValue().getMessages();
            }else{
                // 不复读, 返回null
                return null;
            }
        }
        return null;
    }

    public HashMap<ID, LinkedList<Pair<ID, ReceivedMessageContent>>> getMessages() {
        return messages;
    }

    public static void addMessage(ID groupId, Pair<ID, ReceivedMessageContent> messagePair){
        groupMessageCache.messages.get(groupId).push(messagePair);
    }
}

package com.yikuni.robot.client.service;

import love.forte.simbot.ID;

public interface NormalTalkService {
    String getNormalTalk(String content, ID qqAccount);
    String deleteNormalTalk(int id, ID qqAccount);
    String recoverNormalTalk(int id, ID qqAccount);
    String updateNormalTalk(int id, String content, ID qqAccount);
    String searchByQq(Long ownerQq, ID qqAccount);
    String searchByContent(String content, ID qqAccount);
    String searchByQqAndContent(String content, Long ownerQq, ID qqAccount);
    String insertNormalTalk(String content, String reply, ID qqAccount);
}

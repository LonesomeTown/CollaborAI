package com.smu.data.entity;

import com.vaadin.collaborationengine.UserInfo;
import org.bson.types.ObjectId;

import java.util.Random;

/**
 * ChatRobot
 *
 * @author T.W 2/27/23
 */
public class ChatRobot extends UserInfo {
    public ChatRobot (String userId){
        super(userId,"Chat Robot");
    }

}

/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fixengine.messages;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import fixengine.tags.DeliverToCompID;
import fixengine.tags.MsgSeqNum;
import fixengine.tags.OnBehalfOfCompID;
import fixengine.tags.OrigSendingTime;
import fixengine.tags.PossDupFlag;
import fixengine.tags.PossResend;
import fixengine.tags.SenderCompID;
import fixengine.tags.SendingTime;
import fixengine.tags.TargetCompID;

/**
 * @author Pekka Enberg 
 */
public class MessageHeader extends AbstractFieldContainer implements Parseable {
    private static final Minutes MAX_TIME_DIFFERENCE = Minutes.TWO;

    private String beginString;
    private int bodyLength;
    private String msgType;

    private int msgTypePosition;

    public MessageHeader(MsgTypeValue msgType) {
        this(msgType.value());
    }

    public MessageHeader(String msgType) {
        this();

        setMsgType(msgType);
    }

    public MessageHeader() {
        field(SenderCompID.TAG);
        field(TargetCompID.TAG);
        field(OnBehalfOfCompID.TAG, Required.NO);
        field(DeliverToCompID.TAG, Required.NO);
        field(MsgSeqNum.TAG);
        field(PossDupFlag.TAG, Required.NO);
        field(PossResend.TAG, Required.NO);
        field(SendingTime.TAG);
        field(OrigSendingTime.TAG, Required.NO);
    }

    public String getBeginString() {
        return beginString;
    }

    public void setBeginString(String beginString) {
      this.beginString = beginString;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public boolean isPointToPoint() {
        return !hasValue(OnBehalfOfCompID.TAG) && !hasValue(DeliverToCompID.TAG);
    }

    public boolean hasAccurateSendingTime(DateTime currentTime) {
        if (!hasValue(SendingTime.TAG)) {
            return true;
        }
        Minutes difference = Minutes.minutesBetween(currentTime, getDateTime(SendingTime.TAG));
        return difference.isLessThan(MAX_TIME_DIFFERENCE);
    }

    public boolean hasOrigSendTimeAfterSendingTime() {
        if (!getBoolean(PossDupFlag.TAG) || !hasValue(OrigSendingTime.TAG)) {
            return true;
        }
        return !getDateTime(OrigSendingTime.TAG).isAfter(getDateTime(SendingTime.TAG));
    }

    public void setMsgTypePosition(int msgTypePosition) {
        this.msgTypePosition = msgTypePosition;
    }

    public int getMsgTypePosition() {
        return msgTypePosition;
    }

    public Message newMessage() {
        MsgTypeValue type = MsgTypeValue.parse(getMsgType());
        if (type == null) {
            throw new RuntimeException();
        }
        return type.newMessage(this);
    }
}

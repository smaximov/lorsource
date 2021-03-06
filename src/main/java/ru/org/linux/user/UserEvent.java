/*
 * Copyright 1998-2015 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.org.linux.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Элемент списка уведомлений
 */
public class UserEvent implements Serializable {
  private final int cid;
  private final int cAuthor;
  private final int tAuthor;
  private final int groupId;
  private static final long serialVersionUID = -8433869244309809050L;
  private final String subj;
  private final int msgid;
  private final UserEventFilterEnum type;
  private final String eventMessage;
  private final Timestamp eventDate;
  private final boolean unread;
  private final int id;

  public UserEvent(int cid, int cAuthor,
                   int groupId, String subj,
                   int msgid, UserEventFilterEnum type, String eventMessage,
                   Timestamp eventDate, boolean unread, int tAuthor, int id) {
    this.cid = cid;
    this.cAuthor = cAuthor;
    this.groupId = groupId;
    this.subj = subj;
    this.msgid = msgid;
    this.type = type;
    this.eventMessage = eventMessage;
    this.eventDate = eventDate;
    this.unread = unread;
    this.tAuthor = tAuthor;
    this.id = id;
  }
  
  public boolean isComment() {
    return cid>0;
  }

  public int getCid() {
    return cid;
  }

  public int getCommentAuthor() {
    return cAuthor;
  }

  public int getTopicAuthor() {
    return tAuthor;
  }

  public String getSubj() {
    return subj;
  }

  public int getTopicId() {
    return msgid;
  }

  public UserEventFilterEnum getType() {
    return type;
  }

  public String getEventMessage() {
    return eventMessage;
  }

  public Timestamp getEventDate() {
    return eventDate;
  }

  public boolean isUnread() {
    return unread;
  }

  public int getId() {
    return id;
  }

  public int getGroupId() {
    return groupId;
  }
}
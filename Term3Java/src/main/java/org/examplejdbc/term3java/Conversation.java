package org.examplejdbc.term3java;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Conversation {
    private SimpleIntegerProperty CustomerId;
    private SimpleStringProperty CustomerName;
    private SimpleIntegerProperty AgentId;
    private SimpleStringProperty AgentName;
    private SimpleIntegerProperty MsgCount;
    private boolean MsgFlagged;
    private SimpleStringProperty ActionRequired;
    public Conversation(int CustomerId, String CustomerName, int AgentId, String AgentName, int MsgCount, boolean MsgFlagged) {
        this.CustomerId = new SimpleIntegerProperty(CustomerId);
        this.CustomerName = new SimpleStringProperty(CustomerName);
        this.AgentId = new SimpleIntegerProperty(AgentId);
        this.AgentName = new SimpleStringProperty(AgentName);
        this.MsgCount = new SimpleIntegerProperty(MsgCount);
        this.MsgFlagged = MsgFlagged;
        if(MsgFlagged){
            this.ActionRequired = new SimpleStringProperty("Yes");
        }else
            this.ActionRequired = new SimpleStringProperty("");
    }
    public int getCustomerId() {return CustomerId.get();}
    public void setCustomerId(int CustomerId) {this.CustomerId.set(CustomerId);}
    public String getCustomerName() {return CustomerName.get();}
    public void setCustomerName(String CustomerName) {this.CustomerName.set(CustomerName);}
    public int getAgentId() {return AgentId.get();}
    public void setAgentId(int AgentId) {this.AgentId.set(AgentId);}
    public String getAgentName() {return AgentName.get();}
    public void setAgentName(String AgentName) {this.AgentName.set(AgentName);}
    public int getMsgCount() {return MsgCount.get();}
    public void setMsgCount(int MsgCount) {this.MsgCount.set(MsgCount);}
    public boolean getMsgFlagged() {return MsgFlagged;}


}
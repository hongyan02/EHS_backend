package org.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "check_items")
public class CheckItem {
    @Id
    private String id;
    
    @javax.persistence.Column(name = "item_name")
    private String itemName;
    
    @javax.persistence.Column(name = "method")
    private String checkMethod;
    
    @javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
    @javax.persistence.Column(name = "frequency")
    private CheckPeriod checkPeriod;  // Daily, Weekly, Monthly, Yearly

    public enum CheckPeriod {
        Daily,
        Weekly,
        Monthly,
        Yearly
    }
    
    private String description;
    
    private String status;
    
    private String createTime;
    
    private String updateTime;
}
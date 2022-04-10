package com.temple.manager.notification.entity;

import com.temple.manager.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "active=99999999999999")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long notificationId;

    @Column(nullable = false, length = 250)
    String requestMessage;

    @Column(length = 30)
    String responseTitle;

    @Column(length = 250)
    String responseMessage;

    @Column(nullable = false)
    boolean confirm;

    public void confirmNotification(){
        this.confirm = true;
    }

    public void responseRegistration(String title, String message){
        this.responseTitle = title;
        this.responseMessage = message;
    }
}

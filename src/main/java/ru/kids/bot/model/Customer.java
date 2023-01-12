package ru.kids.bot.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity(name = "customers")
public class Customer {
    @Id
    private Long chat_id;
    private int customer_id;
    private String customer_name;
    private String basket;
    private int amount;
    private Timestamp registration;

    @Override
    public String toString() {
        return "Customer{" +
                "chat_id=" + chat_id +
                ", customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", basket='" + basket + '\'' +
                ", amount=" + amount +
                ", registration=" + registration +
                '}';
    }
}
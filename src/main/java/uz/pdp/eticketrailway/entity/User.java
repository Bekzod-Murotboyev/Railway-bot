package uz.pdp.eticketrailway.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.eticketrailway.utils.enums.BotState;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    String name;

    @Column(unique = true)
    String phoneNumber;


    @Column(unique = true)
    String username;

    @Column(unique = true,nullable = false)
    String chatId;

    @Enumerated(value = EnumType.STRING)
    BotState state;

    String fromRegion;

    String toRegion;

    LocalDate departureDate;

    String trainNumber;

    String carNumber;



    @CreationTimestamp
    LocalDateTime createdDate;

    boolean active;


    public User(String name, String phoneNumber, String username, String chatId, BotState state) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.chatId = chatId;
        this.state = state;
        this.active=true;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setState(BotState state) {
        this.state = state;
        return this;
    }

    public User setFromRegion(String fromRegion) {
        this.fromRegion = fromRegion;
        return this;
    }

    public User setToRegion(String toRegion) {
        this.toRegion = toRegion;
        return this;
    }

    public User setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public User setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
        return this;
    }

    public User setCarNumber(String carNumber) {
        this.carNumber = carNumber;
        return this;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }
}

package uz.pdp.eticketrailway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.eticketrailway.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByChatId(String chatId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByChatId(String chatId);

    boolean existsByPhoneNumberAndChatIdNot(String phoneNumber, String chatId);



}

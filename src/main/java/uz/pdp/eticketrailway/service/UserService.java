package uz.pdp.eticketrailway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.eticketrailway.entity.User;
import uz.pdp.eticketrailway.payload.ApiResponse;
import uz.pdp.eticketrailway.repository.UserRepository;
import uz.pdp.eticketrailway.utils.enums.BotState;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static uz.pdp.eticketrailway.utils.enums.BotState.MAIN_MENU_SEND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse getAll() {
        return new ApiResponse(true, "Success", userRepository.findAll());
    }

    public User findByChatId(String chatId) {
        return userRepository.findByChatId(chatId).orElse(null);
    }

    public User save(User user) {
        return userRepository.existsByChatId(user.getChatId()) ? null : userRepository.save(user);
    }

    public User savePhoneNumber(String chatId, String phone) {
        if (userRepository.existsByPhoneNumberAndChatIdNot(phone, chatId)) return null;
        Optional<User> optionalUser = userRepository.findByChatId(chatId);
        return optionalUser.map(user -> userRepository.save(user.setPhoneNumber(phone))).orElse(null);
    }

    public User getAndCheck(Message message) {
        Optional<User> optionalUser = userRepository.findByChatId(message.getChatId().toString());
        var from = message.getFrom();
        return optionalUser.orElseGet(() -> userRepository.save(
                new User(from.getFirstName(), null,
                        from.getUserName(), message.getChatId().toString(), MAIN_MENU_SEND)));
    }

    public void saveUserData(String chatId, BotState state) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setState(state)));
    }


    public void saveFromRegion(String chatId, String fromRegion) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setFromRegion(fromRegion)));
    }

    public void saveToRegion(String chatId, String toRegion) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setToRegion(toRegion)));
    }

    public String getFromRegion(String chatId) {
        return userRepository.findByChatId(chatId).map(User::getFromRegion).orElse(null);
    }

    public void saveDate(String chatId, LocalDate date) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setDepartureDate(date)));
    }

    public void saveTrainNumber(String chatId, String trainNumber) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setTrainNumber(trainNumber)));
    }

    public void saveCarNumber(String chatId, String carNumber) {
        userRepository.findByChatId(chatId).ifPresent(user -> userRepository.save(user.setCarNumber(carNumber)));
    }

    public boolean checkRegister(String chatId) {
        Optional<User> optionalUser = userRepository.findByChatId(chatId);
        return optionalUser.isPresent() && optionalUser.get().getPhoneNumber() != null;
    }

    public ApiResponse deleteByChatId(String chatId) {
        Optional<User> optionalUser = userRepository.findByChatId(chatId);
        if(!optionalUser.isPresent())
            return new ApiResponse(false,"Not found");
        User user = optionalUser.get();
        userRepository.save(user.setActive(!user.isActive()));
        return new ApiResponse(true,"Success");
    }
}

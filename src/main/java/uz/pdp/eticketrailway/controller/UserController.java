package uz.pdp.eticketrailway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticketrailway.service.UserService;

import static uz.pdp.eticketrailway.utils.interfaces.Url.URL_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_USER)
public class UserController {

    private final UserService userService;

    @GetMapping
    public HttpEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping
    public HttpEntity<?> editUser(@RequestParam String chatId){
        return ResponseEntity.ok(userService.deleteByChatId(chatId));
    }
}

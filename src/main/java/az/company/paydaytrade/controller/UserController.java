package az.company.paydaytrade.controller;

import az.company.paydaytrade.dto.request.UserRequest;
import az.company.paydaytrade.dto.response.Response;
import az.company.paydaytrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public Response signUp(@RequestBody UserRequest userRequest){
        return userService.signUp(userRequest);
    }

    @GetMapping("/confirm-account")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView,@RequestParam("token") String confirmationToken){
        return userService.confirmUserAcoount(modelAndView,confirmationToken);
    }

    @GetMapping("/login")
    public Response login(@RequestParam String identity,@RequestParam String password,@RequestParam String status){
        return userService.checkLogin(identity,password,status);
    }

    @PutMapping("/addMoney")
    public Response addMoney(@RequestParam String username,@RequestParam String amount){
        return userService.addMoney(username,amount);
    }

    @PutMapping("/withdrawMoney")
    public Response withdrawMoney(@RequestParam String username,@RequestParam String amount){
        return userService.withdrawMoney(username,amount);
    }


}

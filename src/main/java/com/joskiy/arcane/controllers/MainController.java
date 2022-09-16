package com.joskiy.arcane.controllers;

import com.joskiy.arcane.dto.UserDto;
import com.joskiy.arcane.dto.WasteDto;
import com.joskiy.arcane.models.WasteData;
import com.joskiy.arcane.repo.UsersDataRepository;
import com.joskiy.arcane.repo.WasteDataRepository;
import com.joskiy.arcane.services.MainServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainServices mainServices;

    private final WasteDataRepository wasteDataRepository;

    private final UsersDataRepository usersDataRepository;
    private List<String> user = new ArrayList<>();

    private MainController mainController;

    @GetMapping
    public String home1(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/info")
    public String info(Model model) {
        mainServices.info(model);
        return "info";
    }

    @GetMapping("/show")
    public String show(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy, @RequestParam Optional<String> sortType, Model model) {
        if (!model.containsAttribute("wastes")) {
            model = mainServices.show(page, sortBy, sortType, model);
        }
        model.addAttribute("user", this.user);
        model.addAttribute("allUsers", usersDataRepository.findAll());

        return "show";
    }

    @PostMapping("/show")
    public String showUpdate(WasteDto wasteDto, Model model) throws Exception {
        mainServices.showUpdate(wasteDto, wasteDto.getId(), model);
        return "redirect:show";
    }

    @PostMapping("/delete")
    public String delete(WasteDto wasteDto, Model model) {
        mainServices.delete(wasteDto, model);
        return "redirect:show";
    }

    @PostMapping("unlogin")
    public String unlogin(Model model) {
        user = mainServices.unlogin(this.user, model);
        return "redirect:add";
    }

    @GetMapping("/add")
    public String addData(Model model) {
        Iterable<WasteData> waste_data = wasteDataRepository.findAll();
        model.addAttribute("user", this.user);
        model.addAttribute("wastes", waste_data);
        return "add";
    }

    @PostMapping("/add")
    public String postAddData(WasteDto wasteDto, Model model) {
        mainServices.postAddData(wasteDto, model);
        return "redirect:add";
    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println(username + " " + password);
        user = mainServices.userLogin(username, password, model);
        model.addAttribute("user", this.user);

        if (!user.isEmpty()){
            return "redirect:show";
        }
        else {
            return "redirect:/loginerror";
        }

    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String userReg(UserDto userDto, Model model) {
        String result = mainServices.userReg(userDto, model);
        if (result.equals("false")) return "redirect:/registrationerror";
        return "redirect:add";
    }

    @GetMapping("/registrationerror")
    public String registrationerror(Model model) {
        return "registrationerror";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }


//    @PostMapping("/show/my")
//    public String getOnly(boolean getonlymine, Model model) {
//        model = mainServices.getOnlyMine(getonlymine, model);
//        show(model);
//        return "/show";
//    }
}
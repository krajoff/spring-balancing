package com.example.balancing.controllers.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebSliderController {

    @GetMapping("/slider")
    public String showSliderPage(Model model) {
        return "slider/slider";
    }

//    @PostMapping("/slider")
//    public String processSliderValue(@RequestParam("slider")
//                                         int sliderValue, Model model) {
//        // Здесь вы можете использовать значение слайдера, которое пришло из формы
//        model.addAttribute("sliderValue", sliderValue);
//        System.out.println(sliderValue);
//        return "result";
//    }

    @PostMapping("/slider/slider")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8080")
    public  HttpStatus updateSliderValue(@RequestBody String sliderValue) {
        // Здесь вы можете обработать полученное значение слайдера
        System.out.println("Получено значение слайдера: " + sliderValue);
        // Возвращаем ответ клиенту (в данном случае, можно вернуть просто сообщение об успешной обработке)
        return HttpStatus.OK;
    }
}
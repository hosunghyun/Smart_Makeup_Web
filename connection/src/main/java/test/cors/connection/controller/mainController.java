package test.cors.connection.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Getter;
import lombok.Setter;
import test.cors.connection.connect.dataSendService;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class mainController {

    @GetMapping("/slider")
    public String getSliderPage() {
        return "slider"; // templates/slider.html을 반환
    }

    @PostMapping("/slider")
    public void receiveSliderValue(@RequestBody SliderValue sliderValue) {
        dataSendService.sendVariable(sliderValue.getValue(), "/generate");
        System.out.println("Received slider value: " + sliderValue.getValue());
    }

    @Getter
    @Setter
    public static class SliderValue {
        private String value;
    }
}
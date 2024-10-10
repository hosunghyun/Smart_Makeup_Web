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

    @GetMapping("/makeup")
    public String getSliderPage() {
        return "makeup";// templates/makeup.html을 반환
    }

    @PostMapping("/slider")
    public String getSliderValue(@RequestBody SliderValue sliderValue) {
        dataSendService.sendIntVariable(sliderValue.getOpacity(), "/slider");
        System.out.println("Received slider value: " + sliderValue.getOpacity());
        return "makeup";
    }

    @PostMapping("/btnColor")
    public String getBtnColor(@RequestBody BtnValue btnValue) {
        dataSendService.sendStringVariable(btnValue.getHex(), "/btnColor");
        System.out.println("Received btnColor : " + btnValue.getHex());
        return "makeup";
    }

    @Getter
    @Setter
    public static class SliderValue {
        private String opacity;
    }

    @Getter
    @Setter
    public static class BtnValue {
        private String hex;
    }
}
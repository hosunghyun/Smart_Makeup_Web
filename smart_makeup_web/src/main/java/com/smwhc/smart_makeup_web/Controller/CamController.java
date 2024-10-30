package com.smwhc.smart_makeup_web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smwhc.smart_makeup_web.WebCam.DataSendService;
import com.smwhc.smart_makeup_web.WebCam.PythonRunner;

import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class CamController {
    private PythonRunner pythonRunner = new PythonRunner();
    private boolean pythonServerRunning = false;

    // Fast API 러너 초기화
    public CamController(PythonRunner pythonRunner) {
        this.pythonRunner = pythonRunner;
    }

    public void setPythonServerRunning(boolean isPythonServerRunning) {
        this.pythonServerRunning = isPythonServerRunning;
    }

    @PostMapping("/practice")
    public String practiceServer() {
        int port = 8080; // 포트설정

        // Fast API가 실행중이 아닌경우 서버 실행
        if (!pythonServerRunning) {
            setPythonServerRunning(true);
            pythonRunner.startPythonServer(port);
            System.out.println("Python 서버가 시작되었습니다!");
        } else {
            System.out.println("Python 서버는 이미 실행 중입니다!");
        }
        return "makeup";
    }

    @PostMapping("/shutdown") // 서버 종료를 위한 경로
    public String shutdownServer() {
        if (pythonServerRunning) {
            System.out.println("Python 서버가 종료하러 드가자!");
            setPythonServerRunning(false);
            System.out.println("Python 서버가 종료되었습니다!");
            DataSendService.sendPostSignal("/shutdown");
        } else {
            System.out.println("Python 서버는 실행되고 있지 않습니다.");
        }
        return "makeup";
    }
// Post 요청
    @PostMapping("/FdSlider")
    public String getFdSliderValue(@RequestBody SliderValue sliderValue) {
        DataSendService.sendIntVariable(sliderValue.getOpacity(), "/FdSlider");
        System.out.println("Received FdSlider: " + sliderValue.getOpacity());
        return "makeup";
    }

    // Post 요청
    @PostMapping("/FdBtnColor")
    public String getFdBtnColorValue(@RequestBody BtnValue btnValue) {
        DataSendService.sendStringVariable(btnValue.getHex(), "/FdBtnColor");
        System.out.println("Received FdBtnColor : " + btnValue.getHex());
        return "makeup";
    }

    // Post 요청
    @PostMapping("/LipSlider")
    public String getLipSliderValue(@RequestBody SliderValue sliderValue) {
        DataSendService.sendIntVariable(sliderValue.getOpacity(), "/LipSlider");
        System.out.println("Received LipSlider: " + sliderValue.getOpacity());
        return "makeup";
    }

    // Post 요청
    @PostMapping("/LipBtnColor")
    public String getLipBtnColorValue(@RequestBody BtnValue btnValue) {
        DataSendService.sendStringVariable(btnValue.getHex(), "/LipBtnColor");
        System.out.println("Received LipBtnColor : " + btnValue.getHex());
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

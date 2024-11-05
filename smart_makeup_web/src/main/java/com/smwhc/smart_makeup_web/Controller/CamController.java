package com.smwhc.smart_makeup_web.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smwhc.smart_makeup_web.Makeup.MakeUp;
import com.smwhc.smart_makeup_web.Makeup.MakeUpDTO;
import com.smwhc.smart_makeup_web.Makeup.MakeUpService;
import com.smwhc.smart_makeup_web.Member.Member;
import com.smwhc.smart_makeup_web.Member.MemberService;
import com.smwhc.smart_makeup_web.Product_Category.ProductCategory;
import com.smwhc.smart_makeup_web.Product_Category.ProductCategoryService;
import com.smwhc.smart_makeup_web.WebCam.DataSendService;
import com.smwhc.smart_makeup_web.WebCam.PythonRunner;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class CamController {
    private PythonRunner pythonRunner = new PythonRunner();
    private boolean pythonServerRunning = false;

    @Autowired
    private final DataSendService dataSendService;
    private final MemberService memberService;
    private final MakeUpService makeUpService;
    private final ProductCategoryService productCategoryService;

    public CamController(PythonRunner pythonRunner, DataSendService dataSendService, MemberService memberService, MakeUpService makeUpService, ProductCategoryService productCategoryService) {
        // Fast API 러너 초기화
        this.pythonRunner = pythonRunner;
        this.dataSendService = dataSendService;
        this.memberService = memberService;
        this.makeUpService = makeUpService;
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/practice")
    public ResponseEntity<String> practiceServer() {
        Integer port = 8080; // 포트설정
        String result;

        // Fast API가 실행중이 아닌경우 서버 실행
        if (!pythonServerRunning) {
            pythonServerRunning = true;
            pythonRunner.startPythonServer(port);
            result = "success";
            System.out.println("Python 서버가 시작되었습니다!");
        } else {
            result = "already";
            System.out.println("Python 서버는 이미 실행 중입니다!");
        }
        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    @PostMapping("/shutdown") // 서버 종료를 위한 경로
    public ResponseEntity<String> shutdownServer() {
        String result;
        if (pythonServerRunning) {
            pythonServerRunning = false;
            dataSendService.sendPostSignal("/shutdown");
            result = "success";
        } 
        else {
            result = "already";
            System.out.println("Python 서버는 실행되고 있지 않습니다.");
        }

        return ResponseEntity.status(200).body(result);     // 결과를 반한
    }

    // Post 요청
    @PostMapping("/Color")
    public String ColorValue(@RequestParam("whatBtn") String whatBtn, @RequestBody MakeUpDTO makeUpDTO) {
        // 화장 부위가 파운데이션이라면
        if (whatBtn.equals("Fd") && !makeUpDTO.getColor_code().isEmpty()) {
            // 파운데이션에서 색상인 경우
            dataSendService.sendStringVariable(makeUpDTO.getColor_code(), "/FdBtnColor");
        }
        // 화장 부위가 립이라면
        else if (whatBtn.equals("Lip") && !makeUpDTO.getColor_code().isEmpty()) {
            dataSendService.sendStringVariable(makeUpDTO.getColor_code(), "/LipBtnColor");
        }

        return "makeup";
    }

    @PostMapping("/Slider")
    public String SliderValue(@RequestParam("whatBtn") String whatBtn, @RequestBody MakeUpDTO makeUpDTO) {
        if(whatBtn.equals("Fd") && makeUpDTO.getOpacity() != null) {
            dataSendService.sendIntVariable(makeUpDTO.getOpacity(), "/FdSlider");
        }
        else if(whatBtn.equals("Lip") && makeUpDTO.getOpacity() != null) {
            dataSendService.sendIntVariable(makeUpDTO.getOpacity(), "/LipSlider");
        }
        
        return "makeup";
    }

    @PostMapping("/savemakeup")
    public String savemakeup(@RequestBody MakeUpDTO makeUpDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        MakeUp makeUp = new MakeUp();
        ProductCategory category = productCategoryService.findById(makeUpDTO.getCategory());

        Member member = memberService.findById(currentUsername);
        makeUp.setMember(member);
        makeUp.setNumber(makeUpDTO.getNumber());
        makeUp.setCategory(category);
        makeUp.setColor_code(makeUpDTO.getColor_code());
        makeUp.setOpacity(makeUpDTO.getOpacity());

        makeUpService.savemakeup(makeUp);
        return "makeup";
    }

    @PostMapping("/loadmakeup")
    public ResponseEntity<List<MakeUpDTO>> postMethodName(@RequestBody MakeUpDTO makeUpDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        Member member = memberService.findById(currentUsername);
        List<MakeUp> makeUp = makeUpService.findByMember(member);
        List<MakeUpDTO> makeUps = new ArrayList<>();        
        
        for(MakeUp makeup : makeUp) {
            if(makeup.getNumber() == makeUpDTO.getNumber()) {
                MakeUpDTO makeUpDTOs = new MakeUpDTO();
                makeUpDTOs.setCategory(makeup.getCategory().getCategory());
                makeUpDTOs.setColor_code(makeup.getColor_code());
                makeUpDTOs.setOpacity(makeup.getOpacity());
                makeUps.add(makeUpDTOs);
            }
        }

        return ResponseEntity.status(200).body(makeUps);     // 결과를 반한
    }
    
}
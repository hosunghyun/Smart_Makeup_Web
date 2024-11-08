package com.smwhc.smart_makeup_web.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

    // 화장할 때 투명도를 조절하면 실행되는 부분
    @PostMapping("/Slider")
    public String SliderValue(@RequestParam("whatBtn") String whatBtn, @RequestBody MakeUpDTO makeUpDTO) {  // 어느 부위(파운데이션, 립, 아이라인) 인지 구분하게 도와주는 부분과 슬라이더값
        if(whatBtn.equals("Fd") && makeUpDTO.getOpacity() != null) {    // 파운데이션이고 투명도가 null이 아니라면 아래 실행
            dataSendService.sendIntVariable(makeUpDTO.getOpacity(), "/FdSlider");
        }
        else if(whatBtn.equals("Lip") && makeUpDTO.getOpacity() != null) {      // 립이고 투명도가 null이 아니라면 아래 실행
            dataSendService.sendIntVariable(makeUpDTO.getOpacity(), "/LipSlider");
        }
        
        return "makeup";
    }

    // 화장 정보를 저장하기 위해 저장하기 버튼을 클릭했을 때 실행되는 부분
    @PostMapping("/savemakeup")
    public String savemakeup(@RequestBody MakeUpDTO makeUpDTO) {    // 파운데이션, 립, 아이라인을 한번에 받을 수 없고 각각 한번씩 실행된다.
        // 저장하기 전에 사용자 정보를 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 화장 정보를 저장하기 위해 담아둘 객체 생성
        MakeUp makeUp = new MakeUp();

        // 화장 정보에는 화장품의 종류가 들어가니 해당 정보를 추가하기 위해 화장품 종류 객체 생성
        ProductCategory category = productCategoryService.findById(makeUpDTO.getCategory());

        // 화장품 저장할 때 누가 저장했는지 확인하기 위해 현재 사용자 정보를 바탕으로 Member 객체 데이터베이스에서 호출하기
        Member member = memberService.findById(currentUsername);    

        // 생성된 객체에 입력받은 데이터를 저장
        makeUp.setMember(member);
        makeUp.setNumber(makeUpDTO.getNumber());
        makeUp.setCategory(category);
        makeUp.setColor_code(makeUpDTO.getColor_code());
        makeUp.setOpacity(makeUpDTO.getOpacity());

        // 객체를 데이터베이스에 저장하기 위해서 서비스에 함수에 객체 전달
        makeUpService.savemakeup(makeUp);
        return "makeup";
    }

    // 저장된 화장 데이터를 호출하기 위해 불러오기 버튼을 클릭됐을 때 실행되는 부분 
    @PostMapping("/loadmakeup")
    public ResponseEntity<List<MakeUpDTO>> postMethodName(@RequestBody MakeUpDTO makeUpDTO) {   // 어느 버튼을 클릭했는지 확인하기 위해 DTO로 전달 받는다.
        // 화장 정보를 호출하기 위해서는 누가 호출했는지 필요
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // 사용자 아이디

        // 현재 로그인된 사용자를 데이터베이스에서 찾아서 객체로 생성
        Member member = memberService.findById(currentUsername);

        // 사용자마다 여러 화장 정보를 저장하기 때문에 리스트 형식으로 전부 호출
        List<MakeUp> makeUp = makeUpService.findByMember(member);

        // 호출된 화장 데이터 중에서 조건에 맞는 화장 정보를 전달하기 위해 저장하는 ArrayList 인데 파운데이션, 립, 아이라인을 한 번에 보낼 수 없어 각각 3개를 DTO 형식으로 생성해서 배열로 묶고 데이터 전달.
        List<MakeUpDTO> makeUps = new ArrayList<>();        
        
        // 조건에 맞는 데이터를 찾기 위해 반복문 실행
        for(MakeUp makeup : makeUp) {
            if(makeup.getNumber() == makeUpDTO.getNumber()) {   // 어느 버튼인지 확인해서 같다면 객체를 생성하고 리스트에 저장
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
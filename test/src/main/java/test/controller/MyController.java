package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
// import org.springframework.web.servlet.view.RedirectView;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @Controller
@RestController
public class MyController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/senddata")
    public String sendData(@RequestParam String value) {
        System.out.println("보낼예정 :" + value);

        // Flask 서버로 데이터 전송
        String flaskUrl = "http://localhost:8080/api/data";
        restTemplate.postForObject(flaskUrl, value, String.class); // POST 요청으로 Flask 서버에 데이터 전송

        System.out.println("보냄: " + value);
        System.out.println("Received value: " + value);
        // Redirect to index.html

        return "redirect:/index";
        // return new RedirectView("/index"); // "index"는 index.html이 위치한 경로
        // return ResponseEntity.ok("Value received: " + value);
    }

    @GetMapping("/api/senddata?value={data}")
    public String sendValue(@RequestParam String value) {
        System.out.println("보낼예정 :" + value);

        // Flask 서버로 데이터 전송
        String flaskUrl = "http://localhost:8080/api/data";
        restTemplate.postForObject(flaskUrl, value, String.class); // POST 요청으로 Flask 서버에 데이터 전송

        System.out.println("보냄: " + value);
        System.out.println("Received value: " + value);
        // Redirect to index.html
        return "redirect:/index"; // "index"는 index.html이 위치한 경로
        // return ResponseEntity.ok("Value received: " + value);
    }

}

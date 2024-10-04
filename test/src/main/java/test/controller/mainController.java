package test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class mainController {
    @GetMapping({ "/", "/index" })
    public String index() {
        return "index";
    }

}

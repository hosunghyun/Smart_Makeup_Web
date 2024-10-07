package test.cors.connection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        dataSendService.sendVariable(sliderValue.getValue());
        System.out.println("Received slider value: " + sliderValue.getValue());
    }

    public static class SliderValue {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
} // @PostMapping("/slider")
  // public ResponseEntity<String> receiveSliderValue(@RequestBody SliderValue
  // sliderValue) {
  // System.out.println("Received slider value: " + sliderValue.getValue());

// String fastApiUrl = "http://localhost:8000/your-endpoint";
// HttpHeaders headers = new HttpHeaders();
// headers.set("Content-Type", "application/json");

// HttpEntity<YourDataObject> request = new HttpEntity<>(data, headers);

// ResponseEntity<String> response = restTemplate.exchange(fastApiUrl,
// HttpMethod.POST, request, String.class);
// }

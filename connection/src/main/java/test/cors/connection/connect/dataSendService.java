package test.cors.connection.connect;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class dataSendService {

    private static String fastApiURL = "http://127.0.0.1:8080/generate";

    public static void sendVariable(String requestValue) {
        RestTemplate restTemplate = new RestTemplate();

        // 통신 시에는 JSON으로 변환 필요
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // YourDataObject 형태로 데이터 객체 생성
        SendDataObject dataObject = new SendDataObject();
        dataObject.setValue(requestValue);

        HttpEntity<SendDataObject> requestEntity = new HttpEntity<>(dataObject, headers);

        // 요청 로그 출력
        System.out.println("Sending request: " + dataObject.getValue());

        restTemplate.exchange(fastApiURL, HttpMethod.POST, requestEntity, Void.class);
    }

    // public static void sendVariable(String request) {
    // RestTemplate restTemplate = new RestTemplate();

    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);

    // HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

    // restTemplate.exchange(fastApiURL, HttpMethod.POST, requestEntity,
    // Void.class);
    // }
}

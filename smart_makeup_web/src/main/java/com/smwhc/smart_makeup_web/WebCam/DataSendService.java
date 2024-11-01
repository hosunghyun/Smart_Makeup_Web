package com.smwhc.smart_makeup_web.WebCam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class DataSendService {
    private static String fastApiPort = "http://127.0.0.1:8080";

    public void sendPostSignal(String postURL) {
        String fastApiURL = fastApiPort + postURL; // 전송할 URL 정의
        RestTemplate restTemplate = new RestTemplate(); // RESTful에 사용할 객체 정의
        // 통신을 위해, JSON형태로 변환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 빈 요청 객체 생성 (본문 없이 보내기)
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        // 요청 로그 출력
        System.out.println("전송 경로: " + postURL + ", Sending POST request");

        try {
            // POST 요청 전송
            ResponseEntity<Void> response = restTemplate.exchange(fastApiURL, HttpMethod.POST, requestEntity,
                    Void.class);
            // 응답 상태 로그 출력
            System.out.println("응답 상태: " + response.getStatusCode());
        } catch (RestClientException e) {
            // 예외 로그 출력
            System.err.println("POST 요청 예외 로그 출력: " + e.getMessage());
            System.err.println(
                    "I/O error on POST request for \"http://127.0.0.1:8080/shutdown\": Connection reset 인 경우: 문제 없습니다.");
        }
    }

    // str 보내기
    public void sendStringVariable(String requestValue, String PostURL) {
        String fastApiURL = fastApiPort + PostURL; // 전송할 URL 정의

        RestTemplate restTemplate = new RestTemplate(); // RESTful에 사용할 객체 정의

        // 통신을 위해, JSON형태로 변환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // YourDataObject 형태로 데이터 객체 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestValue, headers);

        restTemplate.exchange(fastApiURL, HttpMethod.POST, requestEntity, Void.class);

    }

    // int 보내기
    public void sendIntVariable(Integer requestIntValue, String PostURL) {
        String fastApiURL = fastApiPort + PostURL; // 전송할 URL 정의

        RestTemplate restTemplate = new RestTemplate(); // RESTful에 사용할 객체 정의

        // 통신을 위해, JSON형태로 변환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // YourDataObject 형태로 데이터 객체 생성
        HttpEntity<Integer> requestEntity = new HttpEntity<>(requestIntValue, headers);

        restTemplate.exchange(fastApiURL, HttpMethod.POST, requestEntity, Void.class);
    }

    // String을 int로 변환하는 메서드
    public int convertStringToInt(String numberString) {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 정수 형식입니다: " + numberString);
            return 0; // 또는 다른 에러 코드나 예외 처리를 할 수 있습니다.
        }
    }
}

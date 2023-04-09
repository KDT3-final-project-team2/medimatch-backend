package com.project.finalproject.applicant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.dto.request.OpenAIRequestDTO;
import com.project.finalproject.applicant.dto.response.OpenAIResponseDTO;
import com.project.finalproject.applicant.dto.ClassifierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApplicantIntentClassifier {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public ClassifierDTO davinciRequest(String message, Long applicantId) throws Exception {
        System.out.println("davinci-003 called");
        // Construct request to OpenAI API
        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "The following message is in Korean: \"" + message + "\". ";
        prompt += "choose the appropriate action from below and extract any required info: ";
        prompt += "If the message is asking about what to do, reply action with '뭐 해야해'. ";

        prompt += "If the message is about 채용 공고 검색, reply action with '채용공고 검색하기'. ";
        prompt += "If the message is about 채용 공고 확인, reply action with '채용공고 확인하기'. ";
        prompt += "If the message is about 회사 지원, reply action with '채용공고 지원하기'. ";
        prompt += "If the message is about 회사 지원 취소, reply action with '채용공고 지원 취소하기'. ";
        prompt += "If the message is about 지원한 회사 정보, reply action with '지원한 회사 보기'. ";
        prompt += "If the message is about 지원했던 회사 결과, reply action with '합격한 회사 보기'. ";
        prompt += "If the message is about 지원한 곳, reply action with '합격한 회사 보기'. ";

        prompt += "If the message is about 내 정보 보기, reply action with '내 정보 보기'. ";
        prompt += "If the message is about 내 정보 수정, reply action with '내 정보 보기'. ";
        prompt += "If the message is about 비밀번호, reply action with '비밀번호 변경하기'. ";

        prompt += "If the message is about 이력서 등록, reply action with '이력서 등록하기'. ";
        prompt += "If the message is about 이력서 보기, reply action with '이력서 조회하기'. ";
        prompt += "If the message is about 이력서 수정, reply action with '이력서 수정하기'. ";
        prompt += "If the message is about 이력서 삭제, reply action with '이력서 삭제하기'. ";
        prompt += "Action: (strictly only result among replies!!)";
        prompt += "Info: (strictly only name or numeric variable!!)";


        int maxTokens = 50;
        double temperature = 0.7;
        OpenAIRequestDTO openAIRequestDTO = new OpenAIRequestDTO(prompt, maxTokens, temperature);

        String payload = objectMapper.writeValueAsString(openAIRequestDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        // Send request and parse response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        OpenAIResponseDTO response = objectMapper.readValue(responseEntity.getBody(), OpenAIResponseDTO.class);
        String intent = response.getChoices().get(0).getText().trim();
        System.out.println(response.getChoices().toString());

        System.out.println("message : " + message);
        System.out.println("response :" + responseEntity.getBody());

        String[] responseLines = intent.split("\n");
        intent = responseLines[0].split(":", 2)[1].trim();
        String info = responseLines[1].split(":", 2)[1].trim();
        if (info.equalsIgnoreCase("null") ||
                info.equalsIgnoreCase("none") ||
                info.equalsIgnoreCase("n/a")) {
            info = null;
        }

        System.out.println("intent : " + intent);
        System.out.println("info : " + info);
        return new ClassifierDTO(intent, info);
    }
}


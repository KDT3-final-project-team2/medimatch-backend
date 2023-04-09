package com.project.finalproject.company.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.dto.ClassifierDTO;
import com.project.finalproject.applicant.dto.request.OpenAIRequestDTO;
import com.project.finalproject.applicant.dto.response.OpenAIResponseDTO;
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
public class CompanyIntentClassifier {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public ClassifierDTO davinciRequest(String message) throws Exception {
        System.out.println("company: davinci-003 called");
        // Construct request to OpenAI API
        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "The following message is in Korean: \"" + message + "\". ";
        prompt += "choose the appropriate action STRICTLY from below: ";

        prompt += "If the message is about 채용 공고 작성, categorize action with '채용공고 등록하기'. ";
        prompt += "If the message is about 채용 공고 등록, categorize action with '채용공고 등록하기'. ";
        prompt += "If the message is about 채용 공고 만들기, categorize action with '채용공고 등록하기'. ";

        prompt += "If the message is about 채용 공고 조회, reply action with '채용공고 조회'. ";
        prompt += "If the message is about 채용 공고 찾기, reply action with '채용공고 조회'. ";
        // TODO 전체 조회인지, 단건 조회인지(제목 키워드)

        prompt += "If the message is about 채용 공고 수정, reply action with '채용공고 수정'. ";
        prompt += "If the message is about 채용 공고 삭제, reply action with '채용공고 수정'. ";
        prompt += "If the message is about 채용 공고 마감, reply action with '채용공고 수정'. ";

        prompt += "If the message is about 내 정보 수정, reply action with '내 정보 수정'. ";
        prompt += "If the message is about 내 정보, reply action with '내 정보 수정'. ";
        prompt += "If the message is about 마이 페이지, reply action with '내 정보 수정'. ";
        prompt += "If the message is about 비밀번호 변경, reply action with '내 정보 수정'. ";

        prompt += "If the message is about 지원자 조회, reply action with '지원자 조회'. ";
        prompt += "If the message is about 지원자 현황, reply action with '지원자 조회'. ";

        prompt += "If the message is about 지원자 통계, reply action with '지원자 통계'. ";

        prompt += "Action: (strictly only result among the replies!! exclude 'reply action with')";


        int maxTokens = 50;
        double temperature = 0.7;
        OpenAIRequestDTO openAIRequestDTO = new OpenAIRequestDTO(prompt, maxTokens, temperature);

        String payload = objectMapper.writeValueAsString(openAIRequestDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        // Send request and parse response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        OpenAIResponseDTO response = objectMapper.readValue(responseEntity.getBody(), OpenAIResponseDTO.class);
        String intent = response.getChoices().get(0).getText().trim().trim();
//        intent = intent.split(":", 2)[1].trim();
        System.out.println(response.getChoices().toString());

        System.out.println("message : " + message);
        System.out.println("response :" + responseEntity.getBody());
        System.out.println(intent);
        String info = null;

        System.out.println("intent : " + intent);
        return new ClassifierDTO(intent, info);
    }
}


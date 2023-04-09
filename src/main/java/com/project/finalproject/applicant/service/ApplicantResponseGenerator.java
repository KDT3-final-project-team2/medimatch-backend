package com.project.finalproject.applicant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finalproject.applicant.dto.request.ChatMessageRequestDTO;
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
public class ApplicantResponseGenerator {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public String davinciResponse(String message) throws Exception {
        System.out.println("davinci-003 called");
        // Construct request to OpenAI API
        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = "The following message is in Korean: \"" + message + "\". ";
        prompt += "make a suitable rephrase the message.";
        prompt += "result: (strictly only result!!)";
        int maxTokens = 50;
        double temperature = 0.7;
        OpenAIRequestDTO openAIRequestDTO = new OpenAIRequestDTO(prompt, maxTokens, temperature);

        String payload = objectMapper.writeValueAsString(openAIRequestDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        // Send request and parse response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        OpenAIResponseDTO response = objectMapper.readValue(responseEntity.getBody(), OpenAIResponseDTO.class);
        String result = response.getChoices().get(0).getText().trim();

        System.out.println(response);
        System.out.println(response.getChoices().toString());

        System.out.println("message sent : " + message);
        System.out.println("result :" + result);
        return result;
    }
}


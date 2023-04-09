package com.project.finalproject.company.service.impl;

import com.project.finalproject.applicant.dto.ClassifierDTO;
import com.project.finalproject.applicant.dto.request.SignupRequestDTO;
import com.project.finalproject.applicant.dto.response.ChatMessageResponseDTO;
import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.application.entity.Application;
import com.project.finalproject.application.repository.ApplicationRepository;
import com.project.finalproject.company.dto.*;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.company.exception.CompanyExceptionType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.CompanyIntentClassifier;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.exception.GlobalException;
import com.project.finalproject.global.exception.GlobalExceptionType;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.exception.JobpostException;
import com.project.finalproject.jobpost.exception.JobpostExceptionType;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import com.project.finalproject.signup.dto.CompanySignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final JobpostRepository jobpostRepository;
    private final ApplicationRepository applicationRepository;
    private final JavaMailSender javaMailSender;
    private final CompanyIntentClassifier companyIntentClassifier;

    @Value("${spring.mail.username}")
    private String mailSender;
    @Value("${jobpost.file.path.server}")
    private String JOBPOST_FILE_PATH;
//    @Value("${jobpost.file.path.local}")
//    private String JOBPOST_FILE_PATH;


    @Override
    public String checkEmail(CompanySignupReqDTO companySignupReqDTO){
        String companyEmail = companySignupReqDTO.getCompanyEmail();
        if(companyRepository.findByEmail(companyEmail).isPresent()){
            return "duplicate id";
        }
        else{
            return "success";
        }
    }

    /**
     * 채용 공고 생성
     * @param email : 작성자 이메일
     * @param createRequestDTO : 채용공고에 입력할 내용
     * @param jobpostFile : 채용공고 내용 파일 (pdf)
     * @return
     */
    @Override
    public CompanyJobpostResponse.LongDTO createJobpost(String email,
                                                        CompanyJobpostRequest.CreateDTO createRequestDTO,
                                                        MultipartFile jobpostFile) {
        //사용자 2차 검증
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); //파일 저장 시간
        String fileName = now + "_" + company.getName() + ".pdf"; // 파일명 날짜_회사이름.pdf
        Path serverPath = Paths.get(JOBPOST_FILE_PATH + fileName); // ubuntu path
//
        try{
            //파일 저장
            Files.copy(jobpostFile.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            log.error("fail to store file : name = {}, exception = {}",
                    jobpostFile.getOriginalFilename(),
                    e.getMessage());
            throw new JobpostException(JobpostExceptionType.NOT_FOUND_FILE);
        }

        createRequestDTO.setFilePath(serverPath.toString());
        Jobpost creatJobpost = new Jobpost().createJobpost(createRequestDTO, company);

        return new CompanyJobpostResponse.LongDTO(jobpostRepository.save(creatJobpost));
    }

    /**
     * 기업 본인이 작성한 채용공고 목록 조회
     * @param companyEmail : 기업 email
     * @return 채용 공고 리스트
     */
    @Override
    public List<CompanyJobpostResponse.ShortDTO> showJobpostList(String companyEmail) {

        Company company = companyRepository.findByEmail(companyEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        List<Jobpost> jobposts = jobpostRepository.findByCompanyId(company.getId());

        return jobposts.stream()
                .map(CompanyJobpostResponse.ShortDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 채용 공고 단건 조회
     * @param companyEmail : 회사 이메일
     * @param postId : 게시글 아이디
     * @return 채용 공고 내용 상세 출력
     */
    @Override
    public CompanyJobpostResponse.LongDTO showJobpostDetail(String companyEmail, Long postId) {

        //사용자 확인용
        Company company = companyRepository.findByEmail(companyEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        //postId로 게시글 조회
        Jobpost jobpost = jobpostRepository.findById(postId).orElseThrow(
                () -> new JobpostException(JobpostExceptionType.NOT_FOUND_PAGE)
        );

        return CompanyJobpostResponse.LongDTO.builder()
                .jobpost(jobpost)
                .build();
    }

    /**
     * 채용공고 수정
     * @param email : 채용 공고 수정할 기업 email
     * @param postId : 수정할 게시글 id
     * @param updateRequestDTO : 수정하는데 필요한 데이터
     * @return 수정 후 수정된 jobpostId
     */
    @Override
    public CompanyJobpostResponse.LongDTO updateJobpost(String email, Long postId, CompanyJobpostRequest.UpdateDTO updateRequestDTO, MultipartFile jobpostFile) throws IOException {
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        Jobpost jobpost = jobpostRepository.findById(postId).orElseThrow(
                () -> new JobpostException(JobpostExceptionType.NOT_FOUND_PAGE)
        );

        // 기존 파일 덮어 쓰기
        String filePath = jobpost.getFilepath();
        if(jobpostFile != null) {
            jobpostFile.transferTo(new File(filePath));
        }

        jobpost.updateJobpost(updateRequestDTO, company);

        return new CompanyJobpostResponse.LongDTO( jobpostRepository.save(jobpost) );

    }

    /**
     * 채용공고 폐기 상태 변경
     * @param email 바꾸는 사람 이메일
     * @param jobpostId 바꿀 게시글 아이디
     * @return
     */
    @Override
    public CompanyJobpostResponse.LongDTO deleteJobpost(String email, Long jobpostId) {
        //사용자 2차 검증
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        //변경할 데이터 불러오기
        Jobpost jobpost = jobpostRepository.findById(jobpostId).orElseThrow(
                () -> new JobpostException(JobpostExceptionType.NOT_FOUND_PAGE)
        );

        if(jobpost.getStatus().getStatus().equals("DISCARD")){
            throw new JobpostException(JobpostExceptionType.POSTS_DISCARD_ALREADY);
        }

        //상태 변경하기
        jobpost.changeStatus();

        Jobpost newJobpost = jobpostRepository.save(jobpost);

        return new CompanyJobpostResponse.LongDTO(newJobpost);
    }


    public CompanyApplicationResponse.StatisticsDTO statisticsForApplicationsForCompany(Long companyId) {
        HashMap<String, Integer> applicantAge = new HashMap<>();
        HashMap<String, Integer> applicantGender = new HashMap<>();
        HashMap<String, Integer> applicantEducation = new HashMap<>();
        HashMap<String, Integer> jobpostTitle = new HashMap<>();

        List<Object[]> results = companyRepository.findApplicationsForCompany(companyId);

        for (Object[] row : results){
            //연령
            int age = LocalDate.now().getYear() - Integer.parseInt(row[0].toString().substring(0, 4));
            String ageRange;
            if(age>50){
                ageRange = "50대 이상";
            } else if (age>40){
                ageRange = "40대";
            } else if (age>30){
                ageRange = "30대";
            } else if (age>20){
                ageRange = "20대";
            } else {
                ageRange = "10대";
            }
            String gender = Gender.valueOf(row[1].toString()).getGender();
            String education = ApplicantEducation.valueOf(row[2].toString()).getEducation();
            String title = row[3].toString();

            if (applicantAge.containsKey(ageRange)){
                applicantAge.put(ageRange, applicantAge.get(ageRange)+1);
            } else {
                applicantAge.put(ageRange, 1);
            }
            if (applicantGender.containsKey(gender)){
                applicantGender.put(gender, applicantGender.get(gender)+1);
            } else {
                applicantGender.put(gender, 1);
            }
            if (applicantEducation.containsKey(education)){
                applicantEducation.put(education, applicantEducation.get(education)+1);
            } else {
                applicantEducation.put(education, 1);
            }
            if (jobpostTitle.containsKey(title)){
                jobpostTitle.put(title, jobpostTitle.get(title)+1);
            } else {
                jobpostTitle.put(title, 1);
            }
        }



        return CompanyApplicationResponse.StatisticsDTO.builder()
                .applicantAgeCount(applicantAge)
                .applicantGenderCount(applicantGender)
                .applicantEducationCount(applicantEducation)
                .jobpostTitleCount(jobpostTitle)
                .build();
    }


    /**
     * 지원자 목록 조회
     * @param companyEmail : 기업 회원 이메일
     * @return 지원자 리스트 출력
     */
    @Override
    public List<CompanyApplicationResponse.ApplicantInfoDTO> showApplicantInfo(String companyEmail) {
        Company company = companyRepository.findByEmail(companyEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        List<Application> application = applicationRepository.findAll();

        return application
                .stream()
                .filter(app -> app.getJobpost().getCompany().getId().equals(company.getId()))
                .map(CompanyApplicationResponse.ApplicantInfoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * 지원자 상태 변경
     * @param email : 기업 회원 이메일
     * @param applicationStatusReqDTO : 변경할 상태
     */
    public void changeApplicationStatus(String email, CompanyApplicationRequest.StatusReqDTO applicationStatusReqDTO){
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        Application application = applicationRepository.findById(applicationStatusReqDTO.getApplicationId()).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_APPLICATION)
        );

        Application updateApplication = application.updateStatus(applicationStatusReqDTO);

        applicationRepository.save(updateApplication);

    }

    /**
     * 기업회원 내정보 출력
     * @author : 홍수희
     * @param companyEmail :기업회원 이메일
     * @return 기업회원 정보 출력
     */
    @Override
    public CompanyResponse.InfoDTO showCompanyInfo(String companyEmail) {
        Company company = companyRepository.findByEmail(companyEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        return CompanyResponse.InfoDTO.builder()
                .companyId(company.getId())
                .email(company.getEmail())
                .companyNm(company.getName())
                .contact(company.getContact())
                .regNum(company.getRegNum())
                .companyAddr(company.getAddress())
                .ceoName(company.getRepresentativeName())
                .url(company.getUrl())
                .build();
    }

    /**
     * 기업회원 내정보 수정
     * @author : 홍수희
     * @param companyEmail : 기업회원 이메일
     * @param requestDTO : 수정할 데이터
     * @return 수정한 기업회원 정보 출력
     */
    @Override
    public CompanyResponse.InfoDTO updateCompanyInfo(String companyEmail, CompanyRequest.UpdateInfoDTO requestDTO) {
        Company company = companyRepository.findByEmail(companyEmail).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );

        company.updateData(requestDTO);

        CompanyResponse.InfoDTO updateCompany = new CompanyResponse.InfoDTO(companyRepository.save(company));

        return updateCompany;
    }

    /**
     * 합/불 메일발송
     */
    @Override
    public ResponseDTO sendEmail(EmailReqDTO emailReqDTO) throws MessagingException {
        String email = emailReqDTO.getEmail();
        String title = emailReqDTO.getTitle();
        String content = emailReqDTO.getContent();

        if(email == null){
            throw new GlobalException(GlobalExceptionType.BAD_REQUEST);
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        mimeMessageHelper.setFrom(mailSender);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content);
        javaMailSender.send(mimeMessage);

        return new ResponseDTO(200, true, null, "입력하신 메일로 결과를 발송했습니다.");
    }

    @Override
    public ChatMessageResponseDTO doIntentAction(String message, Long companyId) throws Exception {
        ClassifierDTO classifierDTO = companyIntentClassifier.davinciRequest(message);
        String intent = classifierDTO.getIntent();
        String info = classifierDTO.getInfo();

        String responseMessage=null;
        String page=null;
        String result=null;
        String method=null;
        switch (intent) {
            case "채용공고 등록하기":
                responseMessage = "채용공고를 작성할 수 있는 페이지로 이동시켜드렸습니다.";
                page = "/jobposts";
                method = "POST";
                break;

            case "채용공고 조회":
                responseMessage = "등록한 채용공고 조회 페이지로 이동시켜드렸습니다.";
                page = "/company/jobposts";
                method = "GET";
                break;

            case "채용공고 수정":
                responseMessage = "등록한 채용공고 수정 페이지로 이동시켜드렸습니다.";
                page = "/company/jobposts";
                method = "GET";
                break;

            case "내 정보 수정":
                responseMessage = "내 정보를 수정할 수 있는 페이지로 이동시켜드렸습니다.";
                page = "/company/me";
                method = "PUT";
                break;

            case "지원자 조회":
                responseMessage = "지원자 목록을 볼 수 있는 페이지로 이동시켜 드렸습니다.";
                page = "/company/applications";
                method = "GET";
                break;

            case "지원자 통계":
                responseMessage = "지원자 통계를 볼 수 있는 페이지로 이동시켜 드렸습니다.";
                page = "/company/application/statistics";
                method = "GET";
                break;

            default:
                responseMessage = "제가 멍청하여 이해하지 못했습니다. 원하시는걸 다시 말씀해주세요.";
                page="stay";
                break;
        }
        return new ChatMessageResponseDTO(method, page, responseMessage);
    }
}

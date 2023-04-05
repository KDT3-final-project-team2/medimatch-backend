package com.project.finalproject.company.service.impl;

import com.project.finalproject.applicant.entity.enums.ApplicantEducation;
import com.project.finalproject.applicant.entity.enums.Gender;
import com.project.finalproject.company.dto.ApplicationsForCompanyResponseDTO;
import com.project.finalproject.company.dto.CompanyJobpostRequest;
import com.project.finalproject.company.dto.CompanyJobpostResponse;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.company.exception.CompanyException;
import com.project.finalproject.company.exception.CompanyExceptionType;
import com.project.finalproject.company.repository.CompanyRepository;
import com.project.finalproject.company.service.CompanyService;
import com.project.finalproject.jobpost.entity.Jobpost;
import com.project.finalproject.jobpost.exception.JobpostException;
import com.project.finalproject.jobpost.exception.JobpostExceptionType;
import com.project.finalproject.jobpost.repository.JobpostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final JobpostRepository jobpostRepository;
    @Value("${jobpost.file.path.server}")
    private String JOBPOST_FILE_PATH;
//    @Value("${jobpost.file.path.local}")
//    private String JOBPOST_FILE_PATH;

    /**
     * 채용 공고 생성
     * @param email : 작성자 이메일
     * @param createRequestDTO : 채용공고에 입력할 내용
     * @param jobpostFile : 채용공고 내용 파일 (pdf)
     * @return
     */
    @Override
    public CompanyJobpostResponse.LongDTO createJobpost(String email, CompanyJobpostRequest.CreateDTO createRequestDTO, MultipartFile jobpostFile) throws IOException {
        Company company = companyRepository.findByEmail(email).orElseThrow(
                () -> new CompanyException(CompanyExceptionType.NOT_FOUND_USER)
        );
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filePath = JOBPOST_FILE_PATH + now + "_" + company.getName() + ".pdf"; // 파일명 날짜_회사이름.pdf

        if(jobpostFile.isEmpty()){
            throw new JobpostException(JobpostExceptionType.NOT_FOUND_FILE);
        }
        jobpostFile.transferTo(new File(filePath));

        createRequestDTO.setFilePath(filePath);

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

    public ApplicationsForCompanyResponseDTO statisticsForApplicationsForCompany(Long companyId) {
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



        for (Object[] row : results) {
            System.out.println(row[0] + " " + row[1] + " " + row[2] + " " + row[3]);
            System.out.println(row[0]); //생년월일
            System.out.println(row[1]); //성별
            System.out.println(row[2]); //학력
            System.out.println(row[3]); //채용공고 제목
        }

        return ApplicationsForCompanyResponseDTO.builder()
                .applicantAgeCount(applicantAge)
                .applicantGenderCount(applicantGender)
                .applicantEducationCount(applicantEducation)
                .jobpostTitleCount(jobpostTitle)
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
}

package com.project.finalproject.password.service;

import com.project.finalproject.applicant.entity.Applicant;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.password.dto.PasswordDTO;
import com.project.finalproject.password.repository.ApplicantPasswordRepository;
import com.project.finalproject.password.repository.CompanyPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Transactional
@ConstructorBinding
public class PasswordService {

    private final ApplicantPasswordRepository applicantPasswordRepository;
    private final CompanyPasswordRepository companyPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 로그인한 상태에서 비밀번호 변경
     */
    public ResponseDTO changePassword(PasswordDTO passwordDTO){

        String newPassword = passwordDTO.getPassword();
        String role = passwordDTO.getRole();
        Long id = passwordDTO.getId();

        if(role.equals("USER")){
            Applicant findApplicant = applicantPasswordRepository.findApplicantById(id).orElseThrow();
            findApplicant.changeApplicantPassword(passwordEncoding(newPassword));
            applicantPasswordRepository.save(findApplicant);

            return new ResponseDTO(200, true, null, "비밀번호가 변경되었습니다.");
        }
        else{
            Company findCompany = companyPasswordRepository.findCompanyById(id).orElseThrow();
            findCompany.changeCompanyPassword(passwordEncoding(newPassword));
            companyPasswordRepository.save(findCompany);

            return new ResponseDTO(200, true, null, "비밀번호가 변경되었습니다.");
        }
    }

    /**
     * 로그인하지 않은 상태에서 비밀번호 초기화
     */
    public ResponseDTO resetPassword(PasswordDTO passwordDTO) throws MessagingException {
        String email = passwordDTO.getEmail();
        String name = passwordDTO.getName();
        String resetPassword = getRandomPassword(7);

        StringBuilder text = new StringBuilder();
        text.append("안녕하세요, 메디메치입니다.").append("\n");
        text.append("임시 비밀번호를 다음과 같이 안내드리오니, 로그인 후 반드시 비밀번호를 재설정하시기 바랍니다.").append("\n");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("[메디메치] 임시 비밀번호 안내");


        if(applicantPasswordRepository.existsApplicantByEmailAndName(email, name)){

            Applicant findApplicant = applicantPasswordRepository.findApplicantByEmailAndName(email, name).orElseThrow();
            findApplicant.changeApplicantPassword(passwordEncoding(resetPassword));
            applicantPasswordRepository.save(findApplicant);

            text.append("임시비밀번호 : ").append(resetPassword);
            mimeMessageHelper.setText(text.toString());
            javaMailSender.send(mimeMessage);

            return new ResponseDTO(200, true, null, "초기화 된 비밀번호가 이메일로 발송되었습니다.");
        }
        else if(companyPasswordRepository.existsCompanyByEmailAndName(email, name)){

            Company findCompany = companyPasswordRepository.findCompanyByEmailAndName(email, name).orElseThrow();
            findCompany.changeCompanyPassword(passwordEncoding(resetPassword));
            companyPasswordRepository.save(findCompany);

            text.append("임시비밀번호 : ").append(resetPassword);
            mimeMessageHelper.setText(text.toString());
            javaMailSender.send(mimeMessage);

            return new ResponseDTO(200, true, null, "초기화 된 비밀번호가 이메일로 발송되었습니다.");
        }
        else{
            return new ResponseDTO(400, false, null, "존재하지 않는 사용자입니다.");
        }
    }

    /**
     * 비밀번호 암호화
     */
    private String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }

    /**
     * 임시비밀번호 생성
     */
    private String getRandomPassword(int size){
        return RandomStringUtils.randomAlphanumeric(size);
    }
}

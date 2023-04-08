package com.project.finalproject.searchpost.service;

import com.project.finalproject.applicant.entity.enums.Sector;
import com.project.finalproject.company.entity.Company;
import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.exception.CustomExceptionHandler;
import com.project.finalproject.global.exception.GlobalException;
import com.project.finalproject.global.exception.GlobalExceptionType;
import com.project.finalproject.jobpost.entity.enums.JobpostEducation;
import com.project.finalproject.jobpost.entity.enums.JobpostWorkExperience;
import com.project.finalproject.searchpost.dto.PostReqDTO;
import com.project.finalproject.searchpost.dto.PostResDTO;
import com.project.finalproject.searchpost.repository.SearchCompanyRepository;
import com.project.finalproject.searchpost.repository.SearchPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchPostService {

    private final SearchPostRepository searchPostRepository;
    private final SearchCompanyRepository searchCompanyRepository;

    /**
     * 공고 조건 검색
     */
    public Page<PostResDTO> searchPost(String type, PostReqDTO reqDTO, Pageable pageable) {
        String keyword = reqDTO.getKeyword();

        //검색한 회사가 없는 경우 예외 발생시킴
        //나머지는 값이 없으면 빈 페이지들을 반환
        switch (type) {
            case "직무":
                return searchPostRepository.findAllBySector(Sector.valueOf(keyword), pageable).map(PostResDTO::new);
            case "학력":
                return searchPostRepository.findAllByEducation(JobpostEducation.valueOf(keyword), pageable).map(PostResDTO::new);
            case "경력":
                return searchPostRepository.findAllByExperience(JobpostWorkExperience.valueOf(keyword), pageable).map(PostResDTO::new);
            case "공고제목":
                return searchPostRepository.findAllByTitleContains(keyword, pageable).map(PostResDTO::new);
            case "회사":
                Company findCompany = searchCompanyRepository.findCompanyByName(keyword).orElseThrow(()-> new GlobalException(GlobalExceptionType.NOT_FOUND));
                return searchPostRepository.findAllByCompany(findCompany, pageable).map(PostResDTO::new);
        }
        return null;
    }

        /**
         * 전체 공고 조회
         */
        public Page<PostResDTO> allPosts (Pageable pageable){
            return searchPostRepository.findAll(pageable).map(PostResDTO::new);
        }

        /**
         * 상세 공고 조회
         */
        public ResponseDTO detailPost (Long postId){
            return new ResponseDTO(200, true, searchPostRepository.findById(postId).map(PostResDTO::new).orElseThrow(), "공고 상세조회입니다.");
        }
}

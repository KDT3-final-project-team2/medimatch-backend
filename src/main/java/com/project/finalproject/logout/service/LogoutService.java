package com.project.finalproject.logout.service;

import com.project.finalproject.global.dto.ResponseDTO;
import com.project.finalproject.global.jwt.utils.JwtUtil;
import com.project.finalproject.logout.dto.LogoutReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private final JwtUtil jwtUtil;

    /**
     * 로그아웃
     */
    @Transactional(readOnly = true)
    public ResponseDTO logout(LogoutReqDTO req){
        //req에서 access와 refresh 토큰을 받아서 redis에 넣어준다. 이때 유효기간 설정 필!
        String accessToken = jwtUtil.extractToken(req.getAuthorizationHeader());
        String refreshToken = jwtUtil.extractToken(req.getRefreshHeader());

        Date accessExpirationDate = jwtUtil.getExpiration(accessToken);
        Date refreshExpirationDate = jwtUtil.getExpiration(refreshToken);
        Date current = new Date();

        Instant accessInstant = accessExpirationDate.toInstant();
        Instant refreshInstant = refreshExpirationDate.toInstant();
        Instant currentInstant = current.toInstant();

        stringRedisTemplate.opsForValue().set(accessToken,"AccessToken", Duration.between(currentInstant, accessInstant));
        stringRedisTemplate.opsForValue().set(refreshToken,"RefreshToken",Duration.between(currentInstant, refreshInstant));

        return new ResponseDTO(200, true, null, "로그아웃 성공!");
    }
}

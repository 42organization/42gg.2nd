package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "mangoisqute"; //하드코딩 좋지않음
    private long tokenValidTime =30 * 60 * 1000L; // 유효시간 30분
    private final UserDetailsService userDetailsService;

    @PostConstruct //DI 이전 Bean이 생성되기전 한번만 호출하여 초기화됨(서버 올라갈때)
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    /* JWT 양식
Header
{
 	"alg": "서명 시 사용하는 알고리즘",
	"kid": "서명 시 사용하는 키를 식별하는 값",
      	"typ": "타입"
}
payload
{
	"sub": "jaehyuk.kim", //->claim(정보조각)
    	"exp": 1623235123,
    	"iat": 1532341234
}
signature
{
    암호화된 서명값 (secretKey와 payload의 데이터를 헤더의 alg로 암호화한 서명값
}
 */
    //유저의 키와 권한을 매개변수로 받음
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 파기 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 alg 정의 HS256
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

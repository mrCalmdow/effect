package effect.effect.common.security;

import effect.effect.repo.UserAutoRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.Date;
import java.util.HashMap;

/**
 * @author feilongchen
 * @create 2018-01-27 6:05 PM
 */
@Service
public class JwtTokenService {
    Logger log = LoggerFactory.getLogger(JwtTokenService.class);

    private static final String CLAIMS_KEY_SUB = "sub";
    private static final String CLAIMS_KEY_ISS = "iss";
    private static final String CLAIMS_KEY_ACCOUNT = "account";

    @Autowired
    private UserAutoRepo userAutoRepo;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String account, String appName) {
        HashMap claims = new HashMap<>();
        claims.put(CLAIMS_KEY_SUB, userAutoRepo.findByAccount(account).getUid());
        claims.put(CLAIMS_KEY_ISS, appName);
        claims.put(CLAIMS_KEY_ACCOUNT, account);
        log.info("claims = " + claims.toString());
        return generateToken(claims);
//        return Jwts.builder().claim(CLAIMS_KEY_SUB, userAutoRepo.findByAccount(account).getUid())
//                .claim(CLAIMS_KEY_ISS, appName)
//                .claim(CLAIMS_KEY_ACCOUNT, account)
//                .setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            log.info("validate claims = " + claims.toString());
            boolean isExpiration = claims.getExpiration().before(new Date());
            if(isExpiration) {
                log.warn("token has expiration");
                return false;
            }
        } catch (JwtException e) {
            log.warn("Invalid token");
            return false;
        }
        return true;
    }

    public Payload loadPayload(String token) {
        log.info("loadPayload----start");
        Payload payload = new Payload();
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        payload.setUserId(claims.getSubject());
        payload.setAppName(claims.getIssuer());
        payload.setAccount(userAutoRepo.findByUid(claims.getSubject()).getAccount());
        log.info("loadPayload----end");
        return payload;
    }


    private String generateToken(HashMap claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    @Getter
    @Setter
    public class Payload {
        private String userId;
        private String account;
        private String appName;
    }
}

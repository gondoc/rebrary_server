package com.gondoc.rebrary.component;

import com.gondoc.rebrary.component.util.RandomUtil;
import com.gondoc.rebrary.component.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.*;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSender {

    private final RedisUtils redisUtils;
    private final RandomUtil randomUtil;
    private final JavaMailSender javaMailSender;
    private final static long VERIFY_CD_EXPIRE = 1; // 5분 설정

    // 이메일 인증코드 발송
    public long sendVerificationCode(String email) {
        try {
            // 이메일을 받은 후 검증 로직 작성요망
            Optional<String> verifyCd = redisUtils.get(email, String.class);

            // 코드가 있는 경우
            if (verifyCd.isPresent()) {
                // 아이템 삭제
                redisUtils.delete(email);
//                long expireEpoch = redisUtils.getExpire(email);
//                log.info("expireEpoch {} ", expireEpoch);
//                return expireEpoch;
            }

            // 없는 경우 발행
            return issuanceVerifyCdSendMail(email);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 발송 처리 담당 메서드 (코드 발행, 메일 발송, email 과 코드 저장, 만료 시간 반환)
    private long issuanceVerifyCdSendMail(String email) throws Exception {
        String verifyCd = issuanceVerifyCd();
        long expireEpoch = saveVerifyCd(email, verifyCd);
        mailSend(email, verifyCd);
        return expireEpoch;
    }

    // 메일 전송 메서드
    private void mailSend(String email, String verifyCd) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
        message.setSubject("re;brary 이메일 인증코드 발송");
        message.setContent(getVerifyMailContent(verifyCd), "text/html;charset=UTF-8");
        javaMailSender.send(message);
    }

    // 메일 본문을 만드는 메서드
    private static String getVerifyMailContent(String verifyCd) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style='width:100%;max-width:500px;margin:0 auto;'>");
        sb.append("<tr>");
        sb.append("<td style='text-align:center;padding:20px;font-size: 19px;'>");
        sb.append("아래의 인증코드를 화면에 입력해주세요!");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td style='background-color:#deaa79;border-radius:3px;color:#ffffff;font-size:46px;letter-spacing:17px;text-align:center;padding:15px;'>");
        sb.append(verifyCd);
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        return sb.toString();
    }

    // 인증 코드 발행
    private String issuanceVerifyCd() {
        return String.valueOf(randomUtil.getRandomIntValuesByLength(6));
    }

    // 이메일주소와 인증코드를 저장
    private long saveVerifyCd(String email, String verifyCd) {
        // 만료시간
        Duration duration = Duration.ofMinutes(VERIFY_CD_EXPIRE);
        long epochTime = parseDurationToEpochTime(duration);
        redisUtils.set(email, verifyCd, duration);
        return epochTime;
    }

    // 시간을 epochTime 으로 변환
    private long parseDurationToEpochTime(Duration expireDuration) {
        return Instant.now().plus(expireDuration).toEpochMilli();
    }
}

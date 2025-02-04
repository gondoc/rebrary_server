package com.gondoc.rebrary.component;

import com.gondoc.rebrary.component.util.RandomUtil;
import com.gondoc.rebrary.component.util.RedisUtils;
import com.gondoc.rebrary.config.constant.KorAdjective;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSender {

    private final RedisUtils redisUtils;
    private final RandomUtil randomUtil;
    private final JavaMailSender javaMailSender;
    private final static long VERIFY_CD_EXPIRE = 5L;

    // 이메일 인증코드 발송
    public boolean sendVerificationCode(String email) {
        try {
            String verifyCd = issuanceVerifyCd(email);
            MimeMessage message = javaMailSender.createMimeMessage();
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("re;brary 이메일 인증코드 발송");

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

            message.setContent(sb.toString(), "text/html;charset=UTF-8");
            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 인증 코드 발행
    private String issuanceVerifyCd(String email) {
        String verifyCd = String.valueOf(randomUtil.getRandomIntValuesByLength(6));
        saveVerifyCd(email, verifyCd);
        return verifyCd;
    }

    // 이메일주소와 인증코드를 저장
    private void saveVerifyCd(String email, String verifyCd) {
        redisUtils.set(email, verifyCd, VERIFY_CD_EXPIRE);
    }

}

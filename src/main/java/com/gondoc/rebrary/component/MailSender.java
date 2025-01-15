package com.gondoc.rebrary.component;

import com.gondoc.rebrary.component.util.RandomUtil;
import com.gondoc.rebrary.config.constant.KorAdjective;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSender {

    private final KorAdjective korAdjective;
    private final RandomUtil randomUtil;
    private final JavaMailSender javaMailSender;

    // 회원가입 이메일 전송 메서드
    public boolean send(String email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Rebrary 회원가입 인증 코드 안내");
        StringBuffer sb = new StringBuffer();
        String ad = korAdjective.findIndex(randomUtil.getRandomIntByMax(korAdjective.getLength()));
        sb.append("<table><tr>");
        sb.append("<td style='width: 220px'>");
        sb.append(ad);
        sb.append(" 리브레리");
        sb.append(randomUtil.getRandomIntValuesByLength(4));
        sb.append(" 님의 인증 코드</td>");
        sb.append("<td style='background-color: #DEAA79; border-radius: 3px; color: #ffffff'>");
        sb.append(randomUtil.getRandomUUIDCd());
        sb.append("</td>");
        sb.append("</tr></table>");
        message.setContent(sb.toString(), "text/html;charset=UTF-8");
        javaMailSender.send(message);
        return true;
    }


}

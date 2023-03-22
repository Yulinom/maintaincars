package ml.tianhong.rwh.maintain.portal.service.impl;

import ml.tianhong.rwh.maintain.portal.service.EmailService;
import ml.tianhong.rwh.maintain.common.util.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;
    @Value("${host}")
    private String HOST;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     *
     * @param toPerson 目的邮箱
     * @param content 验证链接
     */
    @Override
    public void sendEmail(String toPerson, String content) {
        //邮件服务
        Context context = new Context();
        context.setVariable("email", toPerson);
        context.setVariable("createTime", new Date());
        context.setVariable("fromEmail", from);
        context.setVariable("clickLink", HOST+content);
        String templateContent = templateEngine.process("register-email", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toPerson);
            helper.setSubject("汽车维修中心用户注册");
            helper.setText(templateContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MyException(20001,"发送注册邮件失败");
        }

        //控制台打印token方便测试
        System.out.println("\n\n\t\t\t*******目的邮箱" + toPerson + "===>激活token：" + content + "\n\n");
    }

    @Override
    public void sendNotice(String toPerson, String notice, String subject) {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(toPerson);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(notice);
        javaMailSender.send(simpleMailMessage);
    }
}

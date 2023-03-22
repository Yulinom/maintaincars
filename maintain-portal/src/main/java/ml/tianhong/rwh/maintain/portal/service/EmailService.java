package ml.tianhong.rwh.maintain.portal.service;

public interface EmailService {
    void sendEmail(String toPerson, String context);

    void sendNotice(String toPerson, String notice, String subject);
}

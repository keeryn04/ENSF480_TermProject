package backend;

public interface MailSender {
    void sendEmail(String recipient, String movieName);
}

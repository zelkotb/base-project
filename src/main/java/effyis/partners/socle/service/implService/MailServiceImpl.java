package effyis.partners.socle.service.implService;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import effyis.partners.socle.dto.MailDTO;
import effyis.partners.socle.service.MailService;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	/**
	 * example of sending an email MailDTO mailDto = new MailDTO();
	 * mailDto.setFrom(fromMail); mailDto.setTo(toMail);
	 * mailDto.setTemplate("resetPasswordDemande"); mailDto.setSubject("Reset
	 * Password Verification"); Map<String, Object> props = new HashMap<>();
	 * props.put("link", "https://localhost:443/verification?totp=helloZakaria));
	 * props.put("sign", "signature"); props.put("location", "location");
	 * mailDto.setProps(props);
	 */
	@Override
	public void sendMail(MailDTO mail) throws MessagingException {
		MimeMessage message = this.javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariables(mail.getProps());
		String html = this.templateEngine.process(mail.getTemplate(), context);
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(html, true);
		this.javaMailSender.send(message);

	}

}

package com.springbootapirestcourse.users.shared.email;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.*;

public class AmazonSES {
    public void send(com.springbootapirestcourse.users.shared.email.Message message)  throws MessagingException, IOException {
        MimeMessage mimeMessage = buildMimeMessage(message);
        try {
            System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());
            Region region = Region.US_EAST_1;
            SesClient client = SesClient.builder()
                    .region(region)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            AwsRequestOverrideConfiguration myConf = AwsRequestOverrideConfiguration.builder()
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build() ;

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .overrideConfiguration(myConf)
                    .build();

            client.sendRawEmail(rawEmailRequest);
            System.out.println("Email message Sent");

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private MimeMessage buildMimeMessage(com.springbootapirestcourse.users.shared.email.Message emailMessage) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        message.setSubject(emailMessage.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(emailMessage.getSender()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMessage.getRecipient()));

        MimeMultipart msgBody = new MimeMultipart("alternative");
        msgBody.addBodyPart(
                createBodyPart(emailMessage.getBodyText(), "text/plain; charset=UTF-8")
        );
        msgBody.addBodyPart(
                createBodyPart(emailMessage.getBodyHTML(), "text/html; charset=UTF-8")
        );
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(msgBody);
        MimeMultipart msg = new MimeMultipart("mixed");
        message.setContent(msg);
        msg.addBodyPart(wrap);

        return message;
    }

    private MimeBodyPart createBodyPart(String type, String content) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, type);
        return bodyPart;
    }
}

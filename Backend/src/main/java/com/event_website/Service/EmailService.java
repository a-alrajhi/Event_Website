package com.event_website.Service;

import com.event_website.Dto.DetailedTicketWithSameTypeDTO;
import com.event_website.Dto.TicketWithSameTypeDTO;
import com.event_website.Entity.User;
import com.event_website.Exception.ResourceNotFoundException;
import com.event_website.Repository.UserRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import jakarta.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    @Autowired private UserRepo userRepo;
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email with QR code attachments.
     *
     * @param to          The recipient email
     * @param subject     Email subject
     * @param htmlBody    Email content (HTML)
     * @param qrCodes     A list of QR code strings (e.g., ticket codes)
     * @throws MessagingException
     * @throws IOException
     * @throws WriterException
     */
    public void sendTicketEmailWithQRCodes(String to, String subject, String htmlBody, List<String> qrCodes)
            throws IOException, WriterException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        for (String qrCodeText : qrCodes) {
            InputStreamSource qrImage = generateQRCodeImageAsAttachment(qrCodeText);
            helper.addAttachment( qrCodeText + ".png", qrImage);
        }

        mailSender.send(message);
    }

    /**
     * Generates QR code image as ByteArrayResource (for attachment)
     */
    private InputStreamSource generateQRCodeImageAsAttachment(String text)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    public void sendTicketEmail(Integer userId, List<DetailedTicketWithSameTypeDTO> ticketGroups) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String to = user.getEmail();
        String subject = "🎟️ Your Event Tickets with QR Codes";

        StringBuilder content = new StringBuilder();
        content.append("<h2>Hello ").append(user.getName()).append(",</h2>");
        content.append("<p>Here are your ticket details and attached QR codes for entry:</p>");

        // Collect QR codes (ticket codes)
        List<String> allTicketCodes = ticketGroups.stream()
                .map(TicketWithSameTypeDTO::getGroupedTicketCode)
                .toList();

        // Build HTML email body
        for (DetailedTicketWithSameTypeDTO dto : ticketGroups) {
            content.append("<hr>");
            content.append("<p><strong>Event:</strong> ").append(dto.getEventName()).append("<br>");
            content.append("<strong>Date:</strong> ").append(dto.getDate()).append("<br>");
            content.append("<strong>Ticket Type:</strong> ").append(dto.getTicketType()).append("<br>");
            content.append("<strong>Quantity:</strong> ").append(dto.getCount()).append("</p>");
        }

        content.append("<p>Thank you for your purchase!<br>Event Website Team</p>");

        // Send the email with QR code image attachments
        try {
            sendTicketEmailWithQRCodes(to, subject, content.toString(), allTicketCodes);
        } catch (Exception e) {
            System.err.println("Failed to send QR code email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

}

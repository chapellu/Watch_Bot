public static void sendMessage(String subject, String text, String destinataire, String PATH_TO_FILE) {
	// 1 -> Création de la session
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.host", SMTP_HOST1);
    properties.setProperty("mail.smtp.user", LOGIN_SMTP1);
    properties.setProperty("mail.from", IMAP_ACCOUNT1);
    Session session = Session.getInstance(properties);
	// 2 -> Création du message
	MimeMessage message = new MimeMessage(session);
    try {
        message.setText(text);
        message.setSubject(subject);
        message.addRecipients(Message.RecipientType.TO, destinataire);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
	
	File file = new File(PATH_TO_FILE);
	FileDataSource datasource1 = new FileDataSource(file);
	DataHandler handler1 = new DataHandler(datasource1);
	MimeBodyPart autruche = new MimeBodyPart();
	try {
		autruche.setDataHandler(handler1);
		autruche.setFileName(datasource1.getName());
	} catch (MessagingException e) {
		e.printStackTrace();
	}
	
	MimeBodyPart content = new MimeBodyPart();
	try {
		content.setContent("Texte du message", "text/plain");
	} catch (MessagingException e) {
		e.printStackTrace();
	}
	
	MimeMultipart mimeMultipart = new MimeMultipart();
	try {
		mimeMultipart.addBodyPart(content);
		mimeMultipart.addBodyPart(autruche);
		mimeMultipart.addBodyPart(musique);
	} catch (MessagingException e) {
		e.printStackTrace();
	}

    
	try {
		message.setContent(mimeMultipart);
	} catch (MessagingException e) {
		e.printStackTrace();
	}
	// 3 -> Envoi du message
    Transport transport;
    try {
        transport = session.getTransport("smtp");
        transport.connect(LOGIN_SMTP1, PASSWORD_SMTP1);
        transport.sendMessage(message, new Address[] { new InternetAddress(destinataire),
                                                        new InternetAddress(copyDest) });
    } catch (MessagingException e) {
        e.printStackTrace();
    } finally {
        try {
            if (transport != null) {
                transport.close();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

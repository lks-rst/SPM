package br.com.sulpasso.sulpassomobile.util.funcoes;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Lucas on 13/06/2018 - 15:54 as part of the project SulpassoMobile.
 */
public class EMailler extends Authenticator
{
    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;

    public EMailler()
    {
        _host = "smtp.gmail.com"; //default smtp server
        _port = "465"; // default smtp port
        _sport = "465"; //default socketfactory port

        _user = "";//username
        _pass = ""; //password
        _from = ""; // email sent from
        _subject = ""; //email subject
        _body = ""; //email body

        _debuggable = false; //debug mode on or off - default off
        _auth = true; //smtp authenticatio - default on

        _multipart = new MimeMultipart();

        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;;x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;;x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;;x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart*//*;;x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;;x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public EMailler(String user, String pass)
    {
        this();

        _user = user;
        _pass = pass;
    }

    public boolean send() throws Exception
    {
        Properties props = _setProperties();

        if (!_user.equals("") && !_pass.equals("") && _to.length > 0 &&
                !_from.equals("") && !_subject.equals("") && !_body.equals(""))
        {
            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            List<String> arrayListTo = new ArrayList<String>();
            for(int i = 0; i < _to.length; i++)
            {
                if (_to[i].equals("") || _to[i].equals(" ")){ /*****/ }
                else { arrayListTo.add(_to[i]); }
            }

            InternetAddress[] addresTo = new InternetAddress[arrayListTo.size()];

            int p = 0;
            for(String s : arrayListTo)
            {
                addresTo[p] = new InternetAddress(s);
                p++;
            }

            msg.setRecipients(MimeMessage.RecipientType.TO, addresTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            BodyPart messaBodyPart = new MimeBodyPart();
            messaBodyPart.setText(_body);
            _multipart.addBodyPart(messaBodyPart);

            msg.setContent(_multipart);

            Transport.send(msg);

            return true;
        }
        else { return false; }
    }

    public void addAttachment(String filename) throws Exception
    {
        try
        {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename.substring(filename.length() - 28));

            _multipart.addBodyPart(messageBodyPart);
        }
        catch (Exception e) { /*****/ }
    }

    public void addAttachment(String fileName, String filePath) throws Exception
    {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);

        _multipart.addBodyPart(messageBodyPart);
        /*
        try
        {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);

            _multipart.addBodyPart(messageBodyPart);
        }
        catch (Exception e)
        {
            String s = e.getMessage();
            s = "";
        }
        */
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(_user, _pass);
    }

    private Properties _setProperties()
    {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if(_debuggable){ props.put("mail.smtp.debug", "true"); }

        if(_auth){ props.put("mail.smtp.auth", "true"); }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    public Boolean sendEmail() throws Exception
    {
        try {
            String hostname = "smtp.gmail.com";
            int port = 587;

            String password = "emailtab";
            String login = "sulpassosoftware@gmail.com";

            String from = login;

            String subject = "subject" ;
            String text = "message";

            AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();
            try
            {
                String to = "recipient@email.com";
                client.connect(hostname, port);
                client.ehlo("localhost");
                if (client.execTLS())
                {
                    client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, login, password);
                    checkReply(client);

                    client.setSender(from);
                    checkReply(client);

                    client.addRecipient(to);
                    checkReply(client);

                    Writer writer = client.sendMessageData();

                    if (writer != null)
                    {
                        SimpleSMTPHeader header = new SimpleSMTPHeader(from, to, subject);
                        writer.write(header.toString());
                        writer.write(text);
                        writer.close();
                        if(!client.completePendingCommand())
                        {
                            throw new Exception("Failure to send the email " +
                                    client.getReply() + client.getReplyString());
                        }
                    }
                    else
                    {
                        throw new Exception("Failure to send the email " +
                                client.getReply() + client.getReplyString());
                    }
                }
                else
                {
                    throw new Exception("STARTTLS was not accepted " +
                            client.getReply() + client.getReplyString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                client.logout();
                client.disconnect();
            }

            return true;
        }
        catch (Exception e) { return false; }
    }

    private static void checkReply(SMTPClient sc) throws Exception
    {
        if (SMTPReply.isNegativeTransient(sc.getReplyCode()))
        {
            throw new Exception("Transient SMTP error " + sc.getReply() + sc.getReplyString());
        }
        else if (SMTPReply.isNegativePermanent(sc.getReplyCode()))
        {
            throw new Exception("Permanent SMTP error " + sc.getReply() + sc.getReplyString());
        }
    }

    public String get_user() { return _user; }

    public void set_user(String _user) { this._user = _user; }

    public String get_pass() { return _pass; }

    public void set_pass(String _pass) { this._pass = _pass; }

    public String[] get_to() { return _to; }

    public void set_to(String[] _to) { this._to = _to; }

    public String get_from() { return _from; }

    public void set_from(String _from) { this._from = _from; }

    public String get_port() { return _port; }

    public void set_port(String _port) { this._port = _port; }

    public String get_sport() { return _sport; }

    public void set_sport(String _sport) { this._sport = _sport; }

    public String get_host() { return _host; }

    public void set_host(String _host) { this._host = _host; }

    public String get_subject() { return _subject; }

    public void set_subject(String _subject) { this._subject = _subject; }

    public String get_body() { return _body; }

    public void set_body(String _body) { this._body = _body; }

    public boolean is_auth() { return _auth; }

    public void set_auth(boolean _auth) { this._auth = _auth; }

    public boolean is_debuggable() { return _debuggable; }

    public void set_debuggable(boolean _debuggable) { this._debuggable = _debuggable; }

    public Multipart get_multipart() { return _multipart; }

    public void set_multipart(Multipart _multipart) { this._multipart = _multipart; }
}
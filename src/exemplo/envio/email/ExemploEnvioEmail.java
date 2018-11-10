/**
 *
 * @author Gabriel Haddad
 */

package exemplo.envio.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

//Permitir acesso em:
//https://myaccount.google.com/lesssecureapps

public class ExemploEnvioEmail {

    public static Properties mailServerProperties;
    public static Session getMailSession;
    public static Message message;
    public static MimeMessage generateMailMessage;
    public static JPasswordField pass;
    public static String senha;
    public static String email;

    public static void main(String[] args) {
        Properties props = new Properties();
        
        // Parâmetros de conexão com servidor Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        email  = JOptionPane.showInputDialog(null, "Insira o seu e-mail", "Login", 
                                             JOptionPane.INFORMATION_MESSAGE);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Digite a senha");
        pass = new JPasswordField(10);
        pass.requestFocus(true);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancelar"};
        int option = JOptionPane.showOptionDialog(null, panel, "Login",
                     JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                     null, options, options[1]);
        
        //Caso selecione OK
        if (option == 0)
        {
            char[] password = pass.getPassword();
            senha = new String(password);
        }

        if (!email.isEmpty() && !senha.isEmpty()) {
            Session session;
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email, senha);
                        }
                    });

            // Ativa Debug para sessão
            session.setDebug(true);

            try {
                message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email)); //Remetente
                    
                //Destinatário(s)
                /*Para adicionar novos destinatários, concatenar em string
                /separando por vírgulas*/
                Address[] toUser = InternetAddress 
                        .parse(email);

                message.setRecipients(Message.RecipientType.TO, toUser);
                
                //Assunto
                message.setSubject("Enviando email com JavaMail");
                
                //Corpo do Email
                message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
                
                // Método para enviar a mensagem criada
                Transport.send(message);

                System.out.println("Feito!!!");

            } catch (MessagingException e) {
                System.out.println("Ocorreu um erro ao enviar o e-mail: \n" + e);
            }
        }
    }
}

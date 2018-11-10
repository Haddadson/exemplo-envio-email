/**
 *
 * @author Gabriel Haddad
 */

package exemplo.envio.email;

import java.awt.FlowLayout;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Permitir acesso em:
//https://myaccount.google.com/lesssecureapps

public class ExemploEnvioEmail {

    public static Properties mailServerProperties;
    public static Session getMailSession;
    public static Message message;
    public static MimeMessage generateMailMessage;
    public static JTextField campoEmail;
    public static JPasswordField campoSenha;
    public static JPanel panel;
    public static JLabel labelEmail;
    public static JLabel labelSenha;
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

        panel = new JPanel();
        labelEmail = new JLabel("Email");
        labelSenha = new JLabel("Senha");
        campoEmail = new JTextField(20);
        campoSenha = new JPasswordField(20);
                
        panel.add(labelEmail);
        panel.add(campoEmail);
        //Separador entre campos
        panel.add(Box.createVerticalStrut(15));
        panel.add(labelSenha);
        panel.add(campoSenha);
        
        String[] opcoes = new String[]{"OK", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(null, panel, "Login",
                     JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                     null, opcoes, opcoes[1]);
        
        //Caso selecione OK
        if (resultado == JOptionPane.OK_OPTION)
        {
            email = campoEmail.getText();
            char[] password = campoSenha.getPassword();
            senha = new String(password);
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
                    message.setText("Enviei este email utilizando JavaMail com minha conta Gmail!");

                    // Método para enviar a mensagem criada
                    Transport.send(message);

                    System.out.println("Feito!!!");

                } catch (MessagingException e) {
                    System.out.println("Ocorreu um erro ao enviar o e-mail: \n" + e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login cancelado.");
        }
    }
}

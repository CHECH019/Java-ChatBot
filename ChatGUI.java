import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGUI extends JFrame {
    JPanel contentPane;
    JTextArea pantalla;
    JButton enviar;
    JTextField texto;
    JScrollPane jScrollPane;
    ChatBotController chatbot;

    ChatGUI(){
        initComponents();
    }
    
    public void initComponents(){
        setBounds(100, 50, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane = new JPanel();
        pantalla = new JTextArea();
        enviar = new JButton();
        texto = new JTextField();
        chatbot = new ChatBotController();
        jScrollPane = new JScrollPane();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                chatbot.saveData();
            }
        });
        contentPane.setLayout(null);
        setContentPane(contentPane);

        enviar.setBackground(new Color(255, 255, 255));
        enviar.setText("enviar");
        enviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enviar.setBounds(780, 500, 80, 30);
        enviar.addActionListener(new ActionListener() {

            @Override   
            public void actionPerformed(ActionEvent e) {
                String question = texto.getText();
                pantalla.append("Tú: "+question +"\n");
                texto.setText("");
                pantalla.append("cBot: "+chatbot.findAnswer(question)+"\n");
            }
            
        });
       
        texto.setBounds(20, 500, 740, 30);

        pantalla.setEditable(false);
        pantalla.setFont(new Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        pantalla.setBounds(20, 20, 840, 460);
        

        jScrollPane.setViewportView(pantalla);

        contentPane.add(pantalla);
        contentPane.add(texto);
        contentPane.add(enviar);

    }
}

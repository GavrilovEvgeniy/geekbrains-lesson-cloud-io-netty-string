package geekbrains.L2L4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingClient extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("vasya");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();


    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingClient::new);

        }

    public SwingClient() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5", "user6",
                "user_with_an_exceptionally_long_nickname"};
        userList.setListData(users);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        Thread.setDefaultUncaughtExceptionHandler(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend) {
            log.append(tfMessage.getText() + System.lineSeparator());
            String message = tfMessage.getText();
        }

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] ste = e.getStackTrace();
        JOptionPane.showMessageDialog(this, ste[0].toString(), "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

}






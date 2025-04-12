import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public Main() {
        setTitle("User Registration");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Outer panel with padding
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // top, left, bottom, right

        // Components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");

        // Add components to panel
        mainPanel.add(nameLabel); mainPanel.add(nameField);
        mainPanel.add(emailLabel); mainPanel.add(emailField);
        mainPanel.add(passwordLabel); mainPanel.add(passwordField);
        mainPanel.add(new JLabel()); // Empty cell
        mainPanel.add(registerButton);

        // Add to frame
        add(mainPanel);

        // Button logic
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Registration successful!");
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Registration failed.");
                }

                stmt.close();
                conn.close();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Database connection failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

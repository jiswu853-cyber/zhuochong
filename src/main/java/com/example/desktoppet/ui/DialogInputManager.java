import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogInputManager {

    private JTextField inputField;
    private String userInput;

    public DialogInputManager() {
        createDialog(); // Initialize the dialog on object creation.
    }

    private void createDialog() {
        JFrame frame = new JFrame("Input Dialog");
        inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = inputField.getText();
                System.out.println("User Input: " + userInput);
                frame.dispose(); // Close the dialog after submission.
            }
        });

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(submitButton);

        frame.add(panel);
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public String getUserInput() {
        return userInput;
    }
}

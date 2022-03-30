package demo;

import be.hcbgsystem.core.models.AccessRequest;
import be.hcbgsystem.frontend.AccessDecision;
import be.hcbgsystem.frontend.PolicyEnforcementPoint;
import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SmartLock {
    private PolicyEnforcementPoint policyEnforcementPoint;
    private JLabel currentState;

    public SmartLock(PolicyEnforcementPoint policyEnforcementPoint) {
        this.policyEnforcementPoint = policyEnforcementPoint;
        initGUI();
    }

    private void initGUI() {
        Container pane = new Container();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setBounds(0, 50, 0, 200);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocation(400, 400);
        frame.setResizable(false);

        currentState = new JLabel("", SwingConstants.CENTER);
        currentState.setText("<html><h1><b>CLOSED</b></h1></html>");
        currentState.setForeground(Color.RED);
        currentState.setBounds(0, 100, 280, 50);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(actionEvent -> tryOpen());
        openButton.setBackground(Color.GREEN);
        openButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        openButton.setFont(new Font("Serif", Font.BOLD, 25));
        openButton.setSize(150, 50);
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(actionEvent -> tryClose());
        closeButton.setBackground(Color.RED);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setFont(new Font("Serif", Font.BOLD, 25));
        closeButton.setSize(150, 50);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        frame.add(currentState);
        pane.add(openButton);
        pane.add(closeButton);

        frame.add(pane);
        frame.setVisible(true);
    }

    private void tryOpen() {
        AccessRequest accessRequest = new AccessRequest("AnySubject", "smartlock-1", "actuate");
        policyEnforcementPoint.requestAccess(accessRequest, (decision, request) -> {
            if (decision == AccessDecision.GRANTED) {
                open();
            }
        });
    }

    private void tryClose() {
        AccessRequest accessRequest = new AccessRequest("AnySubject", "smartlock-1", "actuate");
        policyEnforcementPoint.requestAccess(accessRequest, (decision, request) -> {
            if (decision == AccessDecision.GRANTED) {
                close();
            }
        });
    }

    private void open() {
        currentState.setForeground(Color.GREEN);
        currentState.setText("<html><h1><b>OPEN</b></h1></html>");
    }

    private void close() {
        currentState.setForeground(Color.RED);
        currentState.setText("<html><h1><b>CLOSED</b></h1></html>");
    }
}

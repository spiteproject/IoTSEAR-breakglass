package be.hcbgsystem.awareness.providers;

import be.hcbgsystem.core.models.awareness.AwarenessMessage;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;

import javax.swing.*;

import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ScreenAwarenessProvider implements AwarenessProvider {
    @Override
    public void provideAwareness(AwarenessMessage message) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(600,430);
        frame.setLocation(400, 400);

        JLabel warning = new JLabel("", SwingConstants.CENTER);
        warning.setText("<html><h1><b>WARNING</b></h1></html>");
        warning.setForeground(Color.RED);
        warning.setBounds(0, 20, 600, 50);

        JLabel prelude = new JLabel("", SwingConstants.CENTER);
        prelude.setText("<html><h2>" + message.getPrelude() +"</h2></html>");
        prelude.setBounds(0, 80, 600, 50);

        JLabel nonrep = new JLabel("", SwingConstants.CENTER);
        nonrep.setText("<html><h3>Provide identifying evidence with:</h3></html>");
        nonrep.setBounds(0, 140, 600, 50);

        NonRepudiationRequirements nonRepudiationRequirements = message.getBgPolicy().getNonRepudiationRequirements();
        StringBuilder nonRep = new StringBuilder();
        nonRep.append("<html><ul>");
        for (String id  : nonRepudiationRequirements.getProviders()) {
            nonRep.append("<li>").append(id).append("</li>");
        }
        nonRep.append("</ul></html>");

        JLabel nonreprequirements = new JLabel("", SwingConstants.CENTER);
        nonreprequirements.setBounds(0, 400, 600, 50);
        nonreprequirements.setText(nonRep.toString());


        frame.add(warning);
        frame.add(prelude);
        frame.add(nonrep);
        frame.add(nonreprequirements);

        frame.setVisible(true);
    }
}

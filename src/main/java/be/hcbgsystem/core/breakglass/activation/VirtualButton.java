package be.hcbgsystem.core.breakglass.activation;
import javax.swing.*;
import java.awt.*;

public class VirtualButton implements BreakGlassActivator {
    private VirtualButton getThis() {
        return this;
    }

    private JFrame frame;
    private JButton bgButton;

    @Override
    public void startActivator(BreakGlassActivationCallback callback) {
        frame = new JFrame("Break the glass button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        bgButton = new JButton("BREAK THE GLASS");
        bgButton.addActionListener(actionEvent -> callback.handleBreakGlassActivation(getThis()));
        bgButton.setBackground(Color.RED);
        bgButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bgButton.setFont(new Font("Serif", Font.BOLD, 25));
        frame.getContentPane().add(bgButton);
        frame.setVisible(true);
    }

    @Override
    public void onCollectingEvidence() {
        bgButton.setBackground(Color.ORANGE);
        bgButton.setFont(new Font("Serif", Font.BOLD, 20));
        bgButton.setText("<html>BREAK THE GLASS <br> (Collecting evidence)</html>");
    }

    @Override
    public void onCollectedEvidence() {
        bgButton.setBackground(Color.ORANGE);
        bgButton.setFont(new Font("Serif", Font.BOLD, 20));
        bgButton.setText("<html>BREAK THE GLASS<br> (Collected evidence)</html>");
    }

    @Override
    public void onGlassBroken() {
        bgButton.setBackground(Color.GREEN);
        bgButton.setFont(new Font("Serif", Font.BOLD, 25));
        bgButton.setText("<html>GLASS BROKEN</html>");
    }

    @Override
    public void onRestored() {
        bgButton.setBackground(Color.RED);
        bgButton.setFont(new Font("Serif", Font.BOLD, 25));
        bgButton.setText("BREAK THE GLASS");
    }
}

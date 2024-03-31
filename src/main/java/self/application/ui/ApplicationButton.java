package self.application.ui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ApplicationButton extends JButton {
    public ApplicationButton(BufferedImage image,
                             ActionListener listener,
                             String command
    ) {
        super(new ImageIcon(image));

        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
        setFocusPainted(false);
        setActionCommand(command);
        addActionListener(listener);
    }
}

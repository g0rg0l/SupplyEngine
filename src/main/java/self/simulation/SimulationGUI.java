package self.simulation;

import self.application.ui.ApplicationButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static self.utility.Preferences.SIMULATION_MENU_DEFAULT_COLOR;
import static self.utility.Preferences.SIMULATION_MENU_DEFAULT_SIZE;

public class SimulationGUI {
    private final Simulation simulation;
    private JLabel dateText;
    private JButton speedIcon;
    private JButton pauseButton;

    public SimulationGUI(Simulation simulation) {
        this.simulation = simulation;
    }

    public void updateDate(String date) {
        dateText.setText(date);
    }

    public void updateSpeed(String speed) {
        speedIcon.setText(speed);
    }

    public void updatePauseButton(boolean isPause) throws IOException {
        if (isPause) {
            pauseButton.setIcon(new ImageIcon(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/play_button.png")))
            ));
        }
        else {
            pauseButton.setIcon(new ImageIcon(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/pause_button.png")))
            ));
        }
    }

    public void createFooter(Container pane) {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setPreferredSize(SIMULATION_MENU_DEFAULT_SIZE);
        menu.setBackground(SIMULATION_MENU_DEFAULT_COLOR);

        menu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK.brighter(), 2, true),
                        BorderFactory.createEmptyBorder(0, 15, 0, 15)
                )
        );


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(menu.getBackground());
        buttonsPanel.setLayout(new GridLayout(1, 5, 10, 0));

        try {
            buttonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/slow_down_button.png"))),
                    simulation, "previous simulation speed command"));

            pauseButton = new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/pause_button.png"))),
                    simulation, "pause/play simulation speed command");
            buttonsPanel.add(pauseButton);

            buttonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/speed_up_button.png"))),
                    simulation, "next simulation speed command"));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }

        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BorderLayout());
        speedPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        BorderFactory.createEmptyBorder(0, 30, 0, 15)
                )
        );

        speedIcon = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ui/speed_icon.png"))));
        speedIcon.setContentAreaFilled(false);
        speedIcon.setBorder(BorderFactory.createEmptyBorder());
        speedIcon.setFocusPainted(false);
        speedIcon.setFocusable(false);
        speedIcon.setHorizontalTextPosition(JLabel.CENTER);
        speedIcon.setVerticalTextPosition(JLabel.CENTER);
        speedIcon.setFont(new Font("Verdana", Font.PLAIN, 14));
        speedIcon.setText("1s");
        buttonsPanel.add(speedIcon);
        speedPanel.add(speedIcon, BorderLayout.LINE_START);

        dateText = new JLabel();
        dateText.setFont(new Font("Verdana", Font.PLAIN, 18));
        speedPanel.add(dateText, BorderLayout.LINE_END);

        menu.add(speedPanel, BorderLayout.CENTER);

        var rightButtonsPanel = new JPanel();
        rightButtonsPanel.setLayout(new FlowLayout());

        rightButtonsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(0, 0, 0, 0),
                        BorderFactory.createEmptyBorder(5, 0, 0, 0)
                )
        );

        try {
            rightButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/show statistics button.png"))),
                    simulation, "open statistics frame"));

            rightButtonsPanel.add(new ApplicationButton(
                    ImageIO.read(Objects.requireNonNull(getClass().getResource("/ui/stop_button.png"))),
                    simulation, "close simulation"));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load button icons.");
            System.exit(-1);
        }

        menu.add(buttonsPanel, BorderLayout.LINE_START);
        menu.add(rightButtonsPanel, BorderLayout.LINE_END);

        pane.add(menu, BorderLayout.PAGE_END);
    }
}

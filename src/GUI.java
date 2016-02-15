import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created by Антон on 14.02.2016.
 */
public class GUI extends JFrame {

    private JButton startButton;
    private JTextField pointsText;
    private DrawPanel drawPanel;
    private List<Cluster> clusters;
    private Maximin maximin;
    private JPanel guiPanel;
    private JPanel container;

    public GUI() {
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        guiPanel = new JPanel();
        guiPanel.setSize(500, 100);
        maximin = new Maximin();
        this.setContentPane(container);
        this.setSize(500,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        pointsText = new JTextField("Count of points");
        pointsText.setSize(150, 30);
        pointsText.setLocation(5,5);
        pointsText.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pointsText.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        startButton = new JButton("Start!");
        startButton.setSize(150, 30);
        startButton.setLocation(610, 5);

        guiPanel.add(pointsText);
        guiPanel.add(startButton);

        container.add(guiPanel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int countOfPoints = Integer.parseInt(pointsText.getText());
                maximin.removeAllClusters();
                maximin.setCountOfPoints(countOfPoints);
                maximin.createPoints();
                maximin.calculate();
                JFrame pointsFrame = new JFrame("Result");
                pointsFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                pointsFrame.add(new DrawPanel(maximin.getClusters()));
                pointsFrame.setVisible(true);
            }
        });


        this.setVisible(true);
    }


}

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MultiThreadingSample extends Thread {

    JButton calculo;

    public static void main(String[] args) {
        MultiThreadingSample tr1 = new MultiThreadingSample();
        tr1.start();
        MultiThreadingSample tr2 = new MultiThreadingSample();
        tr2.start();
    }

    void setEnabled(boolean b) {
        calculo.setEnabled(b);
    }

    public void run() {

        calculo = new JButton("Calcular en consola");

        final JLabel resultLabel = new JLabel("Estamos ready", JLabel.CENTER);

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout . . .
        JPanel p = new JPanel();
        p.setOpaque(true);
        p.setLayout(new FlowLayout());
        p.add(calculo);

        Container c = f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(p, BorderLayout.CENTER);
        c.add(resultLabel, BorderLayout.SOUTH);

        // Listeners
        calculo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                resultLabel.setText("Working . . .");
                setEnabled(false);

                Thread worker = new Thread() {
                    public void run() {
                        for (int i = 0; i < 55; i++) {
                            System.out.println("Calculando numero " + i);
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //para ver en frame
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                resultLabel.setText("Listo");
                                setEnabled(true);
                            }
                        });
                    }
                };

                worker.start();
            }
        });

        f.setSize(300, 100);
        f.setVisible(true);
    }

}

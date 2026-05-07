import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class Linky_otevreni {

    private JPanel panel;
    private JButton zpetBTN;
    private JLabel nadpis;
    private JTextArea zastavky;

    public int linka;

    public Linky_otevreni(int linka) {
        panel.setBackground(App.pozadiBarva); // Nastavit barvu pozadí podle globálního nastavení

        this.linka = linka;
        zpetBTN.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose();
        });


        if (linka == 0) {

            Object[] spoje = {"Páteřní", "Hlavní", "Vedlejší"};

            Object vybranySpoj = JOptionPane.showInputDialog(
                    null,
                    "Vyberte typ linky:",
                    "Výběr linky",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    spoje,
                    spoje[0]
            );

            if (vybranySpoj != null) {

                String typ = "";

                // převod na p/h/m
                if (vybranySpoj.equals("Páteřní")) {
                    typ = "p";
                } else if (vybranySpoj.equals("Hlavní")) {
                    typ = "h";
                } else if (vybranySpoj.equals("Vedlejší")) {
                    typ = "m";
                }

                java.util.ArrayList<String> nalezeneLinky = new java.util.ArrayList<>();

                try (Scanner sc = new Scanner(
                        new BufferedInputStream(
                                new FileInputStream(App.linkyFile)),
                        "windows-1250")) {

                    while (sc.hasNextLine()) {

                        String line = sc.nextLine();

                        String[] parts = line.split(";");

                        if (parts.length >= 2) {

                            String cisloLinky = parts[0].trim();
                            String typLinky = parts[1].trim();

                            // filtr podle typu
                            if (typLinky.equals(typ)) {
                                nalezeneLinky.add(cisloLinky);
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // druhý dialog s linkami
                if (!nalezeneLinky.isEmpty()) {

                    Object vybranaLinka = JOptionPane.showInputDialog(
                            null,
                            "Vyberte linku:",
                            "Výběr linky",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            nalezeneLinky.toArray(),
                            nalezeneLinky.get(0)
                    );

                    if (vybranaLinka != null) {

                        int cislo = Integer.parseInt(vybranaLinka.toString());

                        // otevření konkrétní linky
                        new Linky_otevreni(cislo).otevriOkno();
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Žádné linky nenalezeny."
                    );
                }
            }

        } else {

            nadpis.setText("Linka " + linka);

                try (Scanner sc = new Scanner(
                        new BufferedInputStream(
                                new FileInputStream("linky/" + linka + ".txt")),
                        "windows-1250")) {

                    while (sc.hasNextLine()) {

                        String line = sc.nextLine();

                        String[] parts = line.split(";");

                        if (parts.length >= 2) {

                            String cisloLinky = parts[0].trim();

                            if (cisloLinky.equals(String.valueOf(linka))) {
                                // Zobrazit informace o lince
                                StringBuilder info = new StringBuilder();
                                info.append("Číslo linky: ").append(cisloLinky).append("\n");
                                info.append("Zastávky: ").append(parts[1].trim()).append("\n");
                                info.append("Počet spojů přes všední den: ").append(parts[2].trim()).append("\n");
                                info.append("Počet spojů přes víkend: ").append(parts[3].trim()).append("\n");

                                zastavky.setText(info.toString());
                                break;
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
    }

    public void otevriOkno() {
        JFrame frame = new JFrame("Ceník - Evidence spojů Pouličky");

        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Nastavit barvu pozadí podle globálního nastavení
        if (panel != null) {
            panel.setBackground(App.pozadiBarva);
        }
    }
}

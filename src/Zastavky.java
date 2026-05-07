import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Zastavky {
    private JPanel panel;
    private JLabel nadpis;
    private JTextArea textArea1;
    private JButton zpětButton;
    private JTextArea textArea2;

    private int ktereMesto;
    private String mestoNazev;
    private String vlastniMesta = ""; // Pro vlastní výběr měst (kódy oddělené čárkou)

    public Zastavky(int ktereMesto, String mestoNazev) {
        this.ktereMesto = ktereMesto;
        this.mestoNazev = mestoNazev;
        
        // Inicializace UI
        nacistZastavkyData();
        zpětButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose();
        });
    }
    
    public void setMesta(String mesta) {
        this.vlastniMesta = mesta;
        nacistZastavkyData();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void nacistZastavkyData() {
        StringBuilder sb = new StringBuilder();
        StringBuilder stats = new StringBuilder();
        String mestoKod = "";
        int pocetZastavek = 0;
        int pocetUH = 0, pocetKU = 0, pocetSM = 0;
        
        try {
            if (ktereMesto == 0) {
                // Zobrazit všechny zastávky ze všech měst
                mestoKod = ""; // Prázdný kod znamená všechna města
            } else if (ktereMesto == 1) {
                mestoKod = "uh";
            } else if (ktereMesto == 2) {
                mestoKod = "ku";
            } else if (ktereMesto == 3) {
                mestoKod = "sm";
            } else if (ktereMesto == 99) {
                mestoKod = vlastniMesta; // Vlastní výběr
            }
            
            File zastavkyFile = new File("zastavky.txt");
            if (!zastavkyFile.exists()) {
                textArea1.setText("Soubor se zastávkami nenalezen.");
                return;
            }
            
            try (Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream(zastavkyFile)), "windows-1250")) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(";");
                    if (parts.length >= 2) {
                        String kod = parts[0].trim();
                        
                        // Počítán všechny zastávky pro statistiku
                        if (kod.equals("uh")) pocetUH++;
                        else if (kod.equals("ku")) pocetKU++;
                        else if (kod.equals("sm")) pocetSM++;
                        
                        // Zjistění, zda zobrazit tuto zastávku
                        boolean zobrazit = false;
                        if (ktereMesto == 99) {
                            // Vlastní výběr - zkontroluj, zda je město v seznamu
                            String[] mesta = mestoKod.split(",");
                            for (String m : mesta) {
                                if (kod.equals(m.trim())) {
                                    zobrazit = true;
                                    break;
                                }
                            }
                        } else if (mestoKod.isEmpty() || kod.equals(mestoKod)) {
                            zobrazit = true;
                        }
                        
                        if (zobrazit) {
                            sb.append(parts[1].trim()).append("\n");
                            pocetZastavek++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Vytvoření statistiky
        stats.append("=== STATISTIKA ===\n\n");
        
        if (ktereMesto == 0) {
            // Všechna města - detailní statistika
            stats.append("Celkem zastávek: ").append(pocetZastavek).append("\n\n");
            stats.append("Rozpad podle měst:\n");
            stats.append("• Uherské Hradiště: ").append(pocetUH).append("\n");
            stats.append("• Kunovice: ").append(pocetKU).append("\n");
            stats.append("• Staré Město: ").append(pocetSM).append("\n\n");
            stats.append("Průměr na město: ").append(
                String.format("%.1f", (pocetZastavek / 3.0))
            ).append("\n");
        } else if (ktereMesto == 99) {
            // Vlastní výběr
            stats.append("Zastávek v městech: ").append(pocetZastavek).append("\n\n");
            stats.append("Statistika všech měst:\n");
            stats.append("• Uherské Hradiště: ").append(pocetUH).append("\n");
            stats.append("• Kunovice: ").append(pocetKU).append("\n");
            stats.append("• Staré Město: ").append(pocetSM).append("\n\n");
            stats.append("Celkem v systému: ").append(pocetUH + pocetKU + pocetSM).append("\n");
        } else {
            // Konkrétní město
            stats.append("Zastávek v městě: ").append(pocetZastavek).append("\n\n");
            stats.append("Statistika všech měst:\n");
            stats.append("• Uherské Hradiště: ").append(pocetUH).append("\n");
            stats.append("• Kunovice: ").append(pocetKU).append("\n");
            stats.append("• Staré Město: ").append(pocetSM).append("\n\n");
            stats.append("Celkem v systému: ").append(pocetUH + pocetKU + pocetSM).append("\n");
        }
        
        textArea1.setText(sb.toString().isEmpty() ? "Žádné zastávky." : sb.toString());
        textArea2.setText(stats.toString());
        nadpis.setText("Zastávky - " + mestoNazev);
    }

    public void otevriOkno() {
        JFrame frame = new JFrame("Evidence spojů Pouličky");
        
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Nastavit barvu pozadí podle globálního nastavení
        if (panel != null) {
            panel.setBackground(App.pozadiBarva);
        }
    }
}

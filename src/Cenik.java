import javax.swing.*;

public class Cenik {
    private JPanel panel;
    private JLabel nadpis;
    private JTextArea cennikArea;
    private JTextArea statistikaArea;
    private JButton zpetButton;

    public Cenik() {
        nacistCennik();
        zpetButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose();
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void nacistCennik() {
        // Vytvoření ceníku
        StringBuilder cenik = new StringBuilder();
        cenik.append("╔════════════════════════════════════════╗\n");
        cenik.append("║          JÍZDNÍ CENÍK                  ║\n");
        cenik.append("╚════════════════════════════════════════╝\n\n");
        cenik.append("JEDNOTLIVÉ JÍZDENKY:\n");
        cenik.append("────────────────────────────────────────\n");
        cenik.append("• Základní jízdenka ..................... 14 Kč\n");
        cenik.append("• Studentská jízdenka ................... 7 Kč\n");
        cenik.append("• Senioři do 75 let ..................... 7 Kč\n");
        cenik.append("• Senioři nad 75 let .................... Zdarma\n\n");
        cenik.append("VŠEOBECNÉ INFORMACE:\n");
        cenik.append("────────────────────────────────────────\n");
        cenik.append("• Platnost: Jednodenní\n");
        cenik.append("• Přestup: Možný\n");
        cenik.append("• Platidlo: Hotovost / Kartou\n");
        cenik.append("• Slevy: Pro skupiny od 10 osob -10%\n");
        cenik.append("• Max. vzdálenost: 50 km\n");

        cennikArea.setText(cenik.toString());

        // Vytvoření statistiky
        StringBuilder stat = new StringBuilder();
        stat.append("═══════════════════════════════════════\n");
        stat.append("STATISTIKA CENÍKU\n");
        stat.append("═══════════════════════════════════════\n\n");
        stat.append("Počet typů jízdenek: 4\n\n");
        stat.append("CENOVÉ SROVNÁNÍ:\n");
        stat.append("─────────────────\n");
        stat.append("• Nejdražší: Základní (14 Kč)\n");
        stat.append("• Nejlevnější: Senior 75+ (0 Kč)\n\n");
        stat.append("PRŮMĚRNÁ CENA:\n");
        stat.append("─────────────────\n");
        stat.append(String.format("  %.2f Kč\n\n", (14.0 + 7.0 + 7.0) / 3.0));
        stat.append("KOLIK LZE UŠETŘIT:\n");
        stat.append("─────────────────\n");
        stat.append("• Student vs. zákl.: -50% (-7 Kč)\n");
        stat.append("• Senior (do 75) vs. zákl.: -50% (-7 Kč)\n");
        stat.append("• Senior (nad 75) vs. zákl.: -100% (-14 Kč)\n\n");
        stat.append("SKUPINOVÁ SLEVA (10+ osob):\n");
        stat.append("─────────────────\n");
        stat.append("  Zákl.: 12,60 Kč (-1,40 Kč)\n");
        stat.append("  Student: 6,30 Kč (-0,70 Kč)\n");

        statistikaArea.setText(stat.toString());
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

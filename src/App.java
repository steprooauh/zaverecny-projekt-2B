import javax.swing.*;
import java.awt.*;

public class App {
    private JPanel panel;

    public static int velikostX = 500;
    public static int velikostY = 600;

    //vysvětlení menubar
    public void menu(JFrame frame){
        JMenuBar menuBar = new JMenuBar();

        JMenu nastaveni = new JMenu("Nastavení");
        JMenu linky = new JMenu("Linky");
        JMenu zastavky = new JMenu("Zastávky");
        JMenu spoje = new JMenu("Spoje");

        //= nastavení menu

        //tlačítko pro změnu velikosti
        JMenuItem zmenaVelikostiOkna = new JMenuItem("Změna velikosti okna");

        zmenaVelikostiOkna.addActionListener(e -> {
            // Zobrazit dialog pro zadání nové velikosti
            String inputX = JOptionPane.showInputDialog("Zadejte novou šířku okna:");
            String inputY = JOptionPane.showInputDialog("Zadejte novou výšku okna:");

            // Pokusit se převést vstupy na čísla a nastavit novou velikost okna
            try {
                int newVelikostX = Integer.parseInt(inputX);
                int newVelikostY = Integer.parseInt(inputY);
                velikostX = newVelikostX;
                velikostY = newVelikostY;
                // Nastavit novou velikost okna
                frame.setSize(velikostX, velikostY);
            } catch (NumberFormatException ex) {
                // Pokud vstup není platné číslo, zobrazit chybovou zprávu
                JOptionPane.showMessageDialog(frame, "Neplatný vstup. Zadejte čísla.");
            }
        });
        nastaveni.add(zmenaVelikostiOkna);

        //tlačítko pro změnu barvy pozadí
        JMenuItem zmenaBarvyPozadi = new JMenuItem("Změna barvy pozadí");
        zmenaBarvyPozadi.addActionListener(e -> {
            // Zobrazit dialog pro výběr barvy
            Color newColor = JColorChooser.showDialog(frame, "Vyberte barvu pozadí", panel.getBackground());
            if (newColor != null) {
                panel.setBackground(newColor);
            }
        });
        nastaveni.add(zmenaBarvyPozadi);

        nastaveni.add(new JSeparator());

        JMenuItem ukonciAplikaci = new JMenuItem("Ukončit aplikaci");
        ukonciAplikaci.addActionListener(e -> System.exit(0));
        nastaveni.add(ukonciAplikaci);




        menuBar.add(nastaveni);
        menuBar.add(linky);
        menuBar.add(zastavky);
        menuBar.add(spoje);

        frame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Evidence spojů Pouličky");
        App app = new App();

        frame.setContentPane(app.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(velikostX, velikostY);

        app.menu(frame);

        frame.setVisible(true);
    }
}


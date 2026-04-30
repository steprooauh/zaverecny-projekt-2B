import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private JPanel panel;
    private JLabel nadpis;
    private JLabel podnadpis;
    private JButton linkyBTN;
    private JButton zastavkyBTN;
    private JButton cenikBTN;
    private JButton oficiálníStránkaButton;

    public static int velikostX = 500;
    public static int velikostY = 600;

    public static File linkyFile = new File("linky.txt");
    public static File linkyUpresnitFile = new File("linky_upresnit.txt");

    //vysvětlení menubar
    public void menu(JFrame frame) throws IOException {
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

        //= linky menu

        // vytvoření kategorií
                JMenu paterni = new JMenu("Páteřní");
                JMenu hlavni = new JMenu("Hlavní");
                JMenu vedlejsi = new JMenu("Vedlejší");
        
        // přidání do menu "Linky"
                linky.add(paterni);
                linky.add(hlavni);
                linky.add(vedlejsi);
        
        // načtení linek do menu
                nacistLinky(paterni, hlavni, vedlejsi);


        menuBar.add(linky);


        //= zastavky menu
        menuBar.add(zastavky);

        // vytvoření měst
        JMenuItem zastUH = new JMenuItem("Uherské Hradiště");
        JMenuItem zastKU = new JMenuItem("Kunovice");
        JMenuItem zastSM = new JMenuItem("Staré Město");

        // přidání měst do menu "Zastávky"
        zastavky.add(zastUH);
        zastavky.add(zastKU);
        zastavky.add(zastSM);

        frame.setJMenuBar(menuBar);
    }

    public void nacistLinky(JMenu paterni, JMenu hlavni, JMenu vedlejsi) {
        try {
            if (!linkyFile.exists()) {
                linkyFile.createNewFile();
            }

            try (Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream(linkyFile)), "windows-1250")) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(";");

                    if (parts.length >= 2) {
                        String linka = parts[0].trim();
                        String typ = parts[1].trim();

                        JMenuItem item = new JMenuItem("Linka " + linka);

                        // rozdělení podle typu
                        switch (typ) {
                            case "p":
                                paterni.add(item);
                                break;
                            case "h":
                                hlavni.add(item);
                                break;
                            case "m":
                                vedlejsi.add(item);
                                break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public App(){
        linkyBTN.addActionListener(e -> {
            nacistLinkyApp();
        });
    }

    public void nacistLinkyApp(){
        try {
            if (!linkyUpresnitFile.exists()) {
                linkyUpresnitFile.createNewFile();
            }

            try (Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream(linkyUpresnitFile)), "windows-1250")) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(";");

                    if (parts.length >= 2) {
                        String linka = parts[0].trim();
                        String typ = parts[1].trim();



                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Evidence spojů Pouličky");
        App app = new App();

        frame.setContentPane(app.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(velikostX, velikostY);

        app.menu(frame);

        frame.setVisible(true);
    }
}


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
    public static Color pozadiBarva = UIManager.getColor("Panel.background"); // Výchozí barva pozadí
    public int ktereMesto = 0; // 0 = žádné, 1 = uh, 2 = kunovice, 3 = staré město

    public static File linkyFile = new File("linky.txt");
    public static File linkyUpresnitFile = new File("linky_upresnit.txt");
    public static File zastavkyFile = new File("zastavky.txt");

    //vysvětlení menubar
    public void menu(JFrame frame) throws IOException {
        JMenuBar menuBar = new JMenuBar();

        JMenu nastaveni = new JMenu("Nastavení");
        JMenu linky = new JMenu("Linky");
        JMenu zastavky = new JMenu("Zastávky");
        JMenu prihlaseni = new JMenu("Přihlášení");

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
            Color newColor = JColorChooser.showDialog(frame, "Vyberte barvu pozadí", pozadiBarva);
            if (newColor != null) {
                pozadiBarva = newColor;
                panel.setBackground(pozadiBarva);
                // Aktualizovat všechna otevřená okna
                aktualizovatBarvuPozadi();
            }
        });
        nastaveni.add(zmenaBarvyPozadi);

        // tlačítko pro reset nastavení
        JMenuItem resetNastaveni = new JMenuItem("Obnovit výchozí nastavení");
        resetNastaveni.addActionListener(e -> {
            velikostX = 500;
            velikostY = 600;
            pozadiBarva = UIManager.getColor("Panel.background");
            frame.setSize(velikostX, velikostY);
            panel.setBackground(pozadiBarva);
            // Aktualizovat všechna otevřená okna
            aktualizovatBarvuPozadi();
            JOptionPane.showMessageDialog(frame, "Nastavení bylo obnoveno na výchozí hodnoty.");
        });
        nastaveni.add(resetNastaveni);

        // tlačítko pro informace o aplikaci
        JMenuItem infoAplikace = new JMenuItem("O aplikaci");
        infoAplikace.addActionListener(e -> {
            String info = "Evidence spojů Pouličky\n\n" +
                    "Verze: 1.0\n" +
                    "Autor: Stepan Prokop\n\n" +
                    "Aplikace pro správu autobusových linek,\n" +
                    "zastávek a spojů v okolí Pouličky.\n\n" +
                    "© 2026 Všechna práva vyhrazena.";
            JOptionPane.showMessageDialog(frame, info, "O aplikaci", JOptionPane.INFORMATION_MESSAGE);
        });
        nastaveni.add(infoAplikace);

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

        // vytvoření měst
        JMenuItem zastUH = new JMenuItem("Uherské Hradiště");
        JMenuItem zastKU = new JMenuItem("Kunovice");
        JMenuItem zastSM = new JMenuItem("Staré Město");
        JMenuItem zastVsechna = new JMenuItem("Všechna města");
        JMenuItem zastVlastni = new JMenuItem("Vlastní výběr");

        // Action listenery pro jednotlivá města
        zastUH.addActionListener(e -> {
            new Zastavky(1, "Uherské Hradiště").otevriOkno();
        });

        zastKU.addActionListener(e -> {
            new Zastavky(2, "Kunovice").otevriOkno();
        });

        zastSM.addActionListener(e -> {
            new Zastavky(3, "Staré Město").otevriOkno();
        });

        zastVsechna.addActionListener(e -> {
            new Zastavky(0, "Všechna města").otevriOkno();
        });

        zastVlastni.addActionListener(e -> {
            // Dialog pro vlastní výběr měst
            JCheckBox uhCheck = new JCheckBox("Uherské Hradiště");
            JCheckBox kuCheck = new JCheckBox("Kunovice");
            JCheckBox smCheck = new JCheckBox("Staré Město");

            JPanel checkPanel = new JPanel();
            checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
            checkPanel.add(uhCheck);
            checkPanel.add(kuCheck);
            checkPanel.add(smCheck);

            int result = JOptionPane.showConfirmDialog(null, checkPanel,
                "Vyberte města:", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                StringBuilder kody = new StringBuilder();
                StringBuilder nazvy = new StringBuilder();

                if (uhCheck.isSelected()) {
                    kody.append("uh");
                    nazvy.append("Uherské Hradiště");
                }
                if (kuCheck.isSelected()) {
                    if (!kody.isEmpty()) {
                        kody.append(",");
                        nazvy.append(", ");
                    }
                    kody.append("ku");
                    nazvy.append("Kunovice");
                }
                if (smCheck.isSelected()) {
                    if (!kody.isEmpty()) {
                        kody.append(",");
                        nazvy.append(", ");
                    }
                    kody.append("sm");
                    nazvy.append("Staré Město");
                }

                if (!kody.isEmpty()) {
                    Zastavky vlastniZastavky = new Zastavky(99, nazvy.toString());
                    vlastniZastavky.setMesta(kody.toString());
                    vlastniZastavky.otevriOkno();
                } else {
                    JOptionPane.showMessageDialog(null, "Vyberte alespoň jedno město!");
                }
            }
        });

        // přidání měst do menu "Zastávky"
        zastavky.add(zastUH);
        zastavky.add(zastKU);
        zastavky.add(zastSM);
        zastavky.add(new JSeparator());
        zastavky.add(zastVsechna);
        zastavky.add(zastVlastni);

        menuBar.add(zastavky);

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

                        int cisloLinky = Integer.parseInt(linka);

                        item.addActionListener(e -> {
                            new Linky_otevreni(cisloLinky).otevriOkno();
                        });

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
        // Nastavit výchozí barvu pozadí
        panel.setBackground(pozadiBarva);

        linkyBTN.addActionListener(e -> {
            // Otevření okna se Linky formuláře
            Linky_otevreni linkyOtevreni = new Linky_otevreni(0);
            }
        );

        zastavkyBTN.addActionListener(e -> {
            // Zobrazí dialog na výběr města
            Object[] mesta = {"Všechna města", "Uherské Hradiště", "Kunovice", "Staré Město"};
            Object vybraneMesto = JOptionPane.showInputDialog(
                null, "Vyberte město:", "Výběr města",
                JOptionPane.QUESTION_MESSAGE, null, mesta, mesta[0]);

            if (vybraneMesto != null) {
                int mestoKod = 0;
                if (vybraneMesto.equals("Všechna města")) mestoKod = 0;
                else if (vybraneMesto.equals("Uherské Hradiště")) mestoKod = 1;
                else if (vybraneMesto.equals("Kunovice")) mestoKod = 2;
                else if (vybraneMesto.equals("Staré Město")) mestoKod = 3;

                // Otevření okna se Zastavky formuláře
                Zastavky zastavky = new Zastavky(mestoKod, vybraneMesto.toString());
                zastavky.otevriOkno();
            }
        });

        cenikBTN.addActionListener(e -> {
            new Cenik().otevriOkno();
        });

        oficiálníStránkaButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://idzk.info/poulicka/"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Metoda na otevření druhého okna s výpisem
    public void otevriVypisovacieOkno(String nazev, String obsah) {
        JFrame vypisFrame = new JFrame(nazev);

        // Vytvoření panelu s textem
        JPanel vypisPanel = new JPanel(new java.awt.BorderLayout());
        vypisPanel.setBackground(pozadiBarva); // Použití globální barvy pozadí

        // TextArea s scrollbarem
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(obsah);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setBackground(pozadiBarva); // Použití globální barvy pozadí

        JScrollPane scrollPane = new JScrollPane(textArea);
        vypisPanel.add(scrollPane, java.awt.BorderLayout.CENTER);

        // Tlačítko zavřít
        JPanel tlacitkaPanel = new JPanel();
        tlacitkaPanel.setBackground(pozadiBarva); // Použití globální barvy pozadí
        JButton zavritBtn = new JButton("Zavřít");
        zavritBtn.addActionListener(e -> vypisFrame.dispose());
        tlacitkaPanel.add(zavritBtn);
        vypisPanel.add(tlacitkaPanel, java.awt.BorderLayout.SOUTH);

        vypisFrame.setContentPane(vypisPanel);
        vypisFrame.setSize(600, 500);
        vypisFrame.setLocationRelativeTo(null); // umístí okno do středu obrazovky
        vypisFrame.setVisible(true);
    }


    // Metoda na aktualizaci barvy pozadí všech otevřených oken
    public static void aktualizovatBarvuPozadi() {
        // Tato metoda bude volána při změně barvy pozadí
        // Všechna nově otevřená okna budou používat novou barvu automaticky
        // díky použití statické proměnné pozadiBarva
    }

    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(true);
        b.setBackground(new Color(70, 130, 220));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleLabel(JLabel l) {
        l.setFont(new Font("Segoe UI", Font.BOLD, 18));
        l.setForeground(new Color(30, 30, 30));
    }

    private void stylePanel(JPanel p) {
        p.setBackground(new Color(245, 247, 250));
    }


    public static void main(String[] args) throws IOException {

        // modernější default Swing styl
        UIManager.put("control", new Color(245, 247, 250));
        UIManager.put("info", new Color(245, 247, 250));
        UIManager.put("nimbusBase", new Color(50, 100, 200));
        UIManager.put("nimbusBlueGrey", new Color(220, 225, 230));
        UIManager.put("text", new Color(30, 30, 30));

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Evidence spojů Pouličky");
        App app = new App();

        frame.setContentPane(app.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(velikostX, velikostY);

        app.menu(frame);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


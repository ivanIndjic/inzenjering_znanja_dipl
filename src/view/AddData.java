package view;

import app.MyApp;
import model.IstorijaPregleda;
import model.Osoba;
import ucm.gaia.jcolibri.util.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddData {

    public JFrame mainF = new JFrame("Oftalmology");
    private IstorijaPregleda ip = new IstorijaPregleda();
    private String notes = "";

    public AddData(String navedeniSimptomi, String jmbg, String diagnose, Osoba o) {
        if (o!=null) {
            BufferedReader br = null;
            FileWriter pw = null;
            FileWriter pw2 = null;

            int counter = 0;
            String line = "";
            try {
                br = new BufferedReader(new InputStreamReader(FileIO.openFile("resources/disease.csv")));
                pw = new FileWriter("resources/disease.csv", true);
                pw2 = new FileWriter("resources/treatment.csv", true);
                while ((line = br.readLine()) != null) {
                    counter++;
                }
                String newLineInput = diagnose + ";" + navedeniSimptomi.replace(", ", ",") + ";" + String.valueOf(o.getGodine()) + ";" + o.getPol() + ";" + o.getRasa() + ";" + String.valueOf(counter) + "\n";
                pw.append(newLineInput);
                pw.flush();
            } catch (Exception f) {
                f.printStackTrace();
            }
        }


        JPanel main = new JPanel();
        JButton canc = new JButton("Cancel");
        JButton ok = new JButton("Save");
        JPanel pan = new JPanel();
        pan.setLayout(new FlowLayout());
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout());
        JLabel nb = new JLabel("Symptoms:             ");
        JTextArea nbT = new JTextArea();
        nbT.setEditable(false);
        nbT.setText(navedeniSimptomi);
        nbT.setPreferredSize(new Dimension(400, 50));
        nbT.setLineWrap(true);


        JLabel tr = new JLabel("Tretment:                ");
        JTextArea trT = new JTextArea();

        trT.setPreferredSize(new Dimension(400, 50));
        trT.setLineWrap(true);


        JLabel dn = new JLabel("Additional notes:      ");

        try {
            notes = notes.substring(0, notes.length() - 2);
        } catch (Exception e) {
        }
        JTextArea dnT = new JTextArea(notes);
        dnT.setLineWrap(true);
        dnT.setPreferredSize(new Dimension(400, 250));

        JLabel disease = new JLabel("Diagnosis:              ");
        JTextArea diseaseT = new JTextArea(diagnose);
        diseaseT.setEditable(false);
        diseaseT.setPreferredSize(new Dimension(400, 30));
        diseaseT.setLineWrap(true);

        JLabel dok = new JLabel("Doctor:                    ");
        String[] dokNiz = {"Nema", "Srdjan", "Milica", "Ivan", "Nina"};
        JComboBox doktori = new JComboBox(dokNiz);

        p1.add(nb);
        p1.add(nbT);
        p2.add(tr);
        p2.add(trT);
       // p2.add(scr);
        p3.add(dn);
        p3.add(dnT);
        p5.add(disease);
        p5.add(diseaseT);
        p4.add(dok);
        p4.add(doktori);

        JLabel lab1 = new JLabel("                  ");
        JLabel lab2 = new JLabel("                   ");
        pan.add(lab1);
        pan.add(canc);
        pan.add(ok);

        main.add(lab1);
        main.add(lab2);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (doktori.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter doctor's name!");
                    doktori.setBackground(Color.RED);
                } else {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    ip.setDatum(dtf.format(now));
                    ip.setSimptomi(nbT.getText());
                    ip.setDodatneNapomene(dnT.getText());
                    ip.setDoktor((String) doktori.getSelectedItem());
                    ip.setTretman(trT.getText());
                    ip.setBolest(diseaseT.getText());

                    Connection conn;
                    try {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inzenjering?useSSL=false", MyApp.username, MyApp.password);

                        String sql2 = "insert into IP (JMBG,Simptomi,Tretman,Doktor,Datum,DN,Disease) values (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                        pstmt2.setString(1, jmbg);
                        pstmt2.setString(2, ip.getSimptomi());
                        pstmt2.setString(3, ip.getTretman());
                        pstmt2.setString(4, ip.getDoktor());
                        pstmt2.setString(5, ip.getDatum());
                        pstmt2.setString(6, ip.getDodatneNapomene());
                        pstmt2.setString(7, ip.getBolest());


                        int updated2 = pstmt2.executeUpdate();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    mainF.dispose();
                    try {
                        MainFrame mf = new MainFrame();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        canc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                mainF.dispose();
            }
        });

        main.add(p1);
        main.add(p2);
        main.add(p3);
        main.add(p5);
        main.add(p4);
        main.add(pan);
        mainF.setSize(800, 560);
        mainF.add(main);
        mainF.setVisible(true);

    }


    public Object vrati() {
        return ip;
    }

}

package view;

import Actions.CbrApplication;
import app.RankingList;
import model.Osoba;
import model.Treatment;
import ucm.gaia.jcolibri.exception.ExecutionException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

public class SelectSymptoms extends JFrame {
    public static String bolest1 = "";
    public static String bolest2 = "";
    public static String bolest3 = "";
    public static ArrayList<String> selected = new ArrayList<>();
    String navedeniSimptomi = "";
    ArrayList<String> simpto = new ArrayList<>();
    private JFrame mainFrame = new JFrame("Ophthalmology");
    private JPanel checkPanel = new JPanel();
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel descPanel = new JPanel(new BorderLayout());
    private ArrayList<HashMap<String, Map<String, Float>>> sveRangListe = new ArrayList<HashMap<String, Map<String, Float>>>();

    private String diOp = "";
    private String paOp = "";
    private String reOp = "";
    private String doOp = "";
    private String laOp = "";
    private String foOp = "";
    private String swOp = "";
    private String clOp = "";
    private String blOp = "";
    private String spOp = "";
    private String buOp = "";
    private String whOp = "";
    private String itOp = "";

    public SelectSymptoms(Osoba o, String jmbg) {

        try {
            int rasaIndex = 0;
            int polIndex = 0;
            int godIndex = 0;

            if (o.getRasa().equals("Black")) {
                rasaIndex = 0;
            } else if (o.getRasa().equals("Hispanic")) {
                rasaIndex = 1;
            } else if (o.getRasa().equals("White")) {
                rasaIndex = 2;
            } else {
                rasaIndex = 3;
            }

            if (o.getPol().equals("Male")) {
                polIndex = 0;
            } else {
                polIndex = 1;
            }

            int g = o.getGodine();
            if (g == 0) {
                godIndex = 0;
            } else if (g >= 1 && g < 5) {
                godIndex = 1;
            } else if (g >= 5 && g < 15) {
                godIndex = 2;
            } else if (g >= 15 && g < 30) {
                godIndex = 3;
            } else if (g >= 30 && g < 45) {
                godIndex = 4;
            } else if (g >= 45 && g < 60) {
                godIndex = 5;
            } else if (g >= 60 && g < 75) {
                godIndex = 6;
            } else if (g >= 75) {
                godIndex = 7;
            }

            Border blackline = BorderFactory.createLineBorder(Color.BLACK);
            TitledBorder title = BorderFactory.createTitledBorder(blackline, "Description of symptom");
            title.setTitleJustification(TitledBorder.CENTER);

            Border blackline2 = BorderFactory.createLineBorder(Color.BLACK);
            TitledBorder title2 = BorderFactory.createTitledBorder(blackline2, "Probability of diseases");
            title2.setTitleJustification(TitledBorder.CENTER);

            JTextArea opSimptoma = new JTextArea();
            opSimptoma.setBorder(title);
            opSimptoma.setEditable(false);
            opSimptoma.setSize(new Dimension(500, 200));
            opSimptoma.setWrapStyleWord(true);
            opSimptoma.setLineWrap(true);
            descPanel.add(opSimptoma, BorderLayout.NORTH);

            JTextArea konacneVrv = new JTextArea();
            konacneVrv.setBorder(title2);
            konacneVrv.setEditable(false);
            descPanel.add(konacneVrv, BorderLayout.CENTER);
            ImageIcon donIm2 = new ImageIcon("resources/done.png");
            Image doneImg2 = donIm2.getImage(); // transform it
            Image newDoneImg2 = doneImg2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            donIm2 = new ImageIcon(newDoneImg2);
            JButton done = new JButton("DoneCBR", donIm2);
            done.setVisible(false);
            checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
            checkPanel.setSize(new Dimension(500, 450));
            checkPanel.setPreferredSize(new Dimension(500, 450));
            JCheckBox dim_vi = new JCheckBox("Diminished vision");
            JCheckBox pain_eye = new JCheckBox("Pain in eye");
            JCheckBox redness = new JCheckBox("Eye redness");
            JCheckBox dob_vi = new JCheckBox("Double vision");
            JCheckBox lacr = new JCheckBox("Lacrimation");
            JCheckBox forig = new JCheckBox("Foreign body sensation in eye");
            JCheckBox swol = new JCheckBox("Swollen eye");
            JCheckBox clo = new JCheckBox("Cloudy eye");
            JCheckBox bli = new JCheckBox("Blindness");
            JCheckBox spots = new JCheckBox("Spots or clouds in vision");
            JCheckBox burn = new JCheckBox("Eye burns or stings");
            JCheckBox white = new JCheckBox("White discharge from eye");
            JCheckBox itchi = new JCheckBox("Itchiness of eye");
            for (String s : selected) {
                if (s.equals("diminished_vision")) {
                    dim_vi.setSelected(true);
                } else if (s.equals("swollen_eye")) {
                    swol.setSelected(true);
                } else if (s.equals("cloudy_eye")) {
                    clo.setSelected(true);
                } else if (s.equals("blindness")) {
                    bli.setSelected(true);
                } else if (s.equals("spots_or_clouds_in_vision")) {
                    spots.setSelected(true);
                } else if (s.equals("eye_burns_or_stings")) {
                    burn.setSelected(true);
                } else if (s.equals("white_discharge_from_eye")) {
                    white.setSelected(true);
                } else if (s.equals("itchiness_of_eye")) {
                    itchi.setSelected(true);
                } else if (s.equals("pain_in_eye")) {
                    pain_eye.setSelected(true);
                } else if (s.equals("eye_redness")) {
                    redness.setSelected(true);
                } else if (s.equals("double_vision")) {
                    dob_vi.setSelected(true);
                } else if (s.equals("lacrimation")) {
                    lacr.setSelected(true);
                } else if (s.equals("foreign_body_sensation_in_eye")) {
                    forig.setSelected(true);
                }
            }

            ImageIcon fin = new ImageIcon("resources/finish.png");
            Image finIm = fin.getImage(); // transform// it
            Image newimgF = finIm.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            fin = new ImageIcon(newimgF);
            itchi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(itOp);
                }
            });
            white.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(whOp);
                }
            });
            burn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(buOp);
                }
            });
            spots.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(spOp);
                }
            });
            bli.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(blOp);
                }
            });
            clo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(clOp);
                }
            });
            forig.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(foOp);
                }
            });
            swol.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(swOp);
                }
            });
            lacr.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(laOp);
                }
            });
            dob_vi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(doOp);
                }
            });

            redness.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(reOp);
                }
            });

            pain_eye.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(paOp);
                }
            });

            dim_vi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    opSimptoma.setText(diOp);
                }
            });

            String[] diseaseStrings = {"conjunctivitis", "blepharitis", "chronic glaucoma", "cataract",
                    "macular degeneration", "dry eye of unknown cause", "eye alignment disorder", "corneal abrasion",
                    "cornea infection", "retinal detachment", "optic neuritis", "iridocyclitis",
                    "subconjunctival hemorrhage", "floaters"
            };
            JPanel combo = new JPanel();
            combo.setVisible(false);
            // combo.setLayout(new FlowLayout());
            combo.setLayout(new BoxLayout(combo, BoxLayout.X_AXIS));
            combo.setSize(new Dimension(400, 40));
            combo.setPreferredSize(new Dimension(400, 40));
            JComboBox diseaseList = new JComboBox(diseaseStrings);
            diseaseList.setSize(new Dimension(250, 20));
            diseaseList.setPreferredSize(new Dimension(250, 20));
            diseaseList.setSelectedIndex(0);
            JLabel diagnosis = new JLabel("      Diagnosis:   ");
            combo.add(diagnosis);
            combo.add(diseaseList);



            JButton cbrBtn = new JButton("CBR ", fin);
            cbrBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    // TODO Auto-generated method stub

                    done.setVisible(true);
                    combo.setVisible(false);
                    simpto = new ArrayList<String>();
                    if (dim_vi.isSelected())
                        simpto.add("diminished vision");
                    if (pain_eye.isSelected())
                        simpto.add("pain in eye");
                    if (redness.isSelected())
                        simpto.add("eye redness");
                    if (dob_vi.isSelected())
                        simpto.add("double vision");
                    if (lacr.isSelected())
                        simpto.add("lacrimation");
                    if (forig.isSelected())
                        simpto.add("foreign body sensation in eye");
                    if (swol.isSelected())
                        simpto.add("swollen eye");
                    if (clo.isSelected())
                        simpto.add("cloudy eye");
                    if (bli.isSelected())
                        simpto.add("Blindness");
                    if (spots.isSelected())
                        simpto.add("spots or clouds in vision");
                    if (burn.isSelected())
                        simpto.add("eye burns or stings");
                    if (white.isSelected())
                        simpto.add("white discharge from eye");
                    if (itchi.isSelected())
                        simpto.add("itchiness of eye");

                    CbrApplication a = new CbrApplication();
                    a.mainS(o, simpto);

                    konacneVrv.setText(CbrApplication.printOfProbabilitiesCBR());
                    RankingList rg = new RankingList();
                    boolean daljaIspBol = CbrApplication.proveraRazlikeVerovatnocaZaDaljaIspitivanjaDouble(CbrApplication.sortedFinalMap);

                }
            });

            JLabel simpt = new JLabel("Symptoms: ");
            checkPanel.add(simpt);
            checkPanel.add(itchi);
            checkPanel.add(white);
            checkPanel.add(burn);
            checkPanel.add(spots);
            checkPanel.add(bli);
            checkPanel.add(clo);
            checkPanel.add(swol);
            checkPanel.add(forig);
            checkPanel.add(lacr);
            checkPanel.add(dim_vi);
            checkPanel.add(pain_eye);
            checkPanel.add(redness);
            checkPanel.add(dob_vi);

            Treatment t = new Treatment();
            done.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {

                    String finn = konacneVrv.getText();
                    if (!finn.split(":")[0].trim().equals("Other")) {
                        t.setDisease(finn.split(":")[0].trim().toLowerCase().replace(" ", "_"));
                    } else {
                        t.setDisease(finn.split(":")[2].trim().toLowerCase().replace(" ", "_"));
                    }
                    t.setAge(o.getGodine());

                    // TODO Auto-generated method stub
                    simpto = new ArrayList<String>();
                    if (dim_vi.isSelected())
                        simpto.add("diminished vision");
                    if (pain_eye.isSelected())
                        simpto.add("pain in eye");
                    if (redness.isSelected())
                        simpto.add("eye redness");
                    if (dob_vi.isSelected())
                        simpto.add("double vision");
                    if (lacr.isSelected())
                        simpto.add("lacrimation");
                    if (forig.isSelected())
                        simpto.add("foreign body sensation in eye");
                    if (swol.isSelected())
                        simpto.add("swollen eye");
                    if (clo.isSelected())
                        simpto.add("cloudy eye");
                    if (bli.isSelected())
                        simpto.add("blindness");
                    if (spots.isSelected())
                        simpto.add("spots of clouds in vision");
                    if (burn.isSelected())
                        simpto.add("eye burns of stings");
                    if (white.isSelected())
                        simpto.add("white discharge from eye");
                    if (itchi.isSelected())
                        simpto.add("itchiness of eye");
                    navedeniSimptomi = "";
                    for (String simpton : simpto) {
                        navedeniSimptomi += simpton;
                        navedeniSimptomi += ", ";
                    }
                    try {
                        navedeniSimptomi = navedeniSimptomi.substring(0, navedeniSimptomi.length() - 2);
                    } catch (Exception e) {
                    }
                    AddData addDataFrame = new AddData(navedeniSimptomi, jmbg, bolest1, o);
                    mainFrame.dispose();

                }
            });


            JPanel ispitivanja = new JPanel();
            ispitivanja.setLayout(new FlowLayout());
            ispitivanja.add(cbrBtn);
            ispitivanja.add(done);
            checkPanel.add(ispitivanja);


            checkPanel.add(combo);

            ImageIcon water = new ImageIcon("resources/eyeIcon.png");
            Image image = water.getImage(); // transform it
            Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            water = new ImageIcon(newimg);
            JButton back = new JButton("Back", water);
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    try {
                        MainFrame mf = new MainFrame();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    mainFrame.dispose();
                }
            });




            JPanel tug = new JPanel(new BorderLayout());
            tug.add(checkPanel, BorderLayout.WEST);
            tug.add(descPanel, BorderLayout.EAST);
            JPanel proba = new JPanel(new BorderLayout());
            proba.add(tug, BorderLayout.NORTH);
            //proba.add(pr, BorderLayout.SOUTH);
            mainPanel.add(proba, BorderLayout.CENTER);
            mainPanel.add(back, BorderLayout.SOUTH);
            mainFrame.add(mainPanel);
            mainFrame.setSize(1100, 600);

            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            mainFrame.setVisible(true);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
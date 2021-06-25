package cs3500.frame;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.view.IMEView;
import cs3500.view.TextualIMEView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

public class IMEFrame extends JFrame implements ActionListener, ItemListener,
    ListSelectionListener {

  // the dimensions of a typical computer screen
  private static final int SCREEN_WIDTH = 1200;
  private static final int SCREEN_HEIGHT = 800;

  // to represent the model--the images to be manipulated
  private final IMultiLayerModel mdl;
  // to represent the scriptable controller embedded in the GUI
  private final IMultiLayerIMEController scrptCtrlr;
  // to store interactively-scripted commands
  private final Readable scriptIn;
  // to represent the embedded text view
  private final IMEView txtView;
  // to store output from the view
  private final Appendable out;
  // to store the GUI elements
  private final JPanel mainPanel;
  // to store the menu ribbon elements
  private JLabel menusLabel;
  private JPanel menusPanel;
  private JLabel fileMenuLabel;
  private JLabel editMenuLabel;
  private JLabel transformationsMenuLabel;
  private JLabel programmaticImagesMenuLabel;


  public IMEFrame() {
    super();
    setTitle("Image Manipulation and Enhancement");
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    this.mdl = new MultiLayerModelImpl();
    this.scriptIn = new StringReader("");
    this.out = new StringBuilder();
    this.txtView = new TextualIMEView(mdl, out);
    this.scrptCtrlr =
        MultiLayerIMEControllerImpl.controllerBuilder()
            .model(mdl)
            .readable(scriptIn)
            .appendable(out)
            .view(txtView)
            .buildController();

    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BorderLayout());

    // setup drop down menus
    this.dropDownMenus();
    // setup layers panel
    this.layersPanel();
    // setup console panel
    this.console();
    // setup script area panel
    this.scriptArea();
    // setup image area panel.
    this.imageArea();

    //~~~~~~~~~~~~~~~~~~~~~~~~~//
    add(mainPanel);
  }

  /**
   * Initializes the dropdown menus at the top of the gui.
   */
  private void dropDownMenus() {
    // setting up the dropdown menus ribbon:
    this.menusLabel = new JLabel("Menus Ribbon");
    this.menusPanel = new JPanel();
    this.menusPanel.setLayout(new FlowLayout());

    // to set up the file menu
    this.fileMenuLabel = new JLabel("File");
    menusPanel.add(fileMenuLabel);
    // fileMenuPanel.add(fileMenuLabel);
    String[] fileOptions = {"Import", "Import layers", "Export", "Export layers"};
    JComboBox<String> fileMenuBox = new JComboBox<>();
    // event listeners
    fileMenuBox.setActionCommand("file");
    fileMenuBox.addActionListener(this);
    for (int i = 0; i < fileOptions.length; i++) {
      fileMenuBox.addItem(fileOptions[i]);
    }
    menusPanel.add(fileMenuBox);
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // to set up the edit menu
    this.editMenuLabel = new JLabel("Edit");
    menusPanel.add(editMenuLabel);
    String[] editOptions = {"Undo", "Redo", "Save", "New layer", "Set current layer"};
    JComboBox<String> editMenuBox = new JComboBox<>();
    editMenuBox.setActionCommand("edit");
    editMenuBox.addActionListener(this);
    for (int i = 0; i < editOptions.length; i++) {
      editMenuBox.addItem(editOptions[i]);
    }
    menusPanel.add(editMenuBox);
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // to set up the transformations menu
    this.transformationsMenuLabel = new JLabel("Transformations");
    menusPanel.add(transformationsMenuLabel);
    String[] transformationsOptions = {"Sepia", "Greyscale", "Blur", ""
        + "Sharpen", "Mosaic...", "Downscale..."};
    JComboBox<String> transformationsMenuBox = new JComboBox<>();
    transformationsMenuBox.setActionCommand("transformations");
    transformationsMenuBox.addActionListener(this);
    for (int i = 0; i < transformationsOptions.length; i++) {
      transformationsMenuBox.addItem(transformationsOptions[i]);
    }
    menusPanel.add(transformationsMenuBox);
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // to set up the programmatic images menu
    this.programmaticImagesMenuLabel = new JLabel("Programmatic Images");
    menusPanel.add(programmaticImagesMenuLabel);
    String[] programmaticImagesOptions = {"Checkerboard...", "Noise..."};
    JComboBox<String> programmaticImagesMenuBox = new JComboBox<>();
    programmaticImagesMenuBox.setActionCommand("programmatic images");
    programmaticImagesMenuBox.addActionListener(this);
    for (int i = 0; i < programmaticImagesOptions.length; i++) {
      programmaticImagesMenuBox.addItem(programmaticImagesOptions[i]);
    }
    menusPanel.add(programmaticImagesMenuBox);

    mainPanel.add(menusPanel, BorderLayout.PAGE_START);
  }

  /**
   * Initializes the panel for the layers.
   */
  private void layersPanel() {
    // setting up the layers panel:
    JPanel layersPanel = new JPanel();
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    layersPanel.setBorder(BorderFactory.createTitledBorder("LAYERS"));
    JPanel layersButtonsPanel = new JPanel();
    layersButtonsPanel.setLayout(new FlowLayout());
    JButton swapBtn = new JButton("Swap");
    swapBtn.setActionCommand("swap");
    swapBtn.addActionListener(this);
    layersButtonsPanel.add(swapBtn);
    JButton newBtn = new JButton("New layer");
    newBtn.setActionCommand("new");
    newBtn.addActionListener(this);
    layersButtonsPanel.add(newBtn);

    layersPanel.add(layersButtonsPanel);

    for (int i = 0; i < 5; i++) {
      layersPanel.add(this.createLayerRow("layer " + i, i, true));
    }

    mainPanel.add(layersPanel, BorderLayout.LINE_START);
  }

  /**
   * Initializes the panel for the console.
   */
  private void console() {
    // setting up the console
    JPanel consolePanel = new JPanel();
    JTextArea consoleTxt = new JTextArea(this.out.toString() + " TEST");
    consolePanel.add(consoleTxt);

    mainPanel.add(consolePanel, BorderLayout.PAGE_END);
  }

  /**
   * Initializes the panel for the script.
   */
  private void scriptArea() {
    // setting up the script area
    JPanel scriptPanel = new JPanel();
    JTextArea scriptArea = new JTextArea("SCRIPT HERE");
    scriptPanel.add(scriptArea);
    mainPanel.add(scriptPanel, BorderLayout.LINE_END);
  }

  /**
   * Initializes the panel for the main image to appear.
   */
  private void imageArea() {
    // Scrolling image panel
    this.mdl.load(new JPEGFile().importImage("src/lakeImage.jpg"));
    BufferedImage image = this.mdl.getImage().getBufferedImage();
    JPanel imagePanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
      }
    };
    imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    JScrollPane scrollPane = new JScrollPane(imagePanel);
    mainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }


  /**
   * Todo
   *
   * @param layerName
   * @param layerNum
   * @param isVisible
   * @return
   * @throws IllegalArgumentException
   */
  private JPanel createLayerRow(String layerName, int layerNum, boolean isVisible)
      throws IllegalArgumentException {
    JPanel thisRow = new JPanel();
    thisRow.setLayout(new FlowLayout());

    JButton layerBtn = new JButton(layerName + " | " + layerNum);
    layerBtn.setActionCommand("layer " + layerNum);
    layerBtn.addActionListener(this);
    JCheckBox layerCB = new JCheckBox("visible?", isVisible);
    layerCB.setActionCommand("layer " + layerNum + " check box");
    layerCB.addActionListener(this);
    // REPLACE THIS WITH A DELETE ICON
    JButton deleteLayerBtn = new JButton("delete");
    deleteLayerBtn.setActionCommand("delete layer " + layerNum);
    deleteLayerBtn.addActionListener(this);

    thisRow.add(layerBtn);
    thisRow.add(layerCB);
    thisRow.add(deleteLayerBtn);

    return thisRow;

  }

}
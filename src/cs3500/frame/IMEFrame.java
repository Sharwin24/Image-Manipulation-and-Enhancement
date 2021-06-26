package cs3500.frame;

import cs3500.Utility;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;


import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.operation.Downscale;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticimages.BWNoise;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.model.programmaticimages.Noise;
import cs3500.model.programmaticimages.PureNoise;
import cs3500.model.programmaticimages.RainbowNoise;
import cs3500.view.IMEView;
import cs3500.view.TextualIMEView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;

import java.awt.Graphics;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jdk.jshell.execution.Util;

public class IMEFrame extends JFrame implements ActionListener, ItemListener,
    ListSelectionListener {


  private static final String WORKING_DIR = System.getProperty("user.dir");
  // the dimensions of a typical computer screen
  private static final int SCREEN_WIDTH = 1200;
  private static final int SCREEN_HEIGHT = 800;

  // to represent the model--the images to be manipulated
  private IMultiLayerModel mdl;
  // to represent the scriptable controller embedded in the GUI
  private IMultiLayerIMEController scrptCtrlr;
  private JButton runScriptBtn;
  // to store interactively-scripted commands
  private Readable scriptIn;
  // to represent the embedded text view
  private IMEView txtView;
  // to store output from the view
  private Appendable out;
  // to store the GUI elements
  private JPanel mainPanel;
  // to store the menu ribbon elements
  private JLabel menusLabel;
  private JPanel menusPanel;
  private JLabel fileMenuLabel;
  private JLabel editMenuLabel;
  private JLabel transformationsMenuLabel;
  private JLabel programmaticImagesMenuLabel;
  private final JPanel layersPanel = new JPanel();


  private JMenuBar menuRibbon;
  // Image panel architecture
  private BufferedImage currentImage;
  private JPanel imagePanel;
  private JScrollPane imageScrollPanel;


  // the architecture for the file menu
  private JMenu fileMenu;
  private JMenu exportSubmenu;
  private JMenu importSubmenu;

  // the architecture for the edit menu
  private JMenu editMenu;

  // the architecture for the transformations menu
  private JMenu transformationsMenu;

  // the architecture for the programmatic images menu
  private JMenu programmaticImagesMenu;

  // to handle input and display it
  private JLabel inputDisplay;

  // to handle text in the console
  private JPanel consolePanel;
  private JTextArea consoleTxt;


  private JLabel imgLabel = new JLabel("");

  private JPanel fileOpenPanel;

  private JPanel inputDialogPanel;

  private JPanel colorChooserDisplay = new JPanel();

  private Map<String, IGUICommand> actionsMap = this.initActionsMap();

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

    // setup layers panel
    this.layersPanel();
    // setup console panel
    this.console();
    // setup script area panel
    this.scriptArea();
    // setup image area panel.
    this.imageArea();
    // setup the menu bar
    this.menuRibbon();

    /**
     * REORGANIZE
     */
    this.fileChooser();

    //~~~~~~~~~~~~~~~~~~~~~~~~~//
    add(mainPanel);

    // this.actionsMap = this.initActionsMap();

    //mdl.load(new PNGFile().importImage("res/MPP.png"));
  }

  /**
   * Initializes the dropdown menu bar at the top of the gui.
   */
  private void menuRibbon() {
    this.menuRibbon = new JMenuBar();

    // the file menu
    this.fileMenu = new JMenu("File");
    this.fileMenu.setMnemonic(KeyEvent.VK_F);
    this.fileMenu.getAccessibleContext().setAccessibleDescription("This menu handles file I/O");

    // the import submenu
    this.importSubmenu = new JMenu("Import...");
    importSubmenu.setMnemonic(KeyEvent.VK_I);
    JMenuItem importOneImageItem = new JMenuItem("Import one image");
    importOneImageItem.setMnemonic(KeyEvent.VK_O);
    importOneImageItem.setActionCommand("import one");
    importOneImageItem.addActionListener(this);
    importSubmenu.add(importOneImageItem);

    JMenuItem importAllImagesItem = new JMenuItem("Import a multi-layered image");
    importAllImagesItem.setMnemonic(KeyEvent.VK_M);
    importAllImagesItem.setActionCommand("import all");
    importAllImagesItem.addActionListener(this);
    importSubmenu.add(importAllImagesItem);

    fileMenu.add(importSubmenu);

    // the export submenu
    this.exportSubmenu = new JMenu("Export...");
    exportSubmenu.setMnemonic(KeyEvent.VK_E);
    JMenuItem exportOneImageItem = new JMenuItem("export one layer of an image");
    exportOneImageItem.setMnemonic(KeyEvent.VK_O);
    exportOneImageItem.setActionCommand("export one");
    exportOneImageItem.addActionListener(this);
    exportSubmenu.add(exportOneImageItem);

    JMenuItem exportAllImagesItem = new JMenuItem("export a multi-layered image");
    exportAllImagesItem.setMnemonic(KeyEvent.VK_M);
    exportAllImagesItem.setActionCommand("export all");
    exportAllImagesItem.addActionListener(this);
    exportSubmenu.add(exportAllImagesItem);

    fileMenu.add(exportSubmenu);

    menuRibbon.add(fileMenu);

    // the edit menu
    this.editMenu = new JMenu("Edit");
    editMenu.setMnemonic(KeyEvent.VK_E);
    editMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides low-level functionality to manipulate images");

    JMenuItem undoItem = new JMenuItem("Undo");
    undoItem.setMnemonic(KeyEvent.VK_U);
    undoItem.setActionCommand("undo");
    undoItem.addActionListener(this);
    editMenu.add(undoItem);

    JMenuItem redoItem = new JMenuItem("Redo");
    redoItem.setMnemonic(KeyEvent.VK_R);
    redoItem.setActionCommand("redo");
    redoItem.addActionListener(this);
    editMenu.add(redoItem);

    JMenuItem newLayerItem = new JMenuItem("New layer");
    newLayerItem.setMnemonic(KeyEvent.VK_N);
    newLayerItem.setActionCommand("new layer");
    newLayerItem.addActionListener(this);
    editMenu.add(newLayerItem);

    JMenuItem setCurrentLayerItem = new JMenuItem("Set current layer");
    setCurrentLayerItem.setMnemonic(KeyEvent.VK_C);
    setCurrentLayerItem.setActionCommand("set current layer");
    setCurrentLayerItem.addActionListener(this);
    editMenu.add(setCurrentLayerItem);

    menuRibbon.add(editMenu);

    // the transformations menu
    this.transformationsMenu = new JMenu("Transformations");
    transformationsMenu.setMnemonic(KeyEvent.VK_T);
    transformationsMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides the functionality to apply one of the following transformations to the"
            + " current layer");

    JMenuItem sepiaItem = new JMenuItem("Sepia");
    sepiaItem.setMnemonic(KeyEvent.VK_E);
    sepiaItem.setActionCommand("sepia");
    sepiaItem.addActionListener(this);
    transformationsMenu.add(sepiaItem);

    JMenuItem greyScaleItem = new JMenuItem("Greyscale");
    greyScaleItem.setMnemonic(KeyEvent.VK_G);
    greyScaleItem.setActionCommand("greyscale");
    greyScaleItem.addActionListener(this);
    transformationsMenu.add(greyScaleItem);

    JMenuItem blurItem = new JMenuItem("Blur");
    blurItem.setMnemonic(KeyEvent.VK_B);
    blurItem.setActionCommand("blur");
    blurItem.addActionListener(this);
    transformationsMenu.add(blurItem);

    JMenuItem sharpenItem = new JMenuItem("Sharpen");
    sharpenItem.setMnemonic(KeyEvent.VK_H);
    sharpenItem.setActionCommand("sharpen");
    sharpenItem.addActionListener(this);
    transformationsMenu.add(sharpenItem);

    JMenuItem mosaicItem = new JMenuItem("Mosaic...");
    mosaicItem.setMnemonic(KeyEvent.VK_M);
    mosaicItem.setActionCommand("mosaic");
    mosaicItem.addActionListener(this);
    transformationsMenu.add(mosaicItem);
    // TODO: ADD DIALOG POPUP HERE TO GET SEEDS
    //JPanel dialogBoxesPanel = new JPanel(); // ???
    //dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("taking input..."));
    //dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel,BoxLayout.PAGE_AXIS));
    //mainPanel.add(dialogBoxesPanel);

    inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());
    //dialogBoxesPanel.add(mosaicInputDialogPanel);

    // inputDisplay = new JLabel("Default");
    // mosaicInputDialogPanel.add(inputDisplay);

    JMenuItem downscaleItem = new JMenuItem("Downscale...");
    downscaleItem.setMnemonic(KeyEvent.VK_D);
    downscaleItem.setActionCommand("downscale");
    downscaleItem.addActionListener(this);
    transformationsMenu.add(downscaleItem);
    // TODO: ADD DIALOG POPUP HERE TO GET DIMS

    menuRibbon.add(transformationsMenu);

    // the programmatic images menu
    this.programmaticImagesMenu = new JMenu("Programmatic Images");
    programmaticImagesMenu.setMnemonic(KeyEvent.VK_P);
    programmaticImagesMenu.getAccessibleContext().setAccessibleDescription(
        "This menu provides the functionality to set the current layer to one of a "
            + "few programmatically created images.");

    JMenuItem checkerBoardItem = new JMenuItem("Checkerboard...");
    checkerBoardItem.setMnemonic(KeyEvent.VK_C);
    checkerBoardItem.setActionCommand("checkerboard");
    checkerBoardItem.addActionListener(this);
    programmaticImagesMenu.add(checkerBoardItem);
    // TODO: ADD DIALOG POPUP TO GET DIMENSIONS

    // the noise images submenu
    JMenu noiseSubMenu = new JMenu("Noise Images");
    noiseSubMenu.setMnemonic(KeyEvent.VK_N);
    noiseSubMenu.getAccessibleContext().setAccessibleDescription(
        "Creates a noise image wherein each pixel is randomly selected from an assortment of "
            + "colors");

    JMenuItem pureNoiseItem = new JMenuItem("Pure noise");
    pureNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel's color is completely randomized.");
    pureNoiseItem.setMnemonic(KeyEvent.VK_P);
    pureNoiseItem.setActionCommand("pure noise");
    pureNoiseItem.addActionListener(this);
    noiseSubMenu.add(pureNoiseItem);

    JMenuItem bwNoiseItem = new JMenuItem("Black and white noise...");
    bwNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is either black or white");
    bwNoiseItem.setMnemonic(KeyEvent.VK_B);
    bwNoiseItem.setActionCommand("bw noise");
    bwNoiseItem.addActionListener(this);
    noiseSubMenu.add(bwNoiseItem);

    JMenuItem rainbowNoiseItem = new JMenuItem("Rainbow noise...");
    rainbowNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is one of the colors of the rainbow: "
            + "red, orange, yellow, green, blue, indigo, or violet.");
    rainbowNoiseItem.setMnemonic(KeyEvent.VK_R);
    rainbowNoiseItem.setActionCommand("rainbow noise");
    rainbowNoiseItem.addActionListener(this);
    noiseSubMenu.add(rainbowNoiseItem);

    JMenuItem customNoiseItem = new JMenuItem("Custom...");
    customNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is randomly selected from a list of colors that "
            + "is specified via a color picker.");
    customNoiseItem.setMnemonic(KeyEvent.VK_C);
    customNoiseItem.setActionCommand("custom noise");
    customNoiseItem.addActionListener(this);
    noiseSubMenu.add(customNoiseItem);
    // TODO: ADD DIALOG TO BUILD LIST OF COLORS VIA COLOR PICKER

    programmaticImagesMenu.add(noiseSubMenu);

    menuRibbon.add(programmaticImagesMenu);

    // finally,
    this.mainPanel.add(menuRibbon, BorderLayout.PAGE_START);
  }


  /**
   * Initializes the panel for the layers.
   */
  private void layersPanel() {
    // setting up the layers panel:
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
    layersPanel.add(this.createLayerRow("layer ", 0, true));

    mainPanel.add(layersPanel, BorderLayout.LINE_START);
  }

  /**
   * Initializes the panel for the console.
   */
  private void console() {
    // setting up the console
    consolePanel = new JPanel();
    consoleTxt = new JTextArea(this.out.toString() + " TEST");
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

    runScriptBtn = new JButton("Run script");
    runScriptBtn.addActionListener(this);
    runScriptBtn.setActionCommand("run script");
    scriptPanel.add(runScriptBtn);

    mainPanel.add(scriptPanel, BorderLayout.LINE_END);


  }

  /**
   * Initializes the panel for the main image to appear.
   */
  private void imageArea() {

    this.imageScrollPanel = new JScrollPane(this.imgLabel);
    imageScrollPanel.setPreferredSize(new Dimension(700, 700));

    mainPanel.add(imageScrollPanel, BorderLayout.CENTER);
  }

  /**
   * Initializes the file chooser for the GUI.
   */
  private void fileChooser() {
    fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    inputDialogPanel.add(fileOpenPanel);


  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (!(actionsMap.containsKey(e.getActionCommand()))) {
      return; // throw new IllegalArgumentException("command not recognized");
    }

    actionsMap.get(e.getActionCommand()).execute();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }


  /**
   * Creates a new layer row given the parameters for the layer.
   *
   * @param layerName the name of the layer.
   * @param layerNum  the number of the layer.
   * @param isVisible the visibility of the layer.
   * @return a {@link JPanel} with the new layer.
   * @throws IllegalArgumentException if arguments are invalid or null.
   */
  private JPanel createLayerRow(String layerName, int layerNum, boolean isVisible)
      throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
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


  /**
   * Returns a Hashmap of the string commands to the command objects.
   *
   * @return a {@link HashMap} of the commands from a string to their objects.
   */
  private Map<String, IGUICommand> initActionsMap() {

    Map<String, IGUICommand> actionsMap = new HashMap<>();

//    actionsMap.putIfAbsent("file", new FileMenuCommand());
//    actionsMap.putIfAbsent("edit", new EditMenuCommand());
//    actionsMap.putIfAbsent("transformations", new TransformationsMenuCommand());
//    actionsMap.putIfAbsent("programmatic images", new ProgrammaticImagesMenuCommand());
//    actionsMap.putIfAbsent("swap", new SwapCommand());
//    actionsMap.putIfAbsent("new", new NewLayerCommand());

    actionsMap.putIfAbsent("new", new NewLayerCommand());
    actionsMap.putIfAbsent("mosaic", new GUIMosaicCommand());
    actionsMap.putIfAbsent("import one", new ImportOneCommand());
    actionsMap.putIfAbsent("sepia", new SepiaCommand());
    actionsMap.putIfAbsent("greyscale", new GreyScaleCommand());
    actionsMap.putIfAbsent("sharpen", new SharpenCommand());
    actionsMap.putIfAbsent("blur", new BlurCommand());
    actionsMap.putIfAbsent("downscale", new DownScaleCommand());
    actionsMap.putIfAbsent("export one", new ExportOneCommand());
    actionsMap.putIfAbsent("set current layer", new CurrentLayerCommand());
    actionsMap.putIfAbsent("checkerboard", new CheckerBoardCommand());
    actionsMap.putIfAbsent("delete", new DeleteLayerCommand());
    actionsMap.putIfAbsent("bw noise", new BWNoiseCommand());
    actionsMap.putIfAbsent("rainbow noise", new RainbowNoiseCommand());
    actionsMap.putIfAbsent("pure noise", new PureNoiseCommand());
    actionsMap.putIfAbsent("custom noise", new CustomNoiseCommand());
    actionsMap.putIfAbsent("undo", new UndoCommand());

    return actionsMap;
  }

  /**
   * Returns a HashMap of the format objects to their strings.
   *
   * @return a {@link HashMap} of the format strings and objects.
   */
  private static Map<String, IFileFormat> initFormatsMap() {
    final Map<String, IFileFormat> formatsMap = new HashMap<>();

    formatsMap.putIfAbsent("jpg", new JPEGFile());
    formatsMap.putIfAbsent("jpeg", new JPEGFile());
    formatsMap.putIfAbsent("ppm", new PPMFile());
    formatsMap.putIfAbsent("png", new PNGFile());

    return formatsMap;

  }


  /**
   * Interface for GUI Commands to be called inside the {@code actionPerformed()} method.
   */
  private interface IGUICommand {

    /**
     * Executes the GUI command.
     */
    void execute();

  }

  private class NewLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.addLayer();
      layersPanel.add(createLayerRow("NEWLAYER", mdl.getLayers().size(),
          true));
    }
  }

  private class SwapLayersCommand implements IGUICommand {

    @Override
    public void execute() {
      // ADD GUI I/O FOR GETTING LAYERS TO SWAP VIA A POPUP
      // mdl.swapLayers();
    }
  }

  /**
   * Class for Importing/Loading an image command.
   */
  private class ImportOneCommand implements IGUICommand {

    @Override
    public void execute() {
      FileDialog dialog = new FileDialog((Frame) null, "Select File");
      dialog.setMode(FileDialog.LOAD);
      dialog.setVisible(true);
      String absolutePath = dialog.getDirectory() + dialog.getFile();
      // ensure valid fileType
      IFileFormat fileFormat = null;
      Map<String, IFileFormat> formats = initFormatsMap();
      boolean validFileTypeSelected = false;
      for (String s : formats.keySet()) {
        if (absolutePath.endsWith(s)) {
          validFileTypeSelected = true;
          fileFormat = formats.get(s);
        }
      }
      if (!validFileTypeSelected) {
        errorPopup("invalid file type selected, try again, specifying either "
            + ".png, .jpg, or .ppm", "Invalid file type");
      }

      mdl.load(fileFormat.importImage(absolutePath));
      setImage();
    }
  }

  /**
   * Applies the Sepia operation to the current image.
   */
  private class SepiaCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.applyOperations(new Sepia());
      setImage();
    }
  }

  /**
   * Applies the Greyscale operation to the current image.
   */
  private class GreyScaleCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.applyOperations(new Greyscale());
      setImage();
    }
  }

  /**
   * Applies the Sharpen operation to the current image.
   */
  private class SharpenCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.applyOperations(new Sharpening());
      setImage();
    }
  }

  /**
   * Applies the Blur operation to the current image.
   */
  private class BlurCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.applyOperations(new ImageBlur());
      setImage();
    }
  }

  /**
   * Command for applying the Mosaic operation to the current image.
   */
  private class GUIMosaicCommand implements IGUICommand {

    @Override
    public void execute() {
      String input = getDialogInput("Enter the number of seeds with which to "
          + "mosaic the image");

      try {
        int seeds = Integer.parseInt(input);
        mdl.mosaic(seeds);
        setImage();        // TODO: repaint the image here
        // TODO: repaint the image here
      } catch (IllegalArgumentException e) {
        // TODO: show an error dialog popup
        errorPopup("Please try again and "
                + "enter an integer greater than or equal to 0 for the number of seeds",
            "Invalid seed number");
      }
    }
  }

//  private class ImportOneCommand implements IGUICommand {
//
//    @Override
//    public void execute() {
//
//      final JFileChooser fChooser = new JFileChooser("");
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, PPM",
//          "jpg", "png", "ppm");
//      fChooser.setFileFilter(filter);
//      int fChooserValidation = fChooser.showOpenDialog(IMEFrame.this);
//      if (fChooserValidation == JFileChooser.APPROVE_OPTION) {
//        File f = fChooser.getSelectedFile();
//        String absPath = f.getAbsolutePath();
//
//        //String fExtension = absPath.substring(absPath.lastIndexOf('.'));
//
//        //Map<String, IFileFormat> formatsMap = IMEFrame.initFormatsMap();
//
//      }
//
//      String path = "res/MPP.png";
//
//      IImage toImport = new PNGFile().importImage(path);
//
//      mdl.load(toImport);
//
//      BufferedImage buffered = toImport.getBufferedImage();
//
//      ImageIcon icon = new ImageIcon(buffered);
//
//      imgLabel.setIcon(icon);
//    }
//  }

  /**
   * Command for downscaling an image.
   */
  private class DownScaleCommand implements IGUICommand {

    @Override
    public void execute() {
      String widthInp = getDialogInput("Enter the new width of the image");

      String heightInp = getDialogInput("Enter the new height of the image");

      try {
        int height = Integer.parseInt(heightInp);
        int width = Integer.parseInt(widthInp);
        new Downscale(mdl, height, width).apply();
        setImage();
      } catch (IllegalArgumentException e) {
        // TODO: show an error dialog popup
        JOptionPane.showMessageDialog(IMEFrame.this,
            "Please try again and "
                + "enter an integer greater than or equal to 0 for the number of seeds",
            "Invalid seed number",
            JOptionPane.ERROR_MESSAGE);
      }

    }
  }

  /**
   * Exports the current image.
   */
  private class ExportOneCommand implements IGUICommand {

    @Override
    public void execute() {
      // Todo: Change to FileDialog
      final JFileChooser fChooser = new JFileChooser("");
      fChooser.setDialogTitle("Choose the location to save the image");

      int selection = fChooser.showSaveDialog(IMEFrame.this);
      if (selection == JFileChooser.APPROVE_OPTION) {
        File f = fChooser.getSelectedFile();
        String absPath = f.getAbsolutePath();
        try {
          if (!initFormatsMap().containsKey(getFileExtension(absPath))) {
            ImageIO.write(mdl.getImage().getBufferedImage(), getFileExtension(absPath), f);
          }
          absPath += ".png"; // default to save as png
          ImageIO.write(mdl.getImage().getBufferedImage(), "png", f);
        } catch (IOException e) {
          JOptionPane.showMessageDialog(IMEFrame.this,
              "Could not save the specified file",
              "I/O Error",
              JOptionPane.ERROR_MESSAGE);
        }

      }
    }
  }

  private class CurrentLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      String desiredLayerInp = getDialogInput("Enter the layer you want to switch to");
      try {
        int desiredLayer = Integer.parseInt(desiredLayerInp);
        mdl.setCurrentLayer(desiredLayer);
      } catch (NumberFormatException e) {
        errorPopup("Cannot switch to layer: \"" + desiredLayerInp + "\"",
            "Bad layer number");

      }
      //mdl.setCurrentLayer();
    }
  }

  private class CheckerBoardCommand implements IGUICommand {

    @Override
    public void execute() {
      String widthInp = getDialogInput("Please enter the width of the checkerboard");
      String heightInp = getDialogInput("Please enter the height of the checkerboard");
      String unitInp = getDialogInput("Please enter the size of a square in the "
          + "checkerboard");

      try {
        int width = Integer.parseInt(widthInp);
        int height = Integer.parseInt(heightInp);
        int unit = Integer.parseInt(unitInp);

        mdl.setProgrammaticImage(new Checkerboard(), width, height, unit);
        setImage();
      } catch (NumberFormatException e) {
        errorPopup("Please enter a width height and unit size that are non-negative "
            + "integers", "Bad dimensions");
      }

    }
  }

  private class DeleteLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      String layerToDeleteInp = getDialogInput("Enter the number of the layer to delete");

      try {
        int layerToDelete = Integer.parseInt(layerToDeleteInp);
        mdl.deleteLayer(layerToDelete);
      } catch (NumberFormatException e) {
        errorPopup("Please enter a valid number between 0 and " +
            (mdl.getLayers().size() - 1), "Bad layer number");
      }
    }
  }

  private abstract class ANoiseCommand implements IGUICommand {

    /**
     * TODO
     */
    @Override
    public void execute() {
      String widthInp = getDialogInput("Please enter the width of the noise image");
      String heightInp = getDialogInput("Please enter the height of the noise image");

      try {
        int width = Integer.parseInt(widthInp);
        int height = Integer.parseInt(heightInp);

        mdl.setProgrammaticImage(this.factoryProgrammaticImage(), width, height, 1);
        setImage();
      } catch (NumberFormatException e) {
        errorPopup("Cannot create a noise input with the specified width \""
            + widthInp + "\" and height \"" + heightInp + "\"", "Bad noise image dimensions");
      }
    }

    /**
     * TODO
     */
    protected abstract IProgramImage factoryProgrammaticImage();

  }

  private class BWNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new BWNoise();
    }
  }

  private class RainbowNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new RainbowNoise();
    }
  }

  private class PureNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new PureNoise();
    }
  }

  private class CustomNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new Noise(getColorsFromUser());
    }

    private IPixel[] getColorsFromUser() {

      List<Color> colorsPicked = new ArrayList<>();


      int addAnotherColor = JOptionPane.YES_OPTION;

      while (addAnotherColor == JOptionPane.YES_OPTION) {
        addAnotherColor = JOptionPane.showConfirmDialog(inputDialogPanel,
            "Would you like to add another color to the noise image to be generated?",
            "Add more colors?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (addAnotherColor == JOptionPane.YES_OPTION) {
          Color c = JColorChooser.showDialog(IMEFrame.this, "Choose a color",
              colorChooserDisplay.getBackground());

          colorsPicked.add(c);
        }
      }

      IPixel[] colorsPickedArr = new IPixel[colorsPicked.size()];

      for (int i = 0; i < colorsPicked.size(); i++) {
        colorsPickedArr[i] = colorToIPixel(colorsPicked.get(i));
      }

      return colorsPickedArr;
    }
  }

  private class UndoCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.undo();
      setImage();
    }
  }

  private class RedoCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.redo();
      setImage();
    }
  }


  /**
   * Returns the file extension for the given fileName.
   *
   * @param fileName the file name to get the extension of.
   * @return a string with the file extension including the dot.
   */
  private static String getFileExtension(String fileName)
      throws IllegalArgumentException {
    return fileName.substring(fileName.lastIndexOf('.'));
  }

  /**
   * Sets the image in the GUI to the current one.
   */
  private void setImage() {
    imgLabel.setIcon(new ImageIcon(mdl.getImage().getBufferedImage()));
  }

  /**
   * TODO
   */
  private void errorPopup(String dialogMsg, String title)
      throws IllegalArgumentException {
    JOptionPane.showMessageDialog(IMEFrame.this,
        Utility.checkNotNull(dialogMsg, "cannot display a popup window"
            + " with a null dialog message"),
        Utility.checkNotNull(title, "cannot display a popup window"
            + " with a null title"),
        JOptionPane.ERROR_MESSAGE);
  }

  /**
   * TODO
   *
   * @param prompt
   * @return
   * @throws IllegalArgumentException
   */
  private String getDialogInput(String prompt)
      throws IllegalArgumentException {
    return JOptionPane.showInputDialog(Utility.checkNotNull(prompt, "cannot create a "
        + "dialog box with no prompt"));
  }

  /**
   * TODO
   *
   * @param c
   * @return
   */
  private static IPixel colorToIPixel(Color c) {
    return new PixelImpl(c.getRed(), c.getGreen(), c.getBlue());
  }


}
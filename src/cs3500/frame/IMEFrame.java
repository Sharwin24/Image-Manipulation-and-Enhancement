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
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.view.IMEView;
import cs3500.view.TextualIMEView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobKOctets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

/**
 * Class for the Java Swing Frame for the IME.
 */
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


  private JPanel inputDialogPanel;

  // Handle Layers
  private final List<JPanel> allLayers = new ArrayList<>();

  /**
   * Todo:
   */
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

    //~~~~~~~~~~~~~~~~~~~~~~~~~//
    add(mainPanel);
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

    JMenuItem deleteItem = new JMenuItem("Delete layer");
    deleteItem.setMnemonic(KeyEvent.VK_D);
    deleteItem.setActionCommand("delete");
    deleteItem.addActionListener(this);
    editMenu.add(deleteItem);

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

    inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());

    JMenuItem downscaleItem = new JMenuItem("Downscale...");
    downscaleItem.setMnemonic(KeyEvent.VK_D);
    downscaleItem.setActionCommand("downscale");
    downscaleItem.addActionListener(this);
    transformationsMenu.add(downscaleItem);

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

    JMenuItem bwNoiseItem = new JMenuItem("Black and white noise");
    bwNoiseItem.getAccessibleContext().setAccessibleDescription(
        "creates a noise image wherein each pixel is either black or white");
    bwNoiseItem.setMnemonic(KeyEvent.VK_B);
    bwNoiseItem.setActionCommand("bw noise");
    bwNoiseItem.addActionListener(this);
    noiseSubMenu.add(bwNoiseItem);

    JMenuItem rainbowNoiseItem = new JMenuItem("Black and white noise");
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

    allLayers.add(createLayerRow(0));
    layersPanel.add(allLayers.get(0));

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

  @Override
  public void actionPerformed(ActionEvent e) {

    Map<String, IGUICommand> actionsMap = this.initActionsMap();

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
   * @param layerNum the number of the layer.
   * @return a {@link JPanel} with the new layer.
   * @throws IllegalArgumentException if arguments are invalid or null.
   */
  private JPanel createLayerRow(int layerNum) {
    JPanel thisRow = new JPanel();
    thisRow.setLayout(new FlowLayout());
    thisRow.add(new JLabel(String.valueOf(layerNum)));
    JButton layerBtn = new JButton("Layer | " + layerNum);
    layerBtn.setActionCommand("currentLayerWithIndex " + layerNum);
    layerBtn.addActionListener(this);
    JCheckBox layerCB = new JCheckBox("visible?", true);
    layerCB.setActionCommand("layer " + layerNum + " check box");
    layerCB.addActionListener(this);

    thisRow.add(layerBtn);
    thisRow.add(layerCB);

    this.allLayers.add(thisRow);
    return thisRow;

  }


  /**
   * Returns a Hashmap of the string commands to the command objects.
   *
   * @return a {@link HashMap} of the commands from a string to their objects.
   */
  private Map<String, IGUICommand> initActionsMap() {

    Map<String, IGUICommand> actionsMap = new HashMap<>();

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
    actionsMap.putIfAbsent("currentLayerWithIndex", new CurrentLayerWithIndex());

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
  private interface IGUICommand{

    /**
     * Executes the GUI command.
     */
    void execute();

  }

  /**
   * Command for setting the current layer with the layer button.
   */
  private class CurrentLayerWithIndex implements IGUICommand {

    @Override
    public void execute() {
      // determine which button was pressed
    }
  }

  /**
   * Command for creating a new layer.
   */
  private class NewLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.addLayer();
      layersPanel.add(createLayerRow(mdl.getLayers().size() - 1));
    }
  }

  /**
   * Command for swapping two layers.
   */
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
        setImage();
      } catch (IllegalArgumentException e) {
        errorPopup("Please try again and "
                + "enter an integer greater than or equal to 0 for the number of seeds",
            "Invalid seed number");
      }
    }
  }

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
        errorPopup("Please try again and enter an integer greater than or equal to 0 for the "
            + "number of seeds", "Invalid number of seeds");
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
          errorPopup("Could not save the specified file", "I/O Error");
        }

      }
    }
  }

  /**
   * Command for selecting the current layer.
   */
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

  /**
   * Command for creating a checkerboard image.
   */
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

  /**
   * Command to delete a layer.
   */
  private class DeleteLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      String layerToDeleteInp = getDialogInput("Enter the index of the layer to delete");
      try {
        int layerToDelete = Integer.parseInt(layerToDeleteInp);
        mdl.deleteLayer(layerToDelete);
      } catch (IllegalArgumentException e) {
        errorPopup("Please enter a valid number between 0 and " +
            (mdl.getLayers().size() - 1), "Bad layer number");
      }
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
   * Displays a popup error message with the given message and title.
   *
   * @param dialogMsg the message text.
   * @param title     the title of the popup.
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
   * Displays a popup prompting the user for input, and saves the user response.
   *
   * @param prompt the prompt for the user to respond to.
   * @return the user's input as a String.
   * @throws IllegalArgumentException if the given input is invalid.
   */
  private String getDialogInput(String prompt)
      throws IllegalArgumentException {
    return JOptionPane.showInputDialog(Utility.checkNotNull(prompt, "cannot create a "
        + "dialog box with no prompt"));
  }


}
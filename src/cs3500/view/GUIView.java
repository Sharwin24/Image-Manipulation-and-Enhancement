package cs3500.view;

import cs3500.Utility;
import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The interactive GUI frame for the Image Manipulation and Enhancement program. Supports all of the
 * functionality of the {@link IMultiLayerModel} interface, exposing all functionality through a
 * menu ribbon as well as convenient buttons. Also supports interactive scripting from within the
 * GUI itself.
 */
public class GUIView extends JFrame implements IMEView, ActionListener, ItemListener,
    ListSelectionListener {

  private static final String WORKING_DIR = System.getProperty("user.dir");
  // the dimensions of a typical computer screen
  private static final int SCREEN_WIDTH = 1200;
  private static final int SCREEN_HEIGHT = 900;

  // themes
  private static final GUITheme LIGHT_THEME = new GUITheme(Color.WHITE, Color.LIGHT_GRAY,
      Color.GRAY);
  private static final GUITheme DARK_THEME = new GUITheme(Color.BLACK, Color.DARK_GRAY,
      Color.ORANGE);
  private static final GUITheme MATRIX_THEME = new GUITheme(Color.BLACK, Color.GREEN, Color.GREEN);
  private static final GUITheme RETRO_THEME = new GUITheme(Color.BLUE, Color.RED,
      new Color(177, 156, 217));

  // URLS
  public static final String GITHUB_URL = "https://github.com/Sharwin24/Image-Manipulation-"
      + "and-Enhancement.git";

  // to represent the model--the images to be manipulated
  private IMultiLayerExtraOperations mdl;
  // to represent the scriptable controller embedded in the GUI
  public IMultiLayerIMEController scrptCtrlr;
  private JButton runScriptBtn;
  private JButton loadScriptBtn;
  // to store interactively-scripted commands
  private Readable scriptIn;
  // to represent the embedded text view
  private IMEView txtView;
  // to store output from the view
  private Appendable out;
  // to store the GUI elements
  public JPanel mainPanel;
  // to store the menu ribbon elements
  private JLabel menusLabel;
  private JPanel menusPanel;
  private JLabel fileMenuLabel;
  private JLabel editMenuLabel;
  private JLabel transformationsMenuLabel;
  private JLabel programmaticImagesMenuLabel;


  public JMenuBar menuRibbon;
  // Image panel architecture
  private BufferedImage currentImage;
  private JPanel imagePanel;
  public JScrollPane imageScrollPanel;


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

  // the architecture for changing the theme
  private JMenu themeMenu;

  // the architecture for the help menu
  private JMenu helpMenu;

  // to handle input and display it
  private JLabel inputDisplay;

  // to handle text in the console
  public JPanel consolePanel;
  private JTextArea consoleTxt;


  private JLabel imgLabel = new JLabel("");


  private JPanel inputDialogPanel;

  private JPanel colorChooserDisplay = new JPanel();

  private Map<String, IGUICommand> actionsMap = this.initActionsMap();

  // Handle Layers
  private JPanel layerAreaPanel = new JPanel();
  private final JPanel layerButtonsPanel = new JPanel();
  public JPanel layersPanel = new JPanel();
  private final List<JPanel> allLayers = new ArrayList<>();

  public JTextArea scriptArea;

  private GUITheme defaultTheme = LIGHT_THEME;

  /**
   * Sets up the visual components of the GUI in all their glory. Sets the default theme of the GUI
   * to {@link this#defaultTheme}. Adds all action listeners to interactive elements so that they
   * can trigger events.
   */
  public GUIView() {
    super();
    setTitle("Image Manipulation and Enhancement");
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    // setBackground(defaultTheme.getPrimary());

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
    this.setLayerButtonsPanel();
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
    new ThemeCommand(defaultTheme).execute();
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
    programmaticImagesMenu.add(noiseSubMenu);

    menuRibbon.add(programmaticImagesMenu);

    // theme menu
    this.themeMenu = new JMenu("Theme");
    themeMenu.setMnemonic(KeyEvent.VK_M);
    themeMenu.getAccessibleContext().setAccessibleDescription("Change the theme of the GUI");

    JMenuItem lightThemeItem = new JMenuItem("Light");
    lightThemeItem.setMnemonic(KeyEvent.VK_L);
    lightThemeItem.setActionCommand("light theme");
    lightThemeItem.addActionListener(this);
    themeMenu.add(lightThemeItem);

    JMenuItem darkThemeItem = new JMenuItem("Dark");
    lightThemeItem.setMnemonic(KeyEvent.VK_D);
    darkThemeItem.setActionCommand("dark theme");
    darkThemeItem.addActionListener(this);
    themeMenu.add(darkThemeItem);

    JMenuItem matrixThemeItem = new JMenuItem("Matrix");
    matrixThemeItem.setMnemonic(KeyEvent.VK_M);
    matrixThemeItem.setActionCommand("matrix theme");
    matrixThemeItem.addActionListener(this);
    themeMenu.add(matrixThemeItem);

    JMenuItem retroThemeItem = new JMenuItem("Retro");
    retroThemeItem.setMnemonic(KeyEvent.VK_R);
    retroThemeItem.setActionCommand("retro theme");
    retroThemeItem.addActionListener(this);
    themeMenu.add(retroThemeItem);

    menuRibbon.add(themeMenu);

    // help menu
    this.helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);

//    JMenuItem aboutItem = new JMenuItem("About");
//    aboutItem.setMnemonic(KeyEvent.VK_A);
//    aboutItem.setActionCommand("about");
//    aboutItem.addActionListener(this);
//    helpMenu.add(aboutItem);
//
//    JMenuItem usemeItem = new JMenuItem("Show USEME");
//    usemeItem.setMnemonic(KeyEvent.VK_U);
//    usemeItem.setActionCommand("show useme");
//    usemeItem.addActionListener(this);
//    helpMenu.add(usemeItem);

    JMenuItem githubItem = new JMenuItem("View GitHub source");
    githubItem.setMnemonic(KeyEvent.VK_G);
    githubItem.setActionCommand("github");
    githubItem.addActionListener(this);
    helpMenu.add(githubItem);

    menuRibbon.add(helpMenu);

    // finally,
    this.mainPanel.add(menuRibbon, BorderLayout.PAGE_START);
  }

  private void setLayerButtonsPanel() {
    layerButtonsPanel.setLayout(new FlowLayout());
    JButton swapBtn = new JButton("Swap Layers");
    swapBtn.setActionCommand("swap");
    swapBtn.addActionListener(this);
    layerButtonsPanel.add(swapBtn);
    JButton newBtn = new JButton("New layer");
    newBtn.setActionCommand("new");
    newBtn.addActionListener(this);
    layerButtonsPanel.add(newBtn);
    // Adding Area panel
    layerAreaPanel.setLayout(new BoxLayout(layerAreaPanel, BoxLayout.Y_AXIS));
    layerAreaPanel.setBorder(BorderFactory.createTitledBorder("LAYERS"));
    layerAreaPanel.add(layerButtonsPanel);
  }


  /**
   * Initializes the panel for the layers.
   */
  private void layersPanel() {
    // setting up the layers panel:
    renderLayers();
    layerAreaPanel.add(layersPanel);
    mainPanel.add(layerAreaPanel, BorderLayout.LINE_START);
  }

  /**
   * Initializes the panel for the console.
   */
  private void console() {
    // setting up the console
    consolePanel = new JPanel();
    consoleTxt = new JTextArea();
    consolePanel.add(consoleTxt);
    consoleTxt.setEditable(false);
    JScrollPane scrollableConsole = new JScrollPane(consolePanel);
    scrollableConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollableConsole.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT / 10));
    mainPanel.add(scrollableConsole, BorderLayout.PAGE_END);
  }

  /**
   * Initializes the panel for the script.
   */
  private void scriptArea() {
    // setting up the script area
    JPanel scriptPanel = new JPanel();
    scriptPanel.setLayout(new BoxLayout(scriptPanel, BoxLayout.Y_AXIS));
    scriptArea = new JTextArea();
    scriptArea.setPreferredSize(new Dimension(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 2));
    scriptArea.setBorder(BorderFactory.createTitledBorder("Type script here"));

    scriptPanel.add(scriptArea);

    JPanel scriptBtnsPanel = new JPanel();
    scriptBtnsPanel.setLayout(new FlowLayout());

    runScriptBtn = new JButton("Run script");
    runScriptBtn.addActionListener(this);
    runScriptBtn.setActionCommand("run script");
    scriptBtnsPanel.add(runScriptBtn);

    loadScriptBtn = new JButton("Load script");
    loadScriptBtn.addActionListener(this);
    loadScriptBtn.setActionCommand("load script");
    scriptBtnsPanel.add(loadScriptBtn);

    scriptPanel.add(scriptBtnsPanel, BoxLayout.Y_AXIS);

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

    if (!(actionsMap.containsKey(e.getActionCommand()))) {
      return; // throw new IllegalArgumentException("command not recognized");
    }
    actionsMap.get(e.getActionCommand()).execute();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (!(actionsMap.containsKey(((JCheckBox) e.getItemSelectable()).getActionCommand()))) {
      return;
    }
    actionsMap.get(((JCheckBox) e.getItemSelectable()).getActionCommand()).execute();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  @Override
  public void renderLayers() {
    allLayers.clear();
    for (int i = 0; i < mdl.getLayers().size(); i++) {
      allLayers.add(createLayerRow(i, !mdl.getLayers().get(i).isInvisible()));
    }
    layersPanel.removeAll();
    for (JPanel row : allLayers) {
      layersPanel.add(row);
    }
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.pack();
  }


  /**
   * Creates a new layer row given the parameters for the layer.
   *
   * @param layerNum the number of the layer.
   * @param visible  if the layer is visible or not.
   * @return a {@link JPanel} with the new layer.
   * @throws IllegalArgumentException if the layer number is invalid.
   */
  private JPanel createLayerRow(int layerNum, boolean visible) {
    // Layer Button
    JPanel thisRow = new JPanel();
    thisRow.setLayout(new FlowLayout());
    JButton layerBtn = new JButton("Layer | " + layerNum);
    layerBtn.setOpaque(false);
    layerBtn.setBackground(this.defaultTheme.getPrimary());
    actionsMap.putIfAbsent("currentLayerWithIndex " + layerNum,
        new CurrentLayerWithIndex(layerNum));
    layerBtn.setActionCommand("currentLayerWithIndex " + layerNum);
    layerBtn.addActionListener(this);
    // Visibility Checkbox
    JCheckBox layerCB = new JCheckBox("visible?", true);
    layerCB.setBackground(this.defaultTheme.getPrimary());
    layerCB.setOpaque(false);
    actionsMap.putIfAbsent("visible " + layerNum, new VisibleLayer(layerNum));
    layerCB.setActionCommand("visible " + layerNum);
    layerCB.addItemListener(this);
    // Delete Button
    JButton deleteBtn = new JButton("Delete");
    deleteBtn.setOpaque(false);
    deleteBtn.setBackground(this.defaultTheme.getPrimary());
    actionsMap.putIfAbsent("delete " + layerNum, new DeleteLayerWithIndexCommand(layerNum));
    deleteBtn.setActionCommand("delete " + layerNum);
    deleteBtn.addActionListener(this);

    thisRow.add(layerBtn);
    thisRow.add(layerCB);
    thisRow.add(deleteBtn);
    thisRow.setOpaque(false);
    this.allLayers.add(thisRow);
    return thisRow;

  }


  /**
   * Returns a Hashmap of the string commands to the command objects. Helps to implement the command
   * pattern for all listeners of the supported events in the GUI frame.
   *
   * @return a {@link HashMap} of the commands from a string to their function objects that execute
   * the promised action.
   */
  private Map<String, IGUICommand> initActionsMap() {

    Map<String, IGUICommand> actionsMap = new HashMap<>();

    actionsMap.putIfAbsent("new", new NewLayerCommand());
    actionsMap.putIfAbsent("mosaic", new GUIMosaicCommand());
    actionsMap.putIfAbsent("import one", new ImportOneCommand());
    actionsMap.putIfAbsent("sepia", new OperationCommand(new Sepia()));
    actionsMap.putIfAbsent("greyscale", new OperationCommand(new Greyscale()));
    actionsMap.putIfAbsent("sharpen", new OperationCommand(new Sharpening()));
    actionsMap.putIfAbsent("blur", new OperationCommand(new ImageBlur()));
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
    actionsMap.putIfAbsent("redo", new RedoCommand());
    actionsMap.putIfAbsent("run script", new RunScriptCommand());
    // actionsMap.putIfAbsent("currentLayerWithIndex", new CurrentLayerWithIndex());
    actionsMap.putIfAbsent("load script", new LoadScriptCommand());
    actionsMap.putIfAbsent("light theme", new ThemeCommand(LIGHT_THEME));
    actionsMap.putIfAbsent("dark theme", new ThemeCommand(DARK_THEME));
    actionsMap.putIfAbsent("matrix theme", new ThemeCommand(MATRIX_THEME));
    actionsMap.putIfAbsent("retro theme", new ThemeCommand(RETRO_THEME));
    actionsMap.putIfAbsent("load script", new LoadScriptCommand());
    actionsMap.putIfAbsent("swap", new SwapLayersCommand());
//    actionsMap.putIfAbsent("about", new AboutCommand());
//    actionsMap.putIfAbsent("show useme", new ShowUSEMECommand());
    actionsMap.putIfAbsent("github", new ViewGitHubCommand());

    return actionsMap;
  }

  /**
   * Returns a HashMap of the format objects to their string representations.
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

  @Override
  public void write(String toWrite) throws IllegalStateException, IllegalArgumentException {

    if (toWrite == null) {
      throw new IllegalArgumentException("Argument is null");
    }
    consoleTxt.append(toWrite);
    consoleTxt.append("\n");
  }


  /**
   * <p>An interface for function objects representing a command that the GUI can execute.
   * Each class implementing this interface has an <code>execute()</code> command that, quite
   * obviously, just executes that command. Any relevant information that the object needs to
   * execute that command is passed in that object's constructor.</p>
   *
   * <p>This interface is marked <code>private</code> and nested inside of the
   * {@link GUIView} since it is only to be used to better organize action listeners and the {@link
   * GUIView#actionPerformed(ActionEvent)} method, and nowhere outside of that class. Having this
   * class nested inside of the {@link GUIView} class also gives it access to the frame and model
   * that are to be manipulated by this interface, meaning that we don't have to pass those objects
   * as parameters, and can instead reference them from within this context.</p>
   */
  private interface IGUICommand {

    /**
     * Executes the GUI command. Mutates the model and GUI accordingly based on the command object.
     */
    void execute();

  }

  /**
   * Action listener to toggle the visibility of a desired layer.
   */
  private class VisibleLayer implements IGUICommand {

    private final int layerNum;

    /**
     * Constructs a VisibleLayer with a given layer index.
     *
     * @param layerNum the index of the layer.
     * @throws IllegalArgumentException if the layer index is invalid.
     */
    public VisibleLayer(int layerNum) throws IllegalArgumentException {
      if (layerNum < 0) {
        throw new IllegalArgumentException("Layer Number cannot be less than zero");
      }
      this.layerNum = layerNum;
    }

    @Override
    public void execute() {
      try {
        mdl.toggleInvisible(layerNum);
        setImage();
        if (mdl.getLayers().get(layerNum).isInvisible()) {
          write("Layer " + layerNum + " is invisible");
        } else {
          write("Layer " + layerNum + " is visible");
        }
      } catch (IllegalArgumentException e) {
        write("Visibility Toggle Failed: " + e.getMessage());
      }

    }
  }

  /**
   * Command for setting the current layer with the layer button.
   */
  private class CurrentLayerWithIndex implements IGUICommand {

    private final int layerNum;

    public CurrentLayerWithIndex(int layerNum) throws IllegalArgumentException {
      if (layerNum < 0) {
        throw new IllegalArgumentException("Layer Number cannot be less than zero");
      }
      this.layerNum = layerNum;
    }

    @Override
    public void execute() {
      mdl.setCurrentLayer(layerNum);
      write("Layer " + layerNum + " selected");
      setImage();
    }
  }

  /**
   * Command for creating a new layer.
   */
  private class NewLayerCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.addLayer();
      allLayers.add(createLayerRow(mdl.getLayers().size() - 1, true));
      layersPanel.add(allLayers.get(allLayers.size() - 1));
      renderLayers();
    }
  }

  /**
   * Command for swapping two layers.
   */
  private class SwapLayersCommand implements IGUICommand {

    @Override
    public void execute() {
      String layerIndex1 = getDialogInput("Enter the index for the first layer to swap");
      String layerIndex2 = getDialogInput("Enter the index for the second layer to swap");
      try {
        mdl.swapLayers(Integer.parseInt(layerIndex1), Integer.parseInt(layerIndex2));
        setImage();
      } catch (IllegalArgumentException e) {
        write("Swap Failed: " + e.getMessage());
      }
    }
  }

  /**
   * Class for Importing/Loading an image into the GUI.
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
            + ".png, .jpg, or .ppm", "Invalid File Type");
      }

      mdl.load(fileFormat.importImage(absolutePath));
      setImage();
    }
  }

  /**
   * Command to apply operations to the GUI's image.
   */
  private class OperationCommand implements IGUICommand {

    private final IOperation toApply;

    public OperationCommand(IOperation toApply) {
      this.toApply = Utility.checkNotNull(toApply, "cannot make an operation command "
          + "with a null operation");
    }

    @Override
    public void execute() {
      mdl.applyOperations(toApply);
      setImage();
      renderLayers();
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
        mdl.downscaleLayers(height, width);
        setImage();
        renderLayers();
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
      final JFileChooser fChooser = new JFileChooser("");
      fChooser.setDialogTitle("Choose the location to save the image");
      int selection = fChooser.showSaveDialog(GUIView.this);
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
   * Command for a delete button to delete that specific layer.
   */
  private class DeleteLayerWithIndexCommand implements IGUICommand {

    private final int layerNum;

    /**
     * Constructs a DeleteLayerWithIndexCommand with the given layerIndex.
     *
     * @param layerNum the index of the layer to delete.
     */
    private DeleteLayerWithIndexCommand(int layerNum) {
      if (layerNum < 0) {
        throw new IllegalArgumentException("Layer Number cannot be less than zero");
      }
      this.layerNum = layerNum;
    }


    @Override
    public void execute() {
      try {
        mdl.deleteLayer(layerNum);
        allLayers.remove(layerNum);
        setImage();
        renderLayers();
      } catch (IllegalArgumentException e) {
        write("Delete Failed: " + e.getMessage());
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
        allLayers.remove(layerToDelete);
        layersPanel.remove(layerToDelete);
        setImage();
        renderLayers();
      } catch (IllegalArgumentException e) {
        errorPopup("Please enter a valid number between 0 and " +
            (mdl.getLayers().size() - 1), "Bad layer number");
      }
    }
  }

  /**
   * All Noise Commands follow the same execution with different parameters, which are specified by
   * each subclass that extends this abstract class.
   */
  private abstract class ANoiseCommand implements IGUICommand {

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
     * Returns the subclass programmed Image.
     */
    protected abstract IProgramImage factoryProgrammaticImage();

  }

  /**
   * Command to create a BWNoise image.
   */
  private class BWNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new BWNoise();
    }
  }

  /**
   * Command to create a RainbowNoise image.
   */
  private class RainbowNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new RainbowNoise();
    }
  }

  /**
   * Command to create a PureNoise image.
   */
  private class PureNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new PureNoise();
    }
  }

  /**
   * Command for Noise with custom colors.
   */
  private class CustomNoiseCommand extends ANoiseCommand {

    @Override
    protected IProgramImage factoryProgrammaticImage() {
      return new Noise(getColorsFromUser());
    }

    /**
     * Prompts user to select colors and returns a list of pixels with the color specified.
     *
     * @return a list of pixels with each pixel's color being a selected color by the user.
     */
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
          Color c = JColorChooser.showDialog(GUIView.this, "Choose a color",
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

  /**
   * Command to Undo an operation.
   */
  private class UndoCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.undo();
      setImage();
    }
  }

  /**
   * Command to Redo an operation.
   */
  private class RedoCommand implements IGUICommand {

    @Override
    public void execute() {
      mdl.redo();
      setImage();
    }
  }

  /**
   * Command to run a script written in the script panel.
   */
  private class RunScriptCommand implements IGUICommand {

    @Override
    public void execute() {
      StringReader scriptInput = new StringReader(scriptArea.getText());

      scrptCtrlr = MultiLayerIMEControllerImpl.controllerBuilder().model(mdl)
          .readable(scriptInput).buildController();

      scrptCtrlr.run();
      setImage();
      renderLayers();
    }
  }

  /**
   * Command to load a script and run it.
   */
  private class LoadScriptCommand implements IGUICommand {

    @Override
    public void execute() {

      FileDialog dialog = new FileDialog((Frame) null, "Select File");
      dialog.setMode(FileDialog.LOAD);
      dialog.setVisible(true);
      String absolutePath = dialog.getDirectory() + dialog.getFile();
      if (!absolutePath.endsWith(".txt")) {
        errorPopup("Invalid file type selected, must be of type: .txt", "Invalid File Type");
        return;
      }
      String scriptInput = "";
      try {
        scriptInput = new String(Files.readAllBytes(Paths.get(absolutePath)));
      } catch (IOException e) {
        errorPopup("Unable to read from File at: " + absolutePath, "Unable to read file");
      }
      scriptArea.setText(scriptInput);
    }
  }

  /**
   * Class to set the GUI's color theme.
   */
  private class ThemeCommand implements IGUICommand {

    private final GUITheme theme;

    /**
     * Constructs a ThemeCommand using a specific GUI theme.
     *
     * @param theme the theme to use for the GUI.
     */
    public ThemeCommand(final GUITheme theme) {
      this.theme = Utility.checkNotNull(theme, "cannot create a theme command object "
          + "with a null theme");
    }

    @Override
    public void execute() {
      mainPanel.setBackground(theme.getPrimary());
      layersPanel.setBackground(theme.getPrimary());
      imageScrollPanel.setBackground(theme.getPrimary());
      //imagePanel.setBackground(theme.getPrimary());
      consolePanel.setBackground(theme.getPrimary());

      scriptArea.setBackground(theme.getPrimary());
      scriptArea.setForeground(theme.getAccent());

      menuRibbon.setBackground(theme.getSecondary());
    }
  }

  /**
   * Command to navigate to the github page.
   */
  private class ViewGitHubCommand implements IGUICommand {

    @Override
    public void execute() {

      try {
        Desktop.getDesktop().browse(new URL(GITHUB_URL).toURI());
      } catch (URISyntaxException | IOException e) {
        errorPopup("Could not open up the github URL. Congrats on breaking the "
            + "program. https://github.com/Sharwin24/Image-Manipulation-and-Enhancement.git is"
            + " the actual link. Contact us there about this issue", "Bad GitHub URL");
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
  public void setImage() {
    try {
      if (!mdl.getCurrentLayer().isInvisible()) {
        imgLabel.setIcon(new ImageIcon(mdl.getImage().getBufferedImage()));
      } else {
        imgLabel.setIcon(new ImageIcon());
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Displays a popup error message with the given message and title.
   *
   * @param dialogMsg the message text.
   * @param title     the title of the popup.
   */
  public void errorPopup(String dialogMsg, String title)
      throws IllegalArgumentException {
    JOptionPane.showMessageDialog(GUIView.this,
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

  /**
   * Converts a Java Color object into an {@link IPixel} object.
   *
   * @param c the color object.
   * @return an {@link IPixel} object with the same value as the given color object.
   */
  private static IPixel colorToIPixel(Color c) {
    return new PixelImpl(c.getRed(), c.getGreen(), c.getBlue());
  }


}
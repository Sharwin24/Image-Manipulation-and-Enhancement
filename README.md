# Image-Manipulation-and-Enhancement

<p>This program serves as an image editing tool for photographers, artists, and personal use. 
It was developed in June 2021 as a project for cs3500: Object Oriented Design at Northeastern 
University.</p>

<p>This document serves as a tool for understanding the design of this project, from a 
high level. For convenience, this document will mimic the package structure of the acutal
project, and at the end, we provide a UML diagram to understand the overall structure and 
interactions between elements of the project from a visual standpoint.</p>

# Design Philosophy

The center goal of creating an image processor with capabilities to apply operations to an image was
only one aspect of this project. The other aspect(arguably more important too) was to build a
framework and design that enabled the developers to easily make modifications to the project in the
future in addition to outside developers being able to easily extend features for their own
implementations. An important step was to take a highly abstract approach to designing
implementations for what an image, operation, a pixel, etc. represent.

# Project Packages 📦

# The <code>Model</code> package

### The <code>IIMEModel</code> interface

An interface to represent functions of an Image-Manipulation-Enhancement model. The functions enable
the developer/user to import/export images, apply operations to an image, and create Programmed
images.

### The <code>IStateTrackingIMEModel</code> interface

The <code>IStateTrackingIMEModel</code> interface enables the Model to track the state of images
using a history. This interface offers useful methods such as <code>undo()</code>/<code>
redo()</code>,
<code>save()</code>, and <code>getImage()</code>. These methods allow the implementation to track
the state of images and to save and revert changes.

### The <code>StateTrackingModelImpl</code> class

The implementation of the Model class. The class implements the methods for applying operations,
saving image states, undoing/redoing operations, and importing/exporting images. The class applies
operations using the <code>IOperation</code> interface, and in order to implement a new operation,
an extension of the <code>IOperation</code> is all that's needed. Thus, the model can remain
unchanged while new operations are added.

### The <code>IMultiLayerModel</code> package

The <code>IMultiLayerModel</code> interface is an extension of the
<code>IStateTrackingIMEModel</code> interface. It also offers the ability to maintain multiple
layers for a group of images to be edited separately. The interface offers methods to edit a layer's
image, to add/remove/swap and get layers. It also provides functionality to set the current working
layer.

### The <code>MultiLayerModelImpl</code> class

The implementation of the MultiLayerModel interface. This class implements the interface's methods
and offers its use to the user.

## The <code>Matrix</code> package

### <code>IMatrix</code> Interface

The <code>IMatrix</code> interface is a generically typed interface that enables the developer to
create implementations of a Matrix using any Object or 'type'. The interface offers clever and
useful methods such as <code>map()</code> to map a function to all the values in a Matrix, <code>
fillWith(X entry)</code> to fill an entire Matrix with a single entry, as well as <code>
elementWiseOperation
()</code> to apply a BiFunction with values from 2 given Matrices.

### <code>AMatrix</code> Abstract class

The <code>AMatrix</code> class is abstract and implements many of the Matrix methods that are
universal to all Matrices regardless of the type of entries such as <code>getElement(int row, int
col)</code>, etc.

### <code>MatrixImpl</code> Class

Our implementation of a Matrix with a generic type. The class offers methods to build a Matrix in
multiple ways with its constructors.

## The <code>fileformat</code> package

### The <code>IFileFormat</code> interface

Provides a flexible way to adapt the import/export functionalities from the model. This interface's
implementations act as function objects with signatures
<ul>
<li>
<code>String -> I</code> for imports, where <code>I</code>
represents some representation of an image, and the given <code>String</code> represents the path
to import the file from</li>
<li>
<code>String, I -> File</code>, where again <code>I</code>
represents some representation of an image, and the given <code>String</code> represents the path
to import the file from.
</li>
</ul>
The advantage to this design is that if we want to import and export to new file types,
we can just add a class that implements this interface.

### The <code>PPMFile</code> class

Our implementation of the <code>IFileFormat</code> interface. Provides the ability to import from a
PPM file and export to a PPM file. When importing, this class checks that the imported image is an
actual PPM file ending in the  ".ppm" extension, and when exporting, this class always adds ".ppm"
to the end of the exported file.

## The <code>Image</code> package

### <code>IImage</code> Interface

The <code>IImage</code> interface is a representation of an image. It offers methods that an Image
class can use for manipulation and observing. The interface offers a method to get the Matrix of
pixels and to create a copy of the image.

### <code>ImageImpl</code> Class

Our implementation of an <code>IImage</code>. It offers being able to create an Image object with a
Matrix of pixels or a nested Arraylist of pixels. The class also offers methods to observe
properties of the Image such as <code>copy()</code>, <code>equals()</code>,
<code>getPixelArray()</code>, etc.

## The <code>operation</code> package

### The <code>IOperation</code> interface

The interface that all operations will implement. It offers the basic functionality of applying an
operation to an image.

### The <code>IFilter</code> interface

The interface for all Filtering Operations. Offers methods for Filters to utilize such as applying
to all or a single color channel.

### The <code>AFilter</code> abstract class

The abstract class to abstract methods that all filters will extend. This class deals with the heavy
lifting for a convolution approach to applying a kernel matrix to every pixel in the image. The
abstract class dynamically implements the kernel to the image depending on its size.

### The <code>ImageBlur</code> and <code>Sharpening</code> class

Implementations of <code>AFilter</code>, the classes provide their respective kernels, and the
abstract class handles their application. This implementation allows us to easily add new filters by
simply creating a new class to extend <code>AFilter</code>, and then implementing a method to
provide its respective kernel.

### The <code>IColorTransform</code> interface

The interface for all Color Transform operations. Offers methods for a color transform to use such
as applying to all channels at once.

### The <code>AColorTransform</code> abstract class

The abstract class to abstract methods that all color transforms will extend. This class deals with
the convolution for applying a matrix multiplication to each channel for every pixel. The abstract
class dynamically implements the kernel to the image depending on its size.

### The <code>Greyscale</code> and <code>Sepia</code> class

Implementations of <code>AColorTransform</code>, the classes provide their respective kernels, and
the abstract class handles their application. This implementation allows us to easily add new color
transformations by simply creating a new class to extend <code>AColorTransform</code>, and then
implementing a method to provide its respective kernel.

## The <code>Pixel</code> package

### The <code>IPixel</code> interface

The interface to represent the implementation for a pixel. The interface offers the observers for
the pixel's implementation.

### The <code>PixelImpl</code> class

The implementation for an <code>IPixel</code>. The class utilizes a <code>Channel</code> object to
represent the color channels for a pixel. The class implements the interface's methods and also
offers the overriden methods: <code>equals()</code>, <code>hashCode()</code>, and
<code>toString()</code>.

## The <code>Channel</code> package

### The <code>IChannel</code> interface

The interface to represent a color channel. A color channel has an intensity value that is enforced
to be within the range <code>[0,255]</code>, in addition to the channel's label: such as RED,GREEN,
and BLUE.

### The <code>ChannelImpl</code> class

The implementation of the <code>IChannel</code> interface. Implements the methods from the interface
to observe the properties of the Channel. The class also enforces the range for the intensity of the
channel within the constructor.

## The <code>ProgrammaticImages</code> package

### The <code>IProgamImage</code> interface

The interface for all programmatic images. Offers a construct method for subclasses to build their
programmatic images.

### The <code>Noise</code>,<code>PureNoise</code>, <code>RainbowNoise</code>, <code>BWNoise</code>, and the <code>Checkerboard</code> class

The subclasses to represent a specific programmatic image. Each class constructs its programmatic
image with a unit size, specified in the <code>setProgrammaticImage()</code> method in the model
class.

# The <code>View</code> Package

## The <code>IMEView</code> interface

The <code>IMEView</code> interface offers the ability to render a textual view for the model in use.
The interface offers functionality to render the layers and to write to the view with a given input
string.

## The <code>TextualIMEView</code> class

The textual implementation of the view interface. The class implements the methods and is used for
the final program.

## The <code>GUIView</code> class

The Graphical implementation of the view interface. This class hanles the construction and
organization of the graphical user interface for the IME. The class also extends JFrame to build the
GUI itself.

## The <code>GUITheme</code> class

A class to build a color theme for the GUI, supports many color themes like: Dark Theme, Light
Theme, Matrix Theme(green and black), and a Retro Theme(red and blue). The class offers support to
easily add new color themes.

# The <code>Controller</code> Package

## The <code>IMultiLayerIMEController</code> interface

The <code>IMultiLayerIMEController</code> interface offers the functionality to run a program
interacting with the model and the view. The implementation of this class will need to accept user
input in order to perform actions in the model and the view.

## The <code>MultiLayerIMEControllerImpl</code> class

The implementation of the controller. The controller initializes commands it has access to via
classes which contain the implementation of each command that the model offers. The controller
executes the commands by delegating the action through the command's class.

## The <code>Commands</code> package

## The <code>Text Commands</code> package

### The <code>IIMECommand</code> interface

The interface for all commands for an <code>IMEModel</code> interface. This interface offers the
functionality to execute a command given a user input. The implementations of this interface will
handle their specific command for the model and perform it with user input.

### The <code>AIMECommand</code> abstract class

An abstract class for the interface to assist handling the arguments given by the user. The abstract
class declares an abstract method <code>handleArgs</code> that each extended subclass will need to
implement.

### The <code>APortCommand</code> abstract class

An abstract class for the import/export commands. The class provides a <code>HashMap</code> of
strings to <code>IFileFormat</code> objects for import/export to utilize.

### The <code>ApplyCommand</code> class

Command for applying an operation to an image. The class is capable of applying multiple operations
in succession utilizing a list of given <code>IOperation</code>.

### The <code>CurrentCommand</code> class

Command for setting the current layer given a layer index.

### The <code>DeleteCommand</code> class
Command for deleting a layer given a layer index.
### The <code>ImportCommand</code> class
Command for importing an image. Extends the <code>APortCommand</code> abstract class to gain 
access to the <code>IFileFormat</code> HashMap. 
### The <code>ExportCommand</code> class
Command for exporting an image. Extends the <code>APortCommand</code> abstract class to gain
access to the <code>IFileFormat</code> HashMap.
### The <code>NewLayerCommand</code> class
Command for creating a new layer with an empty image at first.
### The <code>ProgrammaticImageCommand</code> class
Command for creating a Programmatic Image given parameters for the size and type of image. 
Offers the creation of: checkerboard, pure noise, black and white noise, and rainbow noise images.
### The <code>UndoCommand</code> class
Command for Undoing an operation to an image. Utilizes a stack structure for Undo/Redo History, 
which only allows Undoing/Redoing after a single operation.
### The <code>RedoCommand</code> class
Command for Redoing an operation to an image. Utilizes a stack structure for Undo/Redo History, 
which only allows Undoing/Redoing after a single operation.
### The <code>SaveCommand</code> class
Command for saving an image and pushing it's state to the Undo history.
### The <code>SwapCommand</code> class
Command for swapping two layers given the two layer indices.
### The <code>VisibilityCommand</code> class
Command for toggling the Visibility of a layer given a layer index.
## The <code>GUI Commands</code> package

### The <code>IGUICommand</code> interface
Interface for all GUI Command classes to extend. Provides the <code>execute()</code> method for 
each command class to implement with their respective execution.
### The <code>AGUICommand</code> abstract class
An abstract class for <code>IGUICommand</code>. Offers the construction of the 
<code>MultiLayer</code> model and the <code>GUIView</code> frame for each command to utilize in 
their executions.

### The <code>ThemeCommand</code> class
Command for setting a new color theme to the GUI. Prompts the user to select a 
<code>GUITheme</code> to switch to.

### The <code>ANoiseCommand</code> abstract class
Abstract class for all Noise images to be constructed and output to the GUI given a specific set 
of colors gathered from user input or by the user selecting a specific noise image such as 
RainbowNoise etc.

### The <code>BWNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the 
colors required to create a black and white noise image.
### The <code>PureNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the
colors required to create a pure noise image.
### The <code>RainbowNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the
colors required to create a rainbow noise image.
### The <code>CustomNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and prompts the user to select any number 
of colors using a <code>ColorChooser</code> to create a noise image with the selected colors.
###The <code>CheckerBoardCommand</code> class
Command for drawing a Checkerboard programmatic image by prompting the user for the size of the 
image and of the squares.
### The <code>GUIMosaicCommand</code> class
Command for creating a Mosaic image of the current image by prompting the user for the number of 
seeds to use.
### The <code>DownScaleCommand</code> class
Command for downscaling all the layers. Prompts the user to enter a new size to downscale to.
### The <code>CurrentLayerCommand</code> class
Command for setting a layer as the current one. Prompts the user for enter a specific layer 
index to set the current layer to.
### The <code>CurrentLayerWithIndexCommand</code> class
Command for setting a layer as the current one by pressing the layer button.
### The <code>DeleteLayerCommand</code> class
Command for deleting a layer. Prompts the user to enter a specific layer index to delete.
### The <code>DeleteLayerWithIndexCommand</code> class
Command for deleting a layer by pressing the delete button for a specific layer.
### The <code>VisibleLayerCommand</code> class
Command for setting the selected layer as visible/invisible using the GUI checkbox.
### The <code>SwapLayersCommand</code> class
Command for swapping two layers, prompts the user to enter the two layer indices to swap.
### The <code>ImportOneCommand</code> class
Command to import an image to the current selected layer.
### The <code>ExportOneCommand</code> class
### The <code>LoadScriptCommand</code> class
### The <code>RunScriptCommand</code> class
### The <code>OperationCommand</code> class
### The <code>UndoCommand</code> class
### The <code>RedoCommand</code> class
### The <code>ViewGitHubCommand</code> class
## The <code>Utility</code> class

A class with helpful methods to be utilized throughout the program. Includes many methods to
simplify complicated methods such as enforcing an integer to be within a range, checking for
nullness, etc.

## Submitted Images

### Elmo

The elmo photo was downloaded from google images at
this <a href="https://img.wattpad.com/3f59778a0de8b29ab8f459d9a61b1aceee4d441b/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f776174747061642d6d656469612d736572766963652f53746f7279496d6167652f7466664e446f65356f4b6f6f67513d3d2d3931303631323331362e313631633236643530326334653732653735343035383534303739302e6a7067?s=fit&w=720&h=720">
link.</a>

### Owl

The owl photo was downloaded from google images at
this <a href="https://bforblogging.com/wp-content/uploads/2019/03/download-copyright-free-images.jpg">
link.</a>

### CheckerBoard

This is a programmatic image created by the model utilizing the <code>Checkerboard</code> class.

### PureNoise

This is a programmatic image created by the model utilizing the <code>PureNoise</code> class.

### UML Class Diagram

The class diagram for the project, depicting the extensions and class implementations in a graphical
view. The diagram also shows the methods that each class contains.
![UMLDiagram](https://github.com/Sharwin24/Image-Manipulation-and-Enhancement/blob/main/res/IME-UML.png)
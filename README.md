# Image-Manipulation-and-Enhancement

<p>This program serves as an image editing tool for photographers, artists, and personal use. 
It was developed in June 2021 as a project for cs3500: Object Oriented Design at Northeastern 
University.</p>

<p>This document serves as a tool for understanding the design of this project, from a 
high level. For convenience, this document will mimic the package structure of the acutal
project, and at the end, we provide a UML diagram to understand the overall structure and 
interactions between elements of the project from a visual standpoint.</p>


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
Our implementation of the <code>IFileFormat</code> interface. Provides the ability to 
import from a PPM file and export to a PPM file. When importing, this class
checks that the imported image is an actual PPM file ending in the  ".ppm" extension,
and when exporting, this class always adds ".ppm" to the end of the exported file.

 <img uml="res/IME-UML.png" alt="UML"> 

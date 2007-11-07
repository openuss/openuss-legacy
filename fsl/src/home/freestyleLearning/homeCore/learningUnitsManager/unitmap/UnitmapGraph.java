/**
 * <code>UnitmapGraph</code> offers the core functionality for the
 * Unitmap. It loads the unitmap, saves the layout and stores
 * the prerequisites via the <code>FSLLearningUnitsManager</code>.
 * 
 * Attention:
 * Currently it is checked if a cell is a group by checking if the
 * cell has not a port. So whenever also connections to grouping cells
 * are possible these parts of the code need to be updated.
 * 
 * @author Jens Sieberg, 31.07.2006 - 11.09.2006
 */

package freestyleLearning.homeCore.learningUnitsManager.unitmap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.BeanInfo;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PersistenceDelegate;
import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jgraph.JGraph;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.ParentMap;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitsManager;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitsDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.unitmap.jgraph.JGraphGroupRenderer;
import freestyleLearning.homeCore.learningUnitsManager.unitmap.jgraph.JGraphShadowBorder;

public class UnitmapGraph extends JGraph {

	//TODO maybe an option in UnitmapFrame to configure these
	public static Color VERTEX_COLOR = Color.PINK;
	public static Color GROUP_COLOR = new Color(240, 240, 255);
	public static Color UNINSTALLED_COLOR = Color.WHITE;
	public static Color ERROR_COLOR = Color.RED;
	public static Color MARKING_COLOR = Color.BLUE;
	public static Color PERCENTAGE_OUT_OF_RANGE_COLOR = Color.GRAY;
	
	// above this only not installed learning units are placed
	//TODO do not allow droping installed learning units here
	public static int UNINSTALLED_UNITS_HEIGHT = 100;
	
	
	FSLLearningUnitsManager learningUnitsManager;
	FSLLearningUnitsDescriptor learningUnitsDescriptor;
	File unitmapDescriptorFile;
	
	ArrayList currentPrerequisiteList;
	
	boolean isAuthor = true;

	// Copy from GraphEdX
	// controls which elements of the GraphLayoutCache
	// are to be stored within the XML document
	static {
		makeCellViewFieldsTransient(PortView.class);
		makeCellViewFieldsTransient(VertexView.class);
		makeCellViewFieldsTransient(EdgeView.class);
		
		makeTransient(DefaultPort.class, "edges");
		makeTransient(DefaultEdge.class, "source");
		makeTransient(DefaultEdge.class, "target");
	} //End copy from GraphEdX
	

	/**
	 * construct the Unitmap with <code>GraphModel</code> as its data source.
	 */
	public UnitmapGraph(GraphModel model,
						FSLLearningUnitsManager learningUnitsManager,
						FSLLearningUnitsDescriptor learningUnitsDescriptor,
						File unitmapSaveDirectory) {
		super( model, new GraphLayoutCache(model, new DefaultCellViewFactory(), true) );
		
		this.learningUnitsManager = learningUnitsManager;
		this.learningUnitsDescriptor = learningUnitsDescriptor;

		String fileSeparator = System.getProperty("file.separator");
		File unitmapDescriptorDirectory = new File(unitmapSaveDirectory + fileSeparator + "unitmap");
		if (!unitmapDescriptorDirectory.exists()) unitmapDescriptorDirectory.mkdirs();		
		unitmapDescriptorFile = new File(unitmapDescriptorDirectory.getAbsolutePath() + fileSeparator + "unitmap.xml");		

		// Overrides the global vertex renderer to display +/-
		VertexView.renderer = new JGraphGroupRenderer();
		
		// Use the Grid (but don't make it Visible)
		setGridEnabled(true);
		// Set the Grid Size to 10 Pixel
		setGridSize(6);
		// Set the Tolerance to 2 Pixel
		setTolerance(2);
		// Accept edits if click on background
		setInvokesStopCellEditing(true);
		// Allows control-drag
		setCloneable(true);
		// Jump to default port on connect
		setJumpToDefaultPort(true);
		// enable Anti-Aliasing
		setAntiAliased(true);
			
		setHandleColor(Color.CYAN);
	}

	/***
	 * loads all installed Units.
	 */
	public void loadUnitmapData() {
		try {
			//System.err.println("Checking for saved Layout...");
			loadGraphFromLayout();
			
			//System.err.println("Graph loaded from Layout. Refreshing Graph...");
			ArrayList learningUnits = partiallyRefreshGraph();
			
			//System.err.println("Loading missing Learning Units..."); 
			loadGraphFromLearningUnitDescriptors(learningUnits);
			
		} catch (FileNotFoundException e) {
			//System.err.println("Layout not found. Loading Graph completely from Descriptor...");
			setModel(new UnitmapModel());
			
			ArrayList learningUnits = new ArrayList(learningUnitsDescriptor.getLearningUnitsDescriptors());
			
			loadGraphFromLearningUnitDescriptors(learningUnits);
		}
		
		//System.err.println("Loading all edges from Descriptor...");
		loadAllEdgesFromPrerequisiteDataSource();
		
		//System.err.println("Updating Colors of Graph...");
		restoreGraphDefaults();
		
		//System.err.println("... finished.");
	}

	/**
	 * simply replaces the current instance <code>GraphLayoutCache</code>
	 * with a saved instance
	 * @throws FileNotFoundException if the layout-file could not be found
	 */
	protected void loadGraphFromLayout() throws FileNotFoundException {
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(unitmapDescriptorFile)));
			this.setGraphLayoutCache((GraphLayoutCache)decoder.readObject());
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * replaces the IDs which were saved with the layout with its
	 * corresponding LearningUnitDescriptors. After that the layout is checked
	 * if it still represents the learning units tree correctly. All
	 * learning units which are not represented correctly are deleted from
	 * the layout.
	 * 
	 * @return learningUnitDescriptors which were not correctly
	 *         represented in the graph.
	 */
	protected ArrayList partiallyRefreshGraph()
	{
		//System.err.println("-- Partially Refreshing Graph --");
		
		ArrayList learningUnits = new ArrayList(learningUnitsDescriptor.getLearningUnitsDescriptors());

		ArrayList cellsToRemove = new ArrayList();

		List cells = new ArrayList(java.util.Arrays.asList(this.order(this.getDescendants(this.getRoots()))));

		for (int currentCount = 0; currentCount < cells.size(); currentCount++)
		{	//für alle Knoten im Graph
			if (!graphModel.isEdge(cells.get(currentCount)) && !graphModel.isPort(cells.get(currentCount)) )
			{
				DefaultGraphCell currentCell = (DefaultGraphCell)cells.get(currentCount);
				
				//finde zugehörige learningUnit
				FSLLearningUnitDescriptor learningUnit = null;
				int unitCount;
				for (unitCount = 0; unitCount < learningUnits.size(); unitCount++)
				{
					if ( ((FSLLearningUnitDescriptor)learningUnits.get(unitCount)).getId().equals(currentCell.getUserObject()))
					{
						learningUnit = (FSLLearningUnitDescriptor)learningUnits.get(unitCount);
						break;
					}
				}

				//System.err.println("Cell ID: " + currentCell.getUserObject() );
				//System.err.println("Learning Unit: " + learningUnit);
				if (learningUnit != null)
				{	
					//System.err.println();
					//System.err.println("-+ Refreshing Node: " + learningUnit.getId());
					currentCell.setUserObject(learningUnit);
					
					// falls Teil einer Gruppe
					if (currentCell.getParent() != null)
					{
						//System.err.println(" |-+ Checking Group -");
						DefaultGraphCell parentCell = (DefaultGraphCell)currentCell.getParent();
						
						String parentID = null;
						if (parentCell.getUserObject() instanceof String)
							parentID = (String)parentCell.getUserObject();
						else if (parentCell.getUserObject() instanceof FSLLearningUnitDescriptor)
							parentID = ((FSLLearningUnitDescriptor)parentCell.getUserObject()).getId();
						
						//System.err.println("   |- Old ParentID: " + parentID);
						//System.err.println("   |- Current ParentID: " + learningUnit.getParentID());
						
						// prüfe, ob nun nicht mehr Teil dieser Gruppe
						if ( (learningUnit.getParentID() == null) ||
								!learningUnit.getParentID().equals(parentID) )
						{
							//System.err.println("   |- Removing Node -");
							
							cellsToRemove.add( currentCell );
							
							//TODO in case of errors maybe the parent map needs to be updated
						}
						else
						{
							// sonst ist Unit korrekt im Graph repräsentiert
							learningUnits.remove(unitCount);
						}
					}
					else
					{
						//System.err.println(" |-+ Checking No-Group -");
						//prüfe, ob nun Teil einer Gruppe
						if (learningUnit.getParentID() != null)
						{
							//System.err.println("   |- Current ParentID:" + learningUnit.getParentID());
							
							cellsToRemove.add( currentCell );

						}
						else
						{
							//sonst ist Unit korrekt im Graph repräsentiert
							learningUnits.remove(unitCount);
						}
					}
				}
				else
				{   // der Knoten ist nicht mehr installiert und wird entfernt
					//System.err.println("|- Removing Node - ");
					
					cellsToRemove.add( currentCell );					
				}
			}
		}
		
		for (int i = 0; i < cellsToRemove.size(); i++)
		{
			//System.err.println("-+ Removing Cell and Children: " + cellsToRemove.get(i));

			Object[] subcellsToRemove = this.getDescendants(this.getDescendants(new Object[] {cellsToRemove.get(i)}));
			
			// Vor Entfernen aller Subcells, sicherstellen
			// dass diese auch später wieder eingefügt werden
			for (int c = 0; c < subcellsToRemove.length; c++)
			{
				if (!graphModel.isPort(subcellsToRemove[c])
						&& ( ((DefaultGraphCell)subcellsToRemove[c]).getUserObject() instanceof FSLLearningUnitDescriptor ) )
				{
					FSLLearningUnitDescriptor learningUnit = (FSLLearningUnitDescriptor)((DefaultGraphCell)subcellsToRemove[c]).getUserObject();
					
					if (( learningUnit != null)
							&& !learningUnits.contains(learningUnit))
					{
						//System.err.println(" |- Learning Unit inconsistent: " + learningUnit.getId());
						
						learningUnits.add(learningUnit);
					}
				}
			}
			
			graphModel.remove(subcellsToRemove);
		}
		
		return learningUnits;
	}
	
	/**
	 * loads the Unitmap from the LearningUnitDescriptors specified by 
	 * <code>learningUnits</code>.
	 * 
	 *  A very simple layout is applied to the nodes ordering
	 *  them from left to right. Nodes grouped into a theme are ordered
	 *  top down. This maybe insufficient if a very large Unitmap is loaded.
	 * 
	 * @param learningUnits which should be loaded into the Unitmap.
	 */
	protected void loadGraphFromLearningUnitDescriptors(List learningUnits)
	{
		// einfaches Layout
		int xOffset = 130, yOffset = 100;
		int xLocation = 50, yLocation;
	
		//-- Füge alle Knoten ein --
		
		//zur Bildung von Untergruppen
		ArrayList groups = new ArrayList();
		ArrayList subgroups = new ArrayList();
		
		int counter = 0;
		//solange wie die Liste Elemente enthält
		while ((counter < learningUnits.size()))
		{	
			yLocation = this.UNINSTALLED_UNITS_HEIGHT;
			
			//System.err.println("OuterLoop - Counter: " + counter);
			FSLLearningUnitDescriptor parentUnit = (FSLLearningUnitDescriptor)learningUnits.get(counter);
			//System.err.println(parentUnit.getId());
			
			//prüfe, ob tatsächlich Elternelement
			if ( parentUnit.hasFolder() )
			{	//System.err.println("  -- isFolder --");
				
				//das Element kann aus der Liste gelöscht werden
				learningUnits.remove(counter);

				ArrayList childUnits = new ArrayList();				
				
				//durchsuche nun die Liste nach den Kindern
				for (int childCounter = 0; childCounter < learningUnits.size(); childCounter++) {
					//System.err.println("  InnerLoop - Counter: " + childCounter);
					FSLLearningUnitDescriptor childUnit = (FSLLearningUnitDescriptor)learningUnits.get(childCounter);
					
					//wenn selbst kein Elternelement und ein zugehöriges Kind
					if ( !childUnit.getFolder()
							&& (childUnit.getParentID() != null)
							&& childUnit.getParentID().equals(parentUnit.getId()))
					{	
						//System.err.println("  -- Adding a child node --");
						DefaultGraphCell node = this.createDefaultGraphCell(childUnit);

						node.getAttributes().applyMap(createCellAttributes(new Point(xLocation, yLocation)));

						// 	Insert the Vertex (including child port and attributes)
						this.getGraphLayoutCache().insert(node);						
						
						//füge es in die Liste der Kinder hinzu
						childUnits.add(node);
						
						//entferne Element aus Liste und aktualisiere Zähler
						learningUnits.remove(childCounter);
						childCounter--;

						yLocation += yOffset;
					}	
				}
			
				//wenn Kinder gefunden
				if (childUnits.size() > 0)
				{	//System.err.println("  -- Adding group node --");
					DefaultGraphCell group = group( childUnits.toArray(), parentUnit );

					// Untergruppen werden später erzeugt
					groups.add(group);
					if ( parentUnit.getParentID() != null )
						subgroups.add(group);
					
					xLocation += xOffset;
				}
				

				//setzte Counter -1, da Liste mehrfach verändert
				counter = -1;
			} //wenn kein Elternelement UND kein Kindelement
			else if ( parentUnit.getParentID() == null) {
				//System.err.println("  -- Adding a single vertex --");
				this.insert(new Point(xLocation, yLocation), parentUnit);
				xLocation += xOffset;

				//entferne Element aus Liste und aktualisiere Zähler
				learningUnits.remove(counter);
				counter--;
			}
			else //Kindelement
			{	
				//prüfe, ob zugehörige Gruppe schon eingefügt
				Object[] cells = this.getDescendants(this.getRoots());
				for (int currentCount = 0; currentCount < cells.length; currentCount++)
				{	//für alle Knoten im Graph
					if (!graphModel.isEdge(cells[currentCount]) && !graphModel.isPort(cells[currentCount]) )
					{
						//die eine Gruppe sind
						DefaultGraphCell group = (DefaultGraphCell)cells[currentCount];
						if (!(group.getFirstChild() instanceof Port))
						{
							if ( ((FSLLearningUnitDescriptor)group.getUserObject()).getId().equals(parentUnit.getParentID()) )
							{
								DefaultGraphCell node = this.createDefaultGraphCell(parentUnit);
								node.getAttributes().applyMap(createCellAttributes(new Point(xLocation, yLocation)));

								// Add vertex to existing group
								group.add(node);						

								ParentMap pm = new ParentMap(group.getChildren().toArray(), group);

								this.graphLayoutCache.edit(null, null, pm, null);
								
								//entferne Element aus Liste und aktualisiere Zähler
								learningUnits.remove(counter);
								counter--;
								
								break;
							}
						}
					}
				}
			}
			
			counter++;

			//System.err.println(learningUnits.size() + " Element(s) left. Counter: " + counter);			
			//System.err.println();			
		}
		
		// für alle noch zu bildenden Untergruppen
		for (int subCount = 0; subCount < subgroups.size(); subCount++)
		{		
			DefaultGraphCell subgroup = (DefaultGraphCell)subgroups.get(subCount);
			FSLLearningUnitDescriptor subLearningUnit = (FSLLearningUnitDescriptor)subgroup.getUserObject();
			
			DefaultGraphCell supergroup = null;
			for (int groupCount = 0; groupCount < groups.size(); groupCount++)
			{
				DefaultGraphCell group = (DefaultGraphCell)groups.get(groupCount);
				
				if ( ((FSLLearningUnitDescriptor)group.getUserObject()).getId().equals(subLearningUnit.getParentID()) )
				{					
					supergroup = group;		
					break;
				}
			}

		    // wurde Gruppe nicht neu erstellt
			if (supergroup == null)
			{
				//prüfe, ob zugehörige Gruppe schon eingefügt
				Object[] cells = this.getDescendants(this.getRoots());
				for (int currentCount = 0; currentCount < cells.length; currentCount++)
				{		//für alle Knoten im Graph
					if (!graphModel.isEdge(cells[currentCount]) && !graphModel.isPort(cells[currentCount]) )
					{
						//die eine Gruppe sind
						DefaultGraphCell group = (DefaultGraphCell)cells[currentCount];
						if (!(group.getFirstChild() instanceof Port))
						{
							if ( ((FSLLearningUnitDescriptor)group.getUserObject()).getId().equals(subLearningUnit.getParentID()) )
							{
								supergroup = group;
								break;
							}
						}
					}
				}
			}

			if (supergroup != null)
			{
				// Füge Gruppe in existierende Grupp ein
				supergroup.add(subgroup);						

				ParentMap pm = new ParentMap(supergroup.getChildren().toArray(), supergroup);

				this.graphLayoutCache.edit(null, null, pm, null);
			}			
		}
	}

	
	/**
	 * loads all edges by checking for each node which prerequisites exist.
	 * 
	 * For prerequisites with no corresponding nodes yet, those
	 * nodes are created and placed at top of the unitmap. Applying to them a
	 * simple layout which orders them from left to right.
	 * 
	 * To load the prerequisites the method <code>getLearningUnitPrerequisites()</code>
	 * in <code>FSLLearningUnitsManager</code> is used.
	 */
	protected void loadAllEdgesFromPrerequisiteDataSource()
	{	
		// applies simple layout to uninstalled vertexes
		
		int xOffset = 100;
		int xLocation = 0, yLocation = 0;
		
		//für alle Vertexes A
		Object[] cells = this.getDescendants(this.getRoots());
		for (int currentCount = 0; currentCount < cells.length; currentCount++)
		{   // die eine LearningUnit enthalten
			if (((DefaultGraphCell)cells[currentCount]).getUserObject() instanceof FSLLearningUnitDescriptor)
			{
				FSLLearningUnitDescriptor currentLearningUnit = (FSLLearningUnitDescriptor)((DefaultGraphCell)cells[currentCount]).getUserObject();
				
				//falls Voraussetzungen vorhanden
				List prerequisiteIdsList = learningUnitsManager.getLearningUnitPrerequisites(currentLearningUnit.getId());
				
				if (prerequisiteIdsList != null)
				{
					String[] prerequisiteIds = (String[])prerequisiteIdsList.toArray(new String[0]);
								
					//für alle Voraussetzungen V
					for (int idCount = 0; idCount < prerequisiteIds.length; idCount++)
					{   	//prüfe alle Vertexes
						int prerequisiteCount;
						for (prerequisiteCount = 0; prerequisiteCount < cells.length; prerequisiteCount++)
						{   	// die ungleich Vertex A und eine LearningUnit enthalten
							if (prerequisiteCount != currentCount 
									&& ((DefaultGraphCell)cells[prerequisiteCount]).getUserObject() instanceof FSLLearningUnitDescriptor)
							{
								FSLLearningUnitDescriptor prerequisiteUnit = (FSLLearningUnitDescriptor)((DefaultGraphCell)cells[prerequisiteCount]).getUserObject();
							
								// finde das Vertex B, welches der Voraussetzung V entspricht
								if (prerequisiteUnit.getId().equals(prerequisiteIds[idCount]))
									break;
							}
						}

						if ( prerequisiteCount == cells.length ) 
						{	//es wurde kein entsprechendes B gefunden
							
							//prüfe, ob schon mal ein Knoten einer nicht installiert Unit hinzugefügt wurde
							DefaultGraphCell vertex = null;
							Object[] uninstalledCells = this.getDescendants(this.getRoots());
							for (int existingCount = 0; existingCount < uninstalledCells.length; existingCount++)
							{	//für alle Knoten im Graph
								if (!graphModel.isEdge(uninstalledCells[existingCount]) && !graphModel.isPort(uninstalledCells[existingCount]) )
								{
									if ( ((DefaultGraphCell)uninstalledCells[existingCount]).getUserObject() instanceof String 
											&& ((DefaultGraphCell)uninstalledCells[existingCount]).getUserObject().equals(prerequisiteIds[idCount]) )
									{
										vertex = (DefaultGraphCell)uninstalledCells[existingCount];
										break;
									}
									
								}
							}
							
							if (vertex == null )
							{ //es wurde noch kein entsprechender Knoten hinzugefügt 
								vertex = createDefaultGraphCell(new String(prerequisiteIds[idCount]));
								// Create a Map that holds the attributes for the Vertex
								vertex.getAttributes().applyMap(createCellAttributes(new Point(xLocation, yLocation)));

								GraphConstants.setGradientColor(vertex.getAttributes(), UNINSTALLED_COLOR);
								GraphConstants.setMoveable(vertex.getAttributes(), false);

								// Insert the Vertex (including child port and attributes)
								this.getGraphLayoutCache().insert(vertex);
								
								xLocation += xOffset;
							}
							
							this.connect((Port)vertex.getFirstChild(), (Port)((DefaultGraphCell)cells[currentCount]).getFirstChild());
						}
						else
							// verbinde Vertex B und A
							this.connect((Port)((DefaultGraphCell)cells[prerequisiteCount]).getFirstChild(), (Port)((DefaultGraphCell)cells[currentCount]).getFirstChild());
					}
				}
			}
		}
	}	
	
	/**
	 * saves the unitmap, including the prerequisites and the layout.
	 * @param <code>FALSE</code> if the layout could not be saved, because
	 *        of inconsistencies in the Unitmap.
	 */
	public boolean saveUnitmapData() 
	{
		if (!checkInconsitency())
		{
			saveLearningUnitPrerequisites();
		
			saveGraphToLayout();
			
			return true;
		}
		
		return false;
	}

	
	/***
	 * saves to each learning unit its prerequisites as modeled in the
	 * unitmap.
	 * 
	 * To save the prerequisites the method <code>addLearningUnitPrerequisites()</code> 
	 * in <code>FSLLearningUnitsManager</code> is used.
	 */
	protected void saveLearningUnitPrerequisites() {
		//für alle Vertexes A
		Object[] cells = this.getDescendants(this.getRoots());
		for (int currentCount = 0; currentCount < cells.length; currentCount++)
		{   // die eine LearningUnit enthalten
			if (((DefaultGraphCell)cells[currentCount]).getUserObject() instanceof FSLLearningUnitDescriptor)
			{
				//System.err.print("-- Current Vertex: ");
				//System.err.println(((DefaultGraphCell)cells[currentCount]).getUserObject());
				
				// unten mit Kanten verbunden sind (ausgehend/eingehend)
				if (((DefaultGraphCell)cells[currentCount]).getFirstChild() instanceof DefaultPort) {
					//sammle alle Prerequisites in einer Liste
					DefaultEdge[] edges = (DefaultEdge[])((DefaultPort)((DefaultGraphCell)cells[currentCount]).getFirstChild()).getEdges().toArray(new DefaultEdge[0]); 

			    	java.util.List prerequisiteIds = new ArrayList();
					for (int i = 0; i < edges.length; i++)
					{	
						DefaultGraphCell sourceVertex = (DefaultGraphCell)((DefaultPort)edges[i].getSource()).getParent();
						//System.err.print("Source Vertex: ");
						//System.err.println(sourceVertex.getUserObject());
					
						if (!sourceVertex.equals(cells[currentCount]))
						{
							if (sourceVertex.getUserObject() instanceof FSLLearningUnitDescriptor)
							{
								String prerequisiteId = ((FSLLearningUnitDescriptor)sourceVertex.getUserObject()).getId();
								//System.err.print("Adding PrerequisiteID: ");
								//System.err.println(prerequisiteId);							
								prerequisiteIds.add(prerequisiteId);
							}
							else if (sourceVertex.getUserObject() instanceof String)
							{
								//System.err.print("Adding PrerequisiteID (of uninstalled Unit): ");
								//System.err.println(sourceVertex.getUserObject());
								prerequisiteIds.add(sourceVertex.getUserObject());
							}	
						}
					}
				
					//übergebe die Liste an den LearningUnitsManager
					learningUnitsManager.addLearningUnitPrerequisites(prerequisiteIds, 
							((FSLLearningUnitDescriptor)((DefaultGraphCell)cells[currentCount]).getUserObject()).getId());
					//System.err.println("-- Saving Prerequisites to XML File");
				}
			}
		}
	}
	
	/**
	 * saves the layout of the Unitmap.
	 * 
	 * To reduce the data size the prerequisites and the not installed
	 * learning units are not saved to the layout. The layout is then
	 * simply stored as the <code>GraphLayoutCache</code>'s current state.
	 * 
	 * Parts of this method are copied from the GraphEdX example.
	 * 
	 * @see com.jgraph.example.GraphEdX
	 */
	protected void saveGraphToLayout() {
		//Delete all edges so that they are not saved
		Object[] cells = this.getDescendants(this.getRoots());
		for (int i = 0; i < cells.length; i++)
		{
			if (graphModel.isEdge(cells[i]))
				graphModel.remove(new Object[] {cells[i]});
		}

		//replace the LearningUnitDescriptor object by its ids
		for (int currentCount = 0; currentCount < cells.length; currentCount++)
		{   // die eine LearningUnit enthalten
			if (((DefaultGraphCell)cells[currentCount]).getUserObject() instanceof FSLLearningUnitDescriptor)
			{
				FSLLearningUnitDescriptor learningUnit = (FSLLearningUnitDescriptor)((DefaultGraphCell)cells[currentCount]).getUserObject();
				
				((DefaultGraphCell)cells[currentCount]).setUserObject(learningUnit.getId());
			}
			else if (((DefaultGraphCell)cells[currentCount]).getUserObject() instanceof String)
			{
				graphModel.remove(this.getDescendants(new Object[] {cells[currentCount]}));
			}
		}		
		
		XMLEncoder encoder;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream(unitmapDescriptorFile)));
			configureEncoder(encoder);
			encoder.writeObject(graphLayoutCache);
			encoder.close();
		} catch (Exception e) {
			System.err.println("Error during encoding");
			System.err.println(e.getMessage());
		}
		
		//restore graph
		// a partial refresh is enough here as nothing changes during saving
		partiallyRefreshGraph();
		
		loadAllEdgesFromPrerequisiteDataSource();
		
	}
	
	/**
	 * @see com.jgraph.example.GraphEdX
	 */
	protected void configureEncoder(XMLEncoder encoder) {
		// Better debugging output, in case you need it
		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception e) {
				e.printStackTrace();
			}
		});

		encoder.setPersistenceDelegate(UnitmapModel.class,
				new DefaultPersistenceDelegate(new String[] { "roots",
						"attributes" }));

		encoder.setPersistenceDelegate(GraphLayoutCache.class,
						new DefaultPersistenceDelegate(new String[] { "model",
								"factory", "cellViews", "hiddenCellViews",
								"partial" }));
		encoder.setPersistenceDelegate(DefaultGraphCell.class,
				new DefaultPersistenceDelegate(new String[] { "userObject" }));
		encoder.setPersistenceDelegate(DefaultEdge.class,
				new DefaultPersistenceDelegate(new String[] { "userObject" }));
		encoder.setPersistenceDelegate(DefaultPort.class,
				new DefaultPersistenceDelegate(new String[] { "userObject" }));
		encoder.setPersistenceDelegate(AbstractCellView.class,
				new DefaultPersistenceDelegate(new String[] { "cell",
						"attributes" }));
		encoder.setPersistenceDelegate(DefaultEdge.DefaultRouting.class,
				new PersistenceDelegate() {
					protected Expression instantiate(Object oldInstance,
							Encoder out) {
						return new Expression(oldInstance,
								GraphConstants.class, "getROUTING_SIMPLE", null);
					}
				});
		encoder.setPersistenceDelegate(DefaultEdge.LoopRouting.class,
				new PersistenceDelegate() {
					protected Expression instantiate(Object oldInstance,
							Encoder out) {
						return new Expression(oldInstance,
								GraphConstants.class, "getROUTING_DEFAULT",
								null);
					}
				});
		encoder.setPersistenceDelegate(JGraphShadowBorder.class,
				new PersistenceDelegate() {
					protected Expression instantiate(Object oldInstance,
							Encoder out) {
						return new Expression(oldInstance,
								JGraphShadowBorder.class, "getSharedInstance",
								null);
					}
				});
		encoder.setPersistenceDelegate(ArrayList.class, encoder
				.getPersistenceDelegate(List.class));
	}	
	
	
	
	/**
	 *  Insert a new Vertex at point
	 */
	public void insert(Point2D point) {
		// Construct Vertex with no Label
		DefaultGraphCell vertex = createDefaultGraphCell();
		// Create a Map that holds the attributes for the Vertex
		vertex.getAttributes().applyMap(createCellAttributes(point));
		// Insert the Vertex (including child port and attributes)
		this.getGraphLayoutCache().insert(vertex);
	}
	
	/**
	 *  Insert a new Vertex at point with object as UserObject
	 * @param point where to insert vertex.
	 * @param object to be set as the UserObject
	 */
	public void insert(Point2D point, Object object) {
		// Construct Vertex with no Label
		DefaultGraphCell vertex = createDefaultGraphCell(object);
		// Create a Map that holds the attributes for the Vertex
		vertex.getAttributes().applyMap(createCellAttributes(point));
		// Insert the Vertex (including child port and attributes)
		this.getGraphLayoutCache().insert(vertex);		
	}
	
	/**
	 * creates a default graph cell.
	 * @return created default graph cell.
	 */
	protected DefaultGraphCell createDefaultGraphCell() {
		DefaultGraphCell cell = new DefaultGraphCell("Cell "
				+ new Integer(this.getVertexCount()) );
		// Add one Floating Port
		cell.addPort();
		return cell;
	}
	
	/**
	 * creates a default graph cell with object as UserObject
	 * @param object to be set as the UserObject
	 * @return created default graph cell
	 */
	protected DefaultGraphCell createDefaultGraphCell(Object object) {
		DefaultGraphCell cell = new DefaultGraphCell(object);
		// Add one Floating Port
		cell.addPort();
		return cell;		
	}	
	
	/**
	 * creates attributes for a normal cell.
	 * 
	 * Can be overwritten to make cells look differently, e.g.
	 * if another font shall be used or the cells need to larger/smaller.
	 * 
	 * @param point
	 * @return map with attributes that can be applied to the cell.
	 */
	public Map createCellAttributes(Point2D point) {
		Map map = new Hashtable();
		// Snap the Point to the Grid
		point = this.snap((Point2D) point.clone());

		// Add a Bounds Attribute to the Map
		GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(),
				point.getY(), 0, 0));
		// Make sure the cell is resized on insert
		//GraphConstants.setResize(map, true);
		
		// Makes the Cell a little bit larger when autosizing
		GraphConstants.setInset(map, 20);
		
		// Autosizes the Cells
		GraphConstants.setAutoSize(map, true);
		
		
		// Add a nice looking gradient background
		GraphConstants.setGradientColor(map, VERTEX_COLOR);
		// Add a Border Color Attribute to the Map
		GraphConstants.setBorderColor(map, Color.BLACK);
		// Add a White Background
		GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		
		GraphConstants.setEditable(map, false);
		return map;
	}

	/**
	 * groups the children and passes object as the UserObject of the group.
	 * @param children to be grouped.
	 * @param object to be set as the UserObject of the group cell.
	 * @return created group which already has been inserted into the graph.
	 */
	public DefaultGraphCell group(Object[] children, Object object) {
		// Order Cells by Model Layering
		children = this.order(children);
		// If Any Cells in View
		if (children != null && children.length > 0) {
			double gs2 = 2 * this.getGridSize();
			Rectangle2D collapsedBounds = this.getCellBounds(children);
			collapsedBounds.setFrame(collapsedBounds.getX(), collapsedBounds
					.getY(), Math.max(collapsedBounds.getWidth() / 4, gs2),
					Math.max(collapsedBounds.getHeight() / 2, gs2));
			this.snap(collapsedBounds);
			DefaultGraphCell group = createGroupCell(collapsedBounds);
			if (group != null ) {
				// Create the group structure
				ParentMap pm = new ParentMap();
				for (int i = 0; i < children.length; i++) {
					pm.addEntry(children[i], group);
				}
				
				group.setUserObject(object);
				
				this.getGraphLayoutCache().insert(new Object[] { group },
						null, null, pm);
				
				return group;
			}
		}
		
		return null;
	}


	/**
	 * creates a new group cell.
	 * 
	 * Can be overwritten to make a group look differently, e.g.
	 * if another font shall be used or the group needs to be larger/smaller.
	 * 	  
	 * @param collapsedBounds
	 * @return created group cell.
	 */
	protected DefaultGraphCell createGroupCell(Rectangle2D collapsedBounds) {
		DefaultGraphCell group = new DefaultGraphCell();
		//group.addPort(); there should be no port for the group cell

		GraphConstants.setInset(group.getAttributes(), 20);
		GraphConstants.setBackground(group.getAttributes(), GROUP_COLOR);
		GraphConstants.setBorderColor(group.getAttributes(), Color.black);
		GraphConstants.setOpaque(group.getAttributes(), true);
		GraphConstants.setBorder(group.getAttributes(), JGraphShadowBorder.getSharedInstance());
		GraphConstants.setBounds(group.getAttributes(), collapsedBounds);

		GraphConstants.setEditable(group.getAttributes(), false);
		GraphConstants.setAutoSize(group.getAttributes(), true);
		return group;
	}	
	
	/**
	 * inserts a new Edge between source and target.
	 * 
	 * Additionally the edge is inserted into a group its source or
	 * target vertex is in to avoid self-loops when collapsing the group cell.
	 */
	public void connect(Port source, Port target) {
		// Construct Edge with no label
		DefaultEdge edge = createDefaultEdge();
		edge.setSource(source); edge.setTarget(target);
		if (this.getModel().acceptsSource(edge, source)
				&& this.getModel().acceptsTarget(edge, target)) {
			// Create a Map thath holds the attributes for the edge
			edge.getAttributes().applyMap(createEdgeAttributes());

			// Insert the Edge and its Attributes
			this.graphLayoutCache.insertEdge(edge, source, target);			
			
			//Test if the edge should be part of an existing group
			if ( (((DefaultGraphCell)((DefaultPort)source).getParent()).getParent() != null)
				&& (((DefaultGraphCell)((DefaultPort)target).getParent()).getParent() != null) )
			{
				DefaultGraphCell sourceGroup = (DefaultGraphCell)((DefaultGraphCell)((DefaultPort)source).getParent()).getParent();
				DefaultGraphCell targetGroup = (DefaultGraphCell)((DefaultGraphCell)((DefaultPort)target).getParent()).getParent();
				
				if ( (sourceGroup.getParent() != null) &&
					(sourceGroup.getParent().equals(targetGroup)) ) 
				{
					//Make edge part of target group
					
					targetGroup.add(edge);

					ParentMap pm = new ParentMap(targetGroup.getChildren().toArray(), targetGroup);					
					this.graphLayoutCache.edit(null, null, pm, null);
				}
				else if ( ( (targetGroup.getParent() != null) &&
						(targetGroup.getParent().equals(sourceGroup)) )
						|| sourceGroup.equals(targetGroup) )
				{
					// Make edge part of source group
					sourceGroup.add(edge);

					ParentMap pm = new ParentMap(sourceGroup.getChildren().toArray(), sourceGroup);
					this.graphLayoutCache.edit(null, null, pm, null);
				}
			}
		}
	}

	/**
	 * creates an edge.
	 * @return created edge.
	 */
	protected DefaultEdge createDefaultEdge() {
		return new DefaultEdge();
	}

	/**
	 * creates attributes for an edge.
	 * 
	 * Can be overwritten to make edges look differently.
	 * 
	 * @return map with attributes that can be applied to the edge.
	 */
	public Map createEdgeAttributes() {
		Map map = new Hashtable();
		// Add a Line End Attribute
		GraphConstants.setLineEnd(map, GraphConstants.ARROW_SIMPLE);
		
		//Disables Labels on Edges
		GraphConstants.setEditable(map, false);
		
		GraphConstants.setDisconnectable(map, false);
		
		return map;
	}
	
	
	/**
	 * removes an edge from the Unitmap.
	 * @param cell
	 */
	public void removeEdge(Object cell) {
		if (graphModel.isEdge(cell))
		{
			graphModel.remove(new Object[] {cell});			
		}
	}


	/**
	 * checks if the graph is inconsistent.
	 * 
	 * Currently this means checking the graph for cycles. If there
	 * are cycles all nodes part of the cycle are marked in <code>DEFAULT_ERROR_COLOR</code>.
	 * 
	 * @return <code>FALSE</code> if the graph is consistent otherwhise <code>TRUE</code>.
	 */
	public boolean checkInconsitency() {
			
		java.util.List inconsistentNodes = checkForCycles();
		
		restoreGraphDefaults();
		markVertexes(inconsistentNodes.toArray(), ERROR_COLOR, true);
		
		return ( inconsistentNodes.size() > 0 );
	}
	
	/**
	 * checks the graph for cycles.
	 * 
	 * The checking is done by the colored DFS algorithm.
	 * 
	 * @return empty List if no cycles exist, 
	 *         else a List containing the nodes forming the cycle.
	 */
	private List checkForCycles() {
		//mark all nodes white
		//use in-place edits so that changes are not recorded
		Object[] cells = this.getDescendants(this.getRoots());
		
		for (int i = 0; i < cells.length; i++)
		{
			if (!graphModel.isEdge(cells[i]) && !graphModel.isPort(cells[i]) )
			{
				DefaultGraphCell v = (DefaultGraphCell)cells[i];
				GraphConstants.setGradientColor(v.getAttributes(), Color.WHITE);
			}
		}
		
		//for all nodes which are white
		ArrayList cycleVertexes = new ArrayList();
		for (int i = 0; i < cells.length; i++)
		{
			if (!graphModel.isEdge(cells[i]) && !graphModel.isPort(cells[i]) )
			{
				DefaultGraphCell v = (DefaultGraphCell)cells[i];
				if ( GraphConstants.getGradientColor(v.getAttributes()).equals(Color.WHITE) )
				{
					if ( visitNode(v, cycleVertexes) )
					{
						return cycleVertexes;
					}
				}
			}
		}	

		return cycleVertexes;		
	}
	
	// recursive part of checkForCycles()
	private boolean visitNode(DefaultGraphCell v, List cycleVertexes) {
		GraphConstants.setGradientColor(v.getAttributes(), Color.GRAY);
		cycleVertexes.add(v);

		if (v.getFirstChild() instanceof DefaultPort)
		{ 	//for all edges (v, u)
			DefaultEdge[] edges = (DefaultEdge[])((DefaultPort)v.getFirstChild()).getEdges().toArray(new DefaultEdge[0]);

			for (int i = 0; i < edges.length; i++)
			{	
				DefaultGraphCell u = (DefaultGraphCell)((DefaultPort)edges[i].getTarget()).getParent();

				if (!u.equals(v))
				{
					if (GraphConstants.getGradientColor(u.getAttributes()).equals(Color.GRAY))
						return true;
					else
						if (visitNode(u, cycleVertexes) )
							return true;
				}
			}
		}
		
		GraphConstants.setGradientColor(v.getAttributes(), Color.BLACK);
		cycleVertexes.remove(v);
		return false;
	}

	
	/**
	 * restores the default colors of the graph depending of the current
	 * user-role.
	 */
	public void restoreGraphDefaults() {
		Object[] cells = this.getDescendants(this.getRoots());
	
		for (int i = 0; i < cells.length; i++)
		{
			if (!graphModel.isEdge(cells[i]) && !graphModel.isPort(cells[i]) )
			{
				DefaultGraphCell currentCell = (DefaultGraphCell)cells[i];
					
				Color color = VERTEX_COLOR;
				
				if ( currentCell.getUserObject() instanceof String ) //unistalled Vertex
				{
					color = UNINSTALLED_COLOR;
				}
				else if (!(currentCell.getFirstChild() instanceof Port)) //group Vertex
				{
					color = GROUP_COLOR;
				}
				else if (!isAuthor)
				{
					double progressStatus = learningUnitsManager.getLearningUnitProgressStatus(((FSLLearningUnitDescriptor)currentCell.getUserObject()).getId());
					color = getRGBShade(progressStatus);
				}
				
				markVertexes(new Object[] {currentCell}, color, false);
			}
		}
		
		repaint();
	}
	
	/**
	 * returns the corresponding color to a given percentage.
	 * 
	 * This implementation returns a continuous shade from 
	 * red (0%) over yellow (50%) to green (100%). The method can
	 * be overwritten if for example only the colors red, yellow and green
	 * should be shown.
	 * 
	 * @param percentage for which a color is the range of [0..1].
	 * @return the color corresponding to percentage 
	 *         or <code>PERCENTAGE_OUT_OF_RANGE_COLOR</code> if the
	 *         percentage is outside the range of [0..1].
	 */
	protected Color getRGBShade(double percentage)
	{
		if ( (percentage > 1) || (percentage < 0) )
			return PERCENTAGE_OUT_OF_RANGE_COLOR;
		else
		{
			int green = 255;
			if (percentage < 0.5)
				green = (int)(255 * percentage/0.5);
		
			int red = 255;
			if (percentage > 0.5)
				red = (int)(255 * (-2*percentage + 2));
	
			return new Color(red, green, 0);
		}
	}

	/**
	 * marks all prerequisites of a given array of cells.
	 * @param cells just the first cell in the array is taken into account.
	 */
	public void markAllPrerequisites(Object[] cells) {
		currentPrerequisiteList = new ArrayList();
		restoreGraphDefaults();
		
		if ( (cells != null) && (cells.length > 0) )
		{
			findPrerequisites(new Object[] {cells[0]}, currentPrerequisiteList);
			
			markVertexes(currentPrerequisiteList.toArray(), MARKING_COLOR, true);
		}
	}

	/**
	 * collects for the cells all prerequisites in a list. 
	 * @param cells for which prerequisites shall be collected.
	 * @param prerequisites list in which the prerequisites shall be collected.
	 */
	private void findPrerequisites(Object[] cells, List prerequisites) {
		for (int c = 0; c < cells.length; c++)
		{
			DefaultGraphCell startCell = (DefaultGraphCell)cells[c];
	
			//it is assumed that cells just have one port
			if ( !graphModel.isEdge(startCell)
					&& graphModel.isPort(startCell.getFirstChild()) )
			{
				//System.err.println("---------------------");
				//System.err.print("Fetching FirstChild: ");
				//System.err.print(graphModel.isPort(startCell.getFirstChild()));
				//System.err.println(".");
				
				//System.err.print("Adding Prerequisite: ");
				//System.err.println(startCell);

				prerequisites.add(startCell);
				
				
				DefaultPort port = (DefaultPort)startCell.getFirstChild();				
				DefaultEdge[] edges = (DefaultEdge[])port.getEdges().toArray(new DefaultEdge[0]);
				
				List targetCells = new ArrayList();
				for (int k = 0; k < edges.length; k++)
				{
					DefaultGraphCell sourceCell = (DefaultGraphCell)graphModel.getParent(graphModel.getSource(edges[k]));
					if ( !sourceCell.equals(startCell) )
					{
						//System.err.print("Next Cells: ");
						//System.err.println(sourceCell);

						targetCells.add( sourceCell );
					}
				}
				
				// i.e. for each startCell one recursive call
				if (targetCells.size() > 0)
					findPrerequisites(targetCells.toArray(), prerequisites);

			}
	
		}	
	}	

	/**
	 * marks the cells in the given color.
	 * @param cells to be colored.
	 * @param color
	 * @param repaint if <code>TRUE</code> the Unitmap is repainted after
	 *                marking the cells.
	 */
	public void markVertexes(Object[] cells, Color color, boolean repaint)
	{		
		for (int i=0; i<cells.length; i++)
		{
			if ( !graphModel.isEdge(cells[i]) )
			{
				DefaultGraphCell currentCell = (DefaultGraphCell)cells[i];
				GraphConstants.setGradientColor(currentCell.getAttributes(), color);
			
				CellView tmp = graphLayoutCache.getMapping(currentCell, false);
				graphLayoutCache.refresh(tmp, false);
			}
		}
		
		if (repaint)
			repaint();
	}	
	
//
// HelperMethods
//

	// Returns the total number of cells in a graph
	// including Ports, Edges, Vertices
	protected int getCellCount() {
		Object[] cells = this.getDescendants(this.getRoots());
		return cells.length;
	}
	
	// Only counts vertices
	protected int getVertexCount() {
		int count = 0;
		Object[] cells = this.getDescendants(this.getRoots());
		for (int i = 0; i < cells.length; i++)
		{
			if (!graphModel.isEdge(cells[i]) && !graphModel.isPort(cells[i]) )
				count++;
		}
		return count;
	}

	public void setIsAuthor(boolean isAuthor)
	{
		this.isAuthor = isAuthor;
		restoreGraphDefaults();
	}
	public boolean isAuthor() {
		return isAuthor;
	}

	public FSLLearningUnitsManager getLearningUnitsManager() {
		return learningUnitsManager;
	}	
	
	public List getCurrentPrerequisiteList() {
		return currentPrerequisiteList;
	}
	
	
	/**
	 * used to configure the XML output.
	 * @see com.jgraph.example.GraphEdX
	 */
	public static void makeTransient(Class clazz, String field) {
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; ++i) {
				PropertyDescriptor pd = propertyDescriptors[i];
				if (pd.getName().equals(field)) {
					pd.setValue("transient", Boolean.TRUE);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Makes all fields but <code>cell</code> and <code>attributes</code>
	 * transient in the bean info of <code>clazz</code>.
	 * 
	 * @param clazz
	 *            The cell view class who fields should be made transient.
	 * @see com.jgraph.example.GraphEdX
	 */
	public static void makeCellViewFieldsTransient(Class clazz) {
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = info
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; ++i) {
				PropertyDescriptor pd = propertyDescriptors[i];
				if (!pd.getName().equals("cell")
						&& !pd.getName().equals("attributes")) {
					pd.setValue("transient", Boolean.TRUE);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uncomment this and there will be a gray line dividing the
	 * uninstalled units from the rest of the unitmap. This code
	 * is not included now as it then should be forbidden to place
	 * installed units beyond this line.
	 */
/*	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.GRAY);
		g.drawLine(0, UNINSTALLED_UNITS_HEIGHT - 35, (int)getSize().getWidth(), UNINSTALLED_UNITS_HEIGHT - 35);
	}
*/
}
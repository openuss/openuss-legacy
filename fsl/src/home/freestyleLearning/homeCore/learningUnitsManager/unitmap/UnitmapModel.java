/**
 * a Custom Model that does not allow Self-References.
 * 
 * Some additional changes have been made to not allow undirected
 * and multiple edges between to nodes.
 * 
 * @see com.jgraph.example.GraphEdX
 */
package freestyleLearning.homeCore.learningUnitsManager.unitmap;

import java.util.List;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Edge;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;

public class UnitmapModel extends DefaultGraphModel {
	public UnitmapModel() {
		super();
	}
	
	public UnitmapModel(List roots, AttributeMap attributes) {
		super(roots, attributes);
	}
	
	// Override Superclass Method
	public boolean acceptsSource(Object edge, Object port) {
		// Source only Valid if not Equal Target
	
		return (((Edge) edge).getTarget() != port);
		
	}

	// Override Superclass Method
	public boolean acceptsTarget(Object edge, Object port) {
		//Target only valid if not equal source
		//and if the target holds a LearningUnitDescriptor
		boolean accepts = (((Edge) edge).getSource() != port)
							&& ( ((DefaultGraphCell)((DefaultPort)port).getParent()).getUserObject() instanceof FSLLearningUnitDescriptor );
		
		if (accepts)
		{
			if (((Edge)edge).getSource() != null)
			{
				// Target only valid if not parallel to another edge
				Edge[] edges = (Edge[])((DefaultPort)((Edge)edge).getSource()).getEdges().toArray(new Edge[0]);
		
				for (int i = 0; i < edges.length; i++)
				{
					if (edges[i].getTarget().equals(port)
							|| edges[i].getSource().equals(port))
					{
						accepts = false;
						break;
					}
				}
			}
		}
				
		return accepts;
	}
}
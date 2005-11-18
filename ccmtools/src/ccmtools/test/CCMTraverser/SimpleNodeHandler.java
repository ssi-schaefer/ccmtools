package ccmtools.test.CCMTraverser;

import java.util.Stack;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MModuleDef;


public class SimpleNodeHandler 
    implements NodeHandler
{
    private Object currentNode;
    private Stack nodeStack;
    private Stack namespaceStack;
    
    
    /**
     * Start processing a new graph. Clear out existing class instance
     * variables.
     */
    public void startGraph()
    {
        currentNode = null;
        nodeStack = new Stack();
        namespaceStack = new Stack();
    }

    
    /**
     * Start a new node in the graph. This function provides basic node tracking
     * functionality and should be called at the beginning of all derived
     * classes' startNode functions, if they exist.
     * 
     * @param node
     *            the node that the GraphTraverser object is about to
     *            investigate.
     * @param scopeId
     *            the full scope identifier of the node. This identifier is a
     *            string containing the names of parent nodes, joined together
     *            with double colons.
     */
    public void startNode(Object node, String scopeId)
    {
        System.out.println("start " + scopeId);
        
        currentNode = node;
        nodeStack.push(currentNode);
        
        if(node instanceof MModuleDef)
            namespaceStack.push(((MModuleDef) node).getIdentifier());

    }

    
    /**
     * Add node data to our internal variable hash.
     * 
     * @param fieldType
     *            a string indicating the type of field from whence the node
     *            data came.
     * @param field_name
     *            a string indicating the (capitalized) name of the variable
     *            being handled.
     * @param value
     *            the value of the given variable.
     */
    public void handleNodeData(String fieldType, String fieldId, Object value)
    {
        System.out.println("NodeData: "+ fieldType + ", " + fieldId + ", " + value);
    }

    
    /**
     * End a node in the graph. This function provides basic node tracking
     * functionality and should be called at the beginning of all derived
     * classes' endNode functions, if they are defined.
     * 
     * This function updates variables in this node and in the parent node (if
     * there is a parent---see updateVariables for more details). Then, if the
     * node type is defined in the output_types list, the template for this node
     * is loaded, substituted, and written.
     * 
     * @param node
     *            the node that the graph traverser object just finished
     *            investigating.
     * @param scope_id
     *            the full scope identifier of the node. This identifier is a
     *            string containing the names of ancestor nodes, joined together
     *            with double colons.
     */
    public void endNode(Object node, String scopeId)
    {
        System.out.println("end  " + scopeId);
        
        currentNode = nodeStack.pop();
        
        if(node instanceof MModuleDef) {
            namespaceStack.pop();
        }
    }

    
    /**
     * End processing a graph. This implementation does not do anything.
     */
    public void endGraph()
    {
    }
}

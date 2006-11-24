package ccmtools.parser.idl;

import java.util.Stack;

public class Scope
{
    private Stack<String> scope = new Stack<String>();
    
    public void pushModule(String name)
    {
        //System.out.println("push Scope: " + name);
        scope.push(name);
    }
    
    public String popModule()
    {
        String name = scope.pop();
        //System.out.println("pop Scope: " + name);
        return name;
    }
    
    public void clear()
    {
        scope.clear();
    }
    
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        out.append("::");
        for(String name : scope)
        {
            out.append(name).append("::");
        }        
        return out.toString();
    }
}

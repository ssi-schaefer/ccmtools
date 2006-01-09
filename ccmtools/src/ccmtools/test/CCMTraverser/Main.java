package ccmtools.test.CCMTraverser;

import java.io.File;

import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.UI.Driver;

public class Main
{

    public static void main(String[] args)
    {
        File idlFile = new File(System.getProperty("user.dir"),"src/ccmtools/test/CCMTraverser/test.idl");
        System.out.println("> parse: " + idlFile);
        
        GraphTraverser traverser = new CcmGraphTraverser();
        ParserManager parserManager = new ParserManager(Driver.M_NONE);
        traverser.addHandler(new SimpleNodeHandler());

        parserManager.reset();
        parserManager.setOriginalFile(idlFile.toString());
        MContainer ccmModel = null;
                
        try {
            String modelIdentifier = idlFile.getName().split("\\.")[0];
            ccmModel = parserManager.parseFile(idlFile.toString());
            if(ccmModel != null) {
                ccmModel.setIdentifier(modelIdentifier);
                traverser.traverseGraph(ccmModel);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}

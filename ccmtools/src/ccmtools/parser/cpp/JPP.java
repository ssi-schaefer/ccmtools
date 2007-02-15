/***
 * Jonathan: an Open Distributed Processing Environment 
 *
 * The contents of this file are subject to the JOnAS Public License Version 
 * 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License on the JOnAS web site. *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * See the License for the specific terms governing rights and limitations under 
 * the License. 
 *
 * The Original Code is JOnAS application server code released July 1999. 
 *
 * The Initial Developer of the Original Code is Bull S.A.
 * The Original Code and portions created by Bull S.A. are
 *    Copyright (C) 1999 Bull S.A. All Rights Reserved. 
 *
 * Contributor(s): Fran?ois Horn (rewrite for Jonathan). 
 */
package ccmtools.parser.cpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * JPP as Java PreProcessor.
 * This class allows to preprocess a jpp file included the directives
 * <ul>
 * <li>
 * #include
 * <li>
 * #ifdef
 * <li>
 * #ifndef
 * <li>
 * #else
 * <li>
 * #endif
 * </ul>
 */

public class JPP {
   private static final int JPP_INST_NB = 9;
   public static final int NO_JPP_INST = -1;
   public static final int INST_INCLUDE = 0;
   public static final int INST_DEFINE  = 1;
   public static final int INST_IFDEF   = 2;
   public static final int INST_IFNDEF  = 3;
   public static final int INST_IF      = 4;
   public static final int INST_ELSE    = 5;
   public static final int INST_ELIF    = 6;
   public static final int INST_ENDIF   = 7;
   public static final int INST_UNDEF   = 8;

   public static final int BUFSIZE = 1024;

   static final String[] INST_TOKEN =
      { "#include","#define","#ifdef","#ifndef","#if","#else","#elif","#endif","#undef" };
   
   private static final String JAVA_DELIM = " \t\n\r;,.{}()[]+-*/%<>=&|!?:#";

   private static final String NULLSTRING = "";
   private static final Object NULLSCOPE = new Object();
   private static final Object IFSCOPE = new Object();
   private static final Object ELSESCOPE = new Object();

   Hashtable defined_names = new Hashtable();
   int nb_defs = 0;
   
   private static int jppToken(String nm) {
      for (int i = 0;i<JPP_INST_NB;i++) if (nm.startsWith(INST_TOKEN[i])) return i;
      return -1;
   }

   private JPPExpandedStream jpp_fIn;
   private OutputStream jpp_fOut;
   private int errorNumber = 0;

   /* *************************************************************************
    * This included class provides an inputstream offering a readLine() method 
    * and  handling #include directives.
    ************************************************************************* */

   static class JPPExpandedStream {   
      Vector iDirs = null;
      
      Stack fStack = null;
      Stack nameStack = null;
      Stack nbStack  = null;
      
      BufferedReader fCur = null;
      String nameCur = null;
      int nbCur = -1;
      
      
      JPPExpandedStream (String fileName, Vector includeDirs)  throws java.io.IOException {
         //fCur = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
         fCur=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
         iDirs = includeDirs;
         fStack = new Stack();
         nameStack = new Stack();
         nbStack = new Stack();
         nameCur = fileName;
         nbCur = 0;
      }
      
      String  readLine() throws java.io.IOException {
         String line = fCur.readLine();
         nbCur++;
         
         if (line == null) {
            if (!fStack.empty()) {
               fCur.close();
               nbCur = ((Integer) nbStack.pop()).intValue();
               line = "# "+nbCur+" "+"\""+nameCur+"\""+"      2";
               nameCur = (String) nameStack.pop();
               fCur = (BufferedReader) fStack.pop();
            }
         } else {
            if (line.startsWith("#include")) {
               String fiName;
               StringTokenizer stLine = new StringTokenizer(line);
               stLine.nextToken();
               fiName = stLine.nextToken();
               fiName = this.includeFileName(fiName.substring(1,fiName.length()-1));
               
               if (fiName == null) {
                  System.out.println("Unknown include file : "+fiName);
                  return "";
               }
               else {
                  fStack.push(fCur);
                  nameStack.push(nameCur);
                  nbStack.push(new Integer(nbCur));
                  
                  //fCur = new DataInputStream(new BufferedInputStream(new FileInputStream(fiName)));
                  fCur=new BufferedReader(new InputStreamReader(new FileInputStream(fiName)));
                  nameCur = new String(fiName);
                  line = "# "+nbCur+" "+"\""+fiName+"\""+"      1";
                  nbCur = 0;
               }
            }
         }
         
         return(line);
      }
      
      private String includeFileName(String fName) {
         String fiName = null;
         Enumeration dirs = iDirs.elements();
         while (dirs.hasMoreElements()) {
            fiName = new String((String)dirs.nextElement()+File.separatorChar+fName);
            File f = new File(fiName);
            if (f.exists()) return fiName;
         }
         return(null);
      }
      
      String getFileName() { return(nameCur); }
      int getLineNumber() { return(nbCur); } 
      void close()  throws java.io.IOException { fCur.close(); }  
   }

   /* *************************************************************************
    * This included class provides an inputstream offering a readLine() method.
    ************************************************************************* */

   static class JPPInputStream extends FilterInputStream {
      public static final int BUFSIZE = 1024;
      public static final String NULLSTRING = "";
      
      byte[] buffer = new byte[BUFSIZE];
      boolean isMacFile;
      
      public JPPInputStream(InputStream iS,boolean isM) { super(iS);  isMacFile = isM; }
      public JPPInputStream(InputStream iS) { this(iS,false); }
      
      public int read() throws IOException {
         return super.read();
      }
      
      public String readLine() throws IOException {
         int i = readLine(buffer);
         if (i == -1) return null;
         else return new String(buffer,0,i);
      }
      
      public int readLine(byte[] buf) throws IOException {
         boolean escaped = false;
         boolean CRLFEscaped = false;
         boolean endWhile = false;
         int i = -1,car = 0,maxL = buf.length-1;
         
         while(!endWhile && i<maxL && (car = read())!= -1) {
            switch(car) {
               default :
                  if (escaped) {
                     buf[++i] = (byte) '\\';
                     escaped = false;
                  }
                  buf[++i] = (byte) car;
                  if (CRLFEscaped) CRLFEscaped = false;
                  break;
               case '\\' :
                  if (escaped) {
                     buf[++i] = (byte) '\\';
                     buf[++i] = (byte) '\\';
                  }
                  escaped = !escaped; 
                  if (CRLFEscaped) CRLFEscaped = false;
                  break;    
               case '\r' : case '\n' :
                  if (!escaped) {
                     if (!CRLFEscaped) endWhile = true;
                     else CRLFEscaped = false;
                  } 
                  else if (!CRLFEscaped) CRLFEscaped = true;
                  else CRLFEscaped = false;
                  break;
            }
         }
         if (i>= 0 || endWhile) return i+1; else return -1;
      }
   }
   
   /**
    * JPP Constructor
    *
    * @param    fileNameIn     path of the jpp file
    * @param    outputStream   outputStream where to send the result
    * @param    includeDirs    list of the 'include' directories
    * @param    definedNames   list of the defined names
    */

   public JPP(String fileNameIn, OutputStream outS, Vector includeDirs, Hashtable defNames) 
      throws java.io.IOException {
      jpp_fIn  = new JPPExpandedStream(fileNameIn, includeDirs);
      jpp_fOut = outS;
      defined_names = defNames;
   }

   public boolean preprocess() throws java.io.IOException {
      String line = null;
      int negTest = 0,posTest = 0;
      Stack ifScope = new Stack();
      Object curScope = NULLSCOPE;
      ifScope.push(curScope);

      while(true) {
         line = jpp_fIn.readLine();
         if (line == null) {
            jpp_fIn.close();
            return (errorNumber == 0);
         }
         
         StringTokenizer stLine;
         String nom1,nom2;
         
         switch(jppToken(line)) {
            case INST_DEFINE :
               if (negTest>0) break;
               stLine = new StringTokenizer(line.trim());
               stLine.nextToken();
               if (stLine.hasMoreTokens()) nom1 = stLine.nextToken();
               else {
                  printErrorMsg(jpp_fIn,"illegal empty definition of preprocessor variable",true);
                  break;
               }
               if (stLine.hasMoreTokens()) {
                  nom2 = stLine.nextToken();
                  while (stLine.hasMoreTokens()) nom2 = nom2 + " " + stLine.nextToken();
               } else {
                  nom2 = NULLSTRING;
               }
               if (defined_names.containsKey(nom1)) {
                  printErrorMsg(jpp_fIn,"redefinition of preprocessor variable : "+nom1,false);
               } else {
                  nb_defs++;
               }
               defined_names.put(nom1,nom2);
               break;
            case INST_UNDEF :
               if (negTest>0) break;
               stLine = new StringTokenizer(line);
               stLine.nextToken(); 
               if (stLine.hasMoreTokens()) nom1 = stLine.nextToken();
               else {
                  printErrorMsg(jpp_fIn,"illegal empty (un)definition of preprocessor variable",true);
                  break;
               }
               if (defined_names.containsKey(nom1)) {
                  defined_names.remove(nom1);
                  nb_defs--;
               } else {
                  printErrorMsg(jpp_fIn,"(un)definition of unknown preprocessor variable : "+nom1,false);
               }
               break;
            case INST_IFDEF :
               curScope = IFSCOPE;
               ifScope.push(curScope);
               if (negTest>0) { negTest++; break; }
               stLine = new StringTokenizer(line);
               stLine.nextToken(); 
               if (stLine.hasMoreTokens()) nom1 = stLine.nextToken();
               else {
                  printErrorMsg(jpp_fIn,"illegal empty #ifdef test of preprocessor variable",true);
                  break;
               }
               if (defined_names.containsKey(nom1)) posTest++;  else negTest++;
               break;
            case INST_IFNDEF :
               curScope = IFSCOPE;
               ifScope.push(curScope);
               if (negTest>0) { negTest++; break; }
               stLine = new StringTokenizer(line);
               stLine.nextToken(); 
               if (stLine.hasMoreTokens()) nom1 = stLine.nextToken();
               else {
                  printErrorMsg(jpp_fIn,"illegal empty #ifndef test of preprocessor variable",true);
                  break;
               }
               if (!defined_names.containsKey(nom1)) posTest++;  else negTest++;
               break;
            case INST_IF :
               printErrorMsg(jpp_fIn,"unsupported #if processor directive ",true);
               break;
            case INST_ENDIF :
               if (curScope == NULLSCOPE) {
                  printErrorMsg(jpp_fIn,"unbalanced #endif processor directive ",true);
                  break;
               }
               curScope =ifScope.pop();
               if (negTest>0) negTest--;
               else if (posTest>0) posTest--;
               break;
            case INST_ELIF :
               printErrorMsg(jpp_fIn,"unsupported #elif processor directive ",true);
               break;
            case INST_ELSE :
               if (curScope != IFSCOPE) {
                  printErrorMsg(jpp_fIn,"unbalanced #else processor directive ",true);
                  break;
               }
               ifScope.pop();
               curScope = ELSESCOPE;
               ifScope.push(curScope);
               if (negTest>0) negTest--;
               else { negTest++; posTest--; }
               break;
            case NO_JPP_INST :
               if (negTest>0) break;
               if (nb_defs == 0) {
                  jpp_fOut.write(line.getBytes());
                  jpp_fOut.write('\n');
               }
               else {
                  stLine = new StringTokenizer(line.trim(),JAVA_DELIM,true);
                  String line2;
                  if (stLine.hasMoreTokens()) {
                     line2 = "";
                     while (stLine.hasMoreTokens()) {
                        String tok2 = stLine.nextToken();
                        String tok3 = (String) defined_names.get(tok2);
                        if (tok3 == null) line2 = line2+tok2;
                        else line2 = line2+tok3;
                     }
                     jpp_fOut.write(line2.getBytes());
                  }
                  jpp_fOut.write('\n');
               }
               break; 
            case INST_INCLUDE :
               printErrorMsg(jpp_fIn,"preprocessor ERROR : unexpected #include directive",true);
         }
      }
   }

   void printErrorMsg(JPPExpandedStream jE,String msg,boolean isErr) {
      System.out.println("File "+jE.getFileName()+" at line "+jE.getLineNumber()+" : "+msg);
      if (isErr) errorNumber++;
   }
}

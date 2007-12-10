/* UserCode */

/* will be copied verbatim into the beginning of the source file of the generated lexer */ 
package Formularix.MathML.Import;

import java_cup.runtime.*;


%% /* Options and declarations */

%public
%class Scanner
%extends sym

%unicode

%line
%column

%cup
 
/* will be copied verbatim into the generated class */ 
%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
/*InputCharacter = [^\r\n]*/

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} 

/*| {EndOfLineComment} | {DocumentationComment}*/

TraditionalComment = "<!--" ~"-->"



/* Unicode Literal */


UnicodeCharLiteral = ({UnicodeCharLit1}|{UnicodeCharLit2}|{UnicodeCharLit3})

UnicodeCharLit1 = "&#" {FLit3} ";"
UnicodeCharLit2 = "&" [a-zA-Z]+ ";"
UnicodeCharLit3 = [a-zA-Z0-9‰¸ˆƒ÷‹=|%/\\#*+~,.;:\-_?!\(\)\[\]\{\}']


/* String Literal */
StringLiteral = [^\r\n\"]*
Identifier = [a-zA-Z][a-zA-Z0-9]*
EnclosedString = "<ms>" [^\r\n\"<>&]+ "</ms>"
  
/* floating point literals */        
DoubleValue = ({FLit1}|{FLit2}|{FLit3}) 


/* Floating Point Literals */
FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 

/* Initial-Werte */

InitialMathOpen = "<math" {Parameter}? ">"

/* XML Parameter */
Parameter = ({WhiteSpace}+ {AParameter})+
AParameter = {Identifier} {WhiteSpace}* "=\"" {StringLiteral} "\""


%state TEXT, CHARLITERAL

%% /* LexicalRules */

<YYINITIAL> {

  /* Initial, General */
  {InitialMathOpen}		 { return symbol(MATHHEADER_OPEN); }  
  "</math>"			 { return symbol(MATHHEADER_CLOSE); }    
  ">"				 { return symbol(GT); }    
      
  /* Token Elements */
  "<mo>"			 { return symbol(OPERATOR_OPEN); }
  "</mo>"			 { return symbol(OPERATOR_CLOSE); }  
  "<mn>"			 { return symbol(NUMBER_OPEN); }
  "</mn>"			 { return symbol(NUMBER_CLOSE); }
  "<mi>"			 { return symbol(IDENTIFIER_OPEN); }
  "</mi>"			 { return symbol(IDENTIFIER_CLOSE); }
  /*"<ms>"			 { return symbol(STRING_OPEN); }  */
  /*"</ms>"			 { return symbol(STRING_CLOSE); }  */
  "<mspace/>"			 { return symbol(SPACE_OPENCLOSE); }

  /* General Layout Schemata */
  "<mrow>"			 { return symbol(ROW_OPEN); }
  "</mrow>"			 { return symbol(ROW_CLOSE); }
  "<mfrac>"			 { return symbol(FRACTION_OPEN); }
  "</mfrac>"			 { return symbol(FRACTION_CLOSE); }
  "<msqrt>"			 { return symbol(SQUAREROOT_OPEN); }
  "</msqrt>"			 { return symbol(SQUAREROOT_CLOSE); }
  "<mroot>"			 { return symbol(ROOT_OPEN); }
  "</mroot>"			 { return symbol(ROOT_CLOSE); }
  "<mfenced"			 { return symbol(FENCE_OPEN); }
  "</mfenced>"			 { return symbol(FENCE_CLOSE); }
  
  /* Script and Limit Schemata */
  "<msub>"			 { return symbol(SUBSCRIPT_OPEN); }
  "</msub>"			 { return symbol(SUBSCRIPT_CLOSE); }
  "<msup>"			 { return symbol(SUPERSCRIPT_OPEN); }
  "</msup>"			 { return symbol(SUPERSCRIPT_CLOSE); }
  "<msubsup>"			 { return symbol(SUBSUPERSCRIPT_OPEN); }
  "</msubsup>"			 { return symbol(SUBSUPERSCRIPT_CLOSE); }
  "<mover>"			 { return symbol(OVERSCRIPT_OPEN); }
  "</mover>"			 { return symbol(OVERSCRIPT_CLOSE); }
  "<munder>"			 { return symbol(UNDERSCRIPT_OPEN); }
  "</munder>"			 { return symbol(UNDERSCRIPT_CLOSE); }
  "<munderover>"		 { return symbol(UNDEROVERSCRIPT_OPEN); }
  "</munderover>"		 { return symbol(UNDEROVERSCRIPT_CLOSE); }
  
  /* Tables and Matrices */
  "<mtable>"			 { return symbol(TABLEORMATRIX_OPEN); }
  "</mtable>"			 { return symbol(TABLEORMATRIX_CLOSE); }
  "<mtr>"			 { return symbol(MATRIXROW_OPEN); }
  "</mtr>"			 { return symbol(MATRIXROW_CLOSE); }
  "<mtd>"			 { return symbol(MATRIXELEMENT_OPEN); }
  "</mtd>"			 { return symbol(MATRIXELEMENT_CLOSE); }
  
  /* Intern */
  "<cursor/>"			 { return symbol(CURSOR); }
  "<mbr/>"			 { return symbol(MBR); }
      
  
  {DoubleValue}                  { return symbol(FLOATING_POINT_LITERAL, new String(yytext())); }
  
  {UnicodeCharLiteral}		 { return symbol(UNICODECHAR, new String(yytext())); }
  
  /* Parameter */
  {AParameter}                   { return symbol(APARAMETER, new String(yytext())); }
  
  /* <ms> String </ms> */
  {EnclosedString}		 { return symbol(ENCLOSED_STRING, new String(yytext().substring(4,yytext().length()-5))); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
  
}



/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return symbol(EOF); }
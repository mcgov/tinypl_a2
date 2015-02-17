package tinypl;
public class TinyPL {
		
	public static void main(String args[]) {   
		   Lexer.lex();
		   new Program();  
	}
}

class Program {
	public Program(){
	System.out.println( Lexer.nextToken );
	 switch (Lexer.nextToken) {
		 case Token.KEY_BEGIN: 
			 System.out.println("Found Token Begin");
			 //some other shit
			 //call next thing.
			 break;
		default:
			System.out.println("Nooooo");
			//call exception method.
	 		}
	}
}

class Decls {
	 
}

class Idlist {
	 
}

class Stmts {
	 
}

class Stmt {
	 
} 

class Assign {
	 
}

class Cond  {
	 
}

class Loop {

}

class Cmpd  {
	 
}


class Expr {  
	 
}

class Term {  

}


class Factor {  
	 
}

class Literal {
	 
}

class Int_Lit extends Literal {
	 
	
}

class Real_Lit extends Literal {
	 
	
}

class Bool_Lit extends Literal {
	 
	
}

class Id_Lit extends Literal {

	
}


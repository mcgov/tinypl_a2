
public class TinyPL {
		
	public static void main(String args[]) {   
		   Lexer.lex();
		   new Program();  
	}
}

class Program {
	public Program(){
	System.err.println( Lexer.nextToken );
	 switch (Lexer.nextToken) {
		 case Token.KEY_BEGIN: 
			 System.err.println("Found Token BEGIN.");
			 Lexer.lex();
			 new Decls();

			 //Lexer.lex();
			 new Stmts();

			 Lexer.lex();
			 switch (Lexer.nextToken){	
				case Token.KEY_END:
					System.err.println("Found Token END.");
					break;
				default:
					System.err.println("ERROR: Missing END Token.");
				}
			 break;
		default:
			System.err.println("ERROR: Missing BEGIN Token.");
			//call exception method.
	 		}
	}
}

class Decls {
	public Decls(){
	System.err.println("Declare Stuff!" );
	//int or real or bool
	if ( Lexer.nextToken == Token.KEY_INT){
			System.err.println("FOUND: Int Token");
			Lexer.lex();
			new Idlist();
			if ( Lexer.nextToken != Token.SEMICOLON ){
				System.err.println("ERROR: Missing Semicolon");
			} else{ System.err.println("FOUND: Semicolon."); }
			Lexer.lex();
 
		}	
	} 
}

class Idlist {
	public Idlist(){
	//process first token.
	 
	process_id();
	Lexer.lex();
	//process middle;
	System.err.println("Token " + Lexer.nextToken ) ; 
	while ( Lexer.nextToken == Token.COMMA ){

		System.out.println("FOUND: Token COMMA.");
		//We have another id to process, 0 or more.	
		Lexer.lex();
		process_id();
		Lexer.lex();
		}
	}

	public void process_id(){
		switch (Lexer.nextToken){
        case Token.ID:
            System.err.println("FOUND: Token ID");
            break;
        default:
            System.err.println("ERROR: Missing ID Literal in IDList.");
        }

		}
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



public class TinyPL {
		

public static void find_semi(){
	if ( Lexer.nextToken != Token.SEMICOLON ){
				System.err.println("ERROR: Missing Semicolon");
			} else{ 
				System.err.println("FOUND: Semicolon."); 
				Lexer.lex();
			}
}

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
			 if ( Lexer.nextToken == Token.KEY_INT ||
			 		Lexer.nextToken == Token.KEY_BOOL ||
			 		Lexer.nextToken == Token.KEY_REAL ){
			 	new Decls();
			}
			while ( Lexer.nextToken == Token.LEFT_BRACE ){
				new Stmts();
			}
					///derp, messy.
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
			new Idlist();
			TinyPL.find_semi();
 		
		}
	
	if (Lexer.nextToken == Token.KEY_REAL){
		System.err.println("FOUND: Real token");
		new Idlist();
		TinyPL.find_semi();

		}
	
	if (Lexer.nextToken == Token.KEY_BOOL){ 
		System.err.println("FOUND: Bool Token.");
		new Idlist();
		TinyPL.find_semi();
		
		}
	}

}

class Idlist {
	public Idlist(){
	//process first token.
	Lexer.lex();
	process_id();
	Lexer.lex();
	//process middle;
	//System.err.println("Token " + Lexer.nextToken ) ; 
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
	public Stmts(){
		if (Lexer.nextToken == Token.LEFT_BRACE){ 
			System.err.println("FOUND: Left Brace.");
			Lexer.lex();
		}
		else
			System.err.println("ERROR: Missing Left Brace!");

		new Stmt();

		if ( Lexer.nextToken == Token.RIGHT_BRACE ){
			System.err.println("FOUND: Right Brace.");
			Lexer.lex();
		}
		else
			System.err.println("ERROR: Missing Right Brace!");

	}
	 
}

class Stmt {
	public Stmt(){
	switch (Lexer.nextToken){
		case Token.ID:
			//TODO: store id name
			Lexer.lex();
			System.out.println(Lexer.nextToken);
			String msg = (Lexer.nextToken == Token.ASSIGN_OP) ? "FOUND:" : "MISSING:";
			System.err.println(msg + " Assignment Operation (=).");
			Lexer.lex();
			new Expr();
			TinyPL.find_semi();

			//new Expr();
			break;
		case Token.KEY_WHILE:
			Lexer.lex();
		default:
			System.out.println("ERROR: This statement is garbage.");
		

		}
	}
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


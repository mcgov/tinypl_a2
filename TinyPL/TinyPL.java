

public class TinyPL {
		

public static void find_semi(){
	//evaulate the current next token to see if it's a terminator.
	if ( Lexer.nextToken != Token.SEMICOLON ){
				System.err.println("ERROR: Missing Semicolon");
			} else{ 
				System.err.println("FOUND: Semicolon."); 
				Lexer.lex();
			}
}

public static void expect_token(int tokenVal){
	//more general version of the above, to check for any token, just feed the token value.
	//Token.<WhicheverTokenType>
	String msg = (Lexer.nextToken == tokenVal) ? "FOUND:" : "MISSING:";
	System.err.println(msg + " " + Token.toString(tokenVal) + ".");
	if ( msg == "FOUND:" )
		Lexer.lex();
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
		TinyPL.expect_token(Token.LEFT_BRACE);
		new Stmt();

		TinyPL.expect_token(Token.RIGHT_BRACE);
	}
	 
}

class Stmt {
	public Stmt(){
	switch (Lexer.nextToken){
		case Token.ID:
			//TODO: store id name
			System.err.println("BEGIN: ASSIGNMENT OPERATION.");
			Lexer.lex();
			TinyPL.expect_token(Token.ASSIGN_OP);
			new Expr();
			TinyPL.find_semi();
			System.err.println("END: ASSIGNMENT OPERATION.");

			break;
		case Token.KEY_WHILE:
			System.err.println("BEGIN: WHILE LOOP.");
			Lexer.lex();
			TinyPL.expect_token( Token.LEFT_PAREN );
			new Cond();
			TinyPL.expect_token( Token.RIGHT_PAREN );
			new Stmt();
			break;

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


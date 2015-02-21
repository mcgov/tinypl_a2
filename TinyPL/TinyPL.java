

public class TinyPL {
		
public static boolean hasMoreStatements(){
	//Evaluate whether the next token indicates the start of another stmt.
	switch (Lexer.nextToken){
		case Token.ID:
			//TODO: store id name
			return true;

		case Token.KEY_WHILE:
			return true;
			
		case Token.KEY_IF:
			return true;

		case Token.LEFT_BRACE:
			return true;

		default:
			return false;		
		}

}

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
			TinyPL.expect_token(Token.KEY_BEGIN);
			 if ( Lexer.nextToken == Token.KEY_INT ||
			 		Lexer.nextToken == Token.KEY_BOOL ||
			 		Lexer.nextToken == Token.KEY_REAL ){
			 	new Decls();
			}
			
			new Stmts();
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
		System.out.println( TinyPL.hasMoreStatements() );
		while ( TinyPL.hasMoreStatements() ){
			new Stmt();
			}
	 
	}

}

class Stmt {
	public Stmt(){
	switch (Lexer.nextToken){
		case Token.ID:
			//TODO: store id name
			new Assign();
			break;

		case Token.KEY_WHILE:
			new Loop();
			break;
		case Token.KEY_IF:
			new Cond();
			break;
		case Token.LEFT_BRACE:
			System.err.println("FOUND: Left Brace.");
			new Cmpd();
			TinyPL.expect_token(Token.RIGHT_BRACE);

			break;

		default:
			System.out.println("ERROR: This statement is garbage.");
		

		}
	}
} 

class Assign {
	public Assign(){
	 System.err.println("BEGIN: ASSIGNMENT OPERATION.");

	 //Buffer should be pointing at the var name.
			Lexer.lex();
			TinyPL.expect_token(Token.ASSIGN_OP);
			new Expr();
			TinyPL.find_semi();
			System.err.println("END: ASSIGNMENT OPERATION.");
	}

}

class Cond  {
	public Cond(){
		System.err.println("BEGIN: CONDITIONAL STATEMENT.");
		Lexer.lex();
		TinyPL.expect_token(Token.LEFT_PAREN);
		new Expr();
		TinyPL.expect_token(Token.RIGHT_PAREN);
		new Stmt();
		if ( Lexer.nextToken == Token.KEY_ELSE ){
			System.err.println("FOUND: ELSE STATEMENT.");
			Lexer.lex();
			new Stmt();
		}
	}

}

class Loop {
	public Loop(){ 
			System.err.println("BEGIN: WHILE LOOP.");
			Lexer.lex();
			TinyPL.expect_token( Token.LEFT_PAREN );
			new Expr();
			TinyPL.expect_token( Token.RIGHT_PAREN );
			new Stmt();
			System.err.println("END: WHILE LOOP.");
		}
}

class Cmpd  {
	public Cmpd(){
		System.err.println("BEGIN CMPD STATEMENT:");
		Lexer.lex();
		new Stmts();
		System.err.println("END CMPD STATEMENT.");
	}	
	 
}


class Expr {  
	public Expr(){
		System.err.println("CREATING EXPRESSION...");
	 	new Term();
	 	if (Lexer.nextToken == Token.ADD_OP || 
	 		Lexer.nextToken == Token.SUB_OP ||
	 		Lexer.nextToken == Token.OR_OP    ) {
	 		System.err.println("FOUND ADDITIONAL EXPRESSION: " + Token.toString(Lexer.nextToken) );
	 		Lexer.lex();
	 		new Expr();
	 	}

	}
}

class Term {  
	public Term(){
		System.err.println("RESOLVE: Term.");
	new Factor();
	if (Lexer.nextToken == Token.MULT_OP || 
	 		Lexer.nextToken == Token.DIV_OP ||
	 		Lexer.nextToken == Token.AND_OP ) {
	 		System.err.println("FOUND ADDITIONAL EXPRESSION: " + Token.toString(Lexer.nextToken) );
	 		Lexer.lex();
	 		new Term();
	 	}
	 }

}


class Factor {  
	public Factor(){
		System.err.println("RESOLVE: Factor.");
		switch (Lexer.nextToken){
			case Token.NEG_OP:
				System.err.println("FOUND: Negation Operation.");
				Lexer.lex();
				break;
			case Token.LEFT_PAREN:
				Lexer.lex();
				System.out.println("FOUND TOKEN: " + Token.toString(Lexer.nextToken));

				new Expr();
				if (Lexer.nextToken == Token.RIGHT_PAREN){
					TinyPL.expect_token(Token.RIGHT_PAREN);
					//and we're done.
				} else {
					System.err.println("FOUND AN EQUIVALENCE STATEMENT.");
					switch ( Lexer.nextToken ){
						case Token.LE_OP:
							TinyPL.expect_token(Token.LE_OP);
							break;
						case Token.GE_OP:
							TinyPL.expect_token(Token.GE_OP);
							break;
						case Token.NE_OP:
							TinyPL.expect_token(Token.NE_OP);
							break;
						case Token.EQ_OP:
							TinyPL.expect_token(Token.EQ_OP);
							break;
						case Token.LT_OP:
							TinyPL.expect_token(Token.LT_OP);
							break;
						case Token.GT_OP:
							TinyPL.expect_token(Token.GT_OP);
							break;	
						default:
							System.err.println("ERROR: You fucked up.");
						} //switch
					new Expr();
					TinyPL.expect_token(Token.RIGHT_PAREN);
				}
				///handle weird cases
				break;
			default:
				switch ( Lexer.nextToken ){
					case Token.INT_LIT:
						new Int_Lit();
						break;
					case Token.REAL_LIT:
						new Real_Lit();
						break;
					case Token.ID:
						new Id_Lit();
						break;
					case Token.TRUE_LIT:
						//do something?
						new Bool_Lit();
						break;
					case Token.FALSE_LIT:
						new Bool_Lit();
						break;
					default:
						System.out.println("ERROR: Literal couldn't resolve.");
						Lexer.lex();
						break;
					}
		}
		//new Literal();
	
	}
	 
}

class Literal {
	public int type;
	public Literal(){
		System.err.println("RESOLVED TO: Literal Type " + Token.toString(Lexer.nextToken));
		this.type = Lexer.nextToken;
	}
	 
}

class Int_Lit extends Literal {
	public Int_Lit(){
		System.err.println(Token.toString(Lexer.nextToken) + " RESOLVED TO INT LITERAL " );
		Lexer.lex();

	}
	 
	
}

class Real_Lit extends Literal {
	 
	
}

class Bool_Lit extends Literal {
	public boolean value;
	 public Bool_Lit(){
	 	
	 	System.err.println("BOOL LITERAL CREATION.");
	 	
	 	if (Lexer.nextToken == Token.TRUE_LIT){
	 		TinyPL.expect_token(Token.TRUE_LIT);
	 		this.value = true;
	 	}
	 	else if( Lexer.nextToken == Token.FALSE_LIT ){
	 		TinyPL.expect_token(Token.FALSE_LIT);
	 		this.value = false;
	 	} else {
	 		System.err.println("Something Resolved Incorrectly.");
	 		Lexer.lex();
	 	}
	 	
	 }
	
}

class Id_Lit extends Literal {
	public Id_Lit(){
		System.err.println("Found ID Literal.");
		Lexer.lex();
	}

	
}


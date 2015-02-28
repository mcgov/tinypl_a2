import java.util.HashMap;

public class TinyPL {
	public static HashMap<String, Id_Lit> map;		
	 
	
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
		else 
			TinyPL.fail(tokenVal);
	} 
	
	public static int exp_contains_conflicts(Expr e){
		//will recursively check all subthings in an expression and return whether any of the things
		//have type conflicts.
		if ( e == null || e.term == null){ return -1; }
		else {
			
			int type = term_contains_conflicts(e.term);
			System.err.println("Term Contains Code: "  + type);
			if ( e.expr != null && type  >= 0) {
				if ( exp_contains_conflicts( e.expr ) != type ){
					System.err.println("E.EXPR type != type: " + type);
					return -1;
				}
			
			}
			return type;
		}
	}
	//TODO: Write out printlns or tests for this.
	//check that the right types are actually being assigned to start with.
	public static int term_contains_conflicts(Term t){
		if (t == null || t.fac == null){
			return -1;
		} else{
			int type = fac_contains_conflicts(  t.fac );
			if ( t.t != null){
				if( term_contains_conflicts( t.t ) != type ){
					return -1;
				}
			
			}
			return type;
		}
	}
	
	public static int fac_contains_conflicts(Factor f){
		if (f == null || f.boo == null && f.exp1 == null && 
				f.exp2 == null && f.id == null && 
				f.in == null && f.real == null){
			return -1;
		} else{
			int type = fac_contains_conflicts(  f.fac );
			if ( type == -1 ) { type = exp_contains_conflicts(f.exp1); }
			if ( type == -1 ) { type = exp_contains_conflicts(f.exp2); }
			if ( type == -1 ) {
				if ( f.id != null ) { return f.id.type ; }
				if ( f.boo != null ) { return 1; }
				if ( f.real != null ) { return 2 ; }
				if ( f.in != null ) { return 0; }
				
			}
			return type;
		}
	}
	
	
	
	
	public static void fail(int token){
		//easyfail method :D
		
		String msg = "Failed due to missing token: " + Token.toString(token);
		System.out.println(msg);
		System.exit(1);
		}
	
	public static void failVar(Exception E){
		//easyfail method :D
		
		String msg = "Failed due to Exception: " + E;
		System.out.println(msg);
		System.exit(1);
		}

	public static void main(String args[]) {   
		   Program p;
		   Lexer.lex();
		   p = new Program();
		   
	}
}



class Program {
	
	Decls d;
	Stmts ss;
	public Program(){
		TinyPL.map = new HashMap<String, Id_Lit>();
	
	System.err.println( Lexer.nextToken );
	 switch (Lexer.nextToken) {
		 case Token.KEY_BEGIN: 
			TinyPL.expect_token(Token.KEY_BEGIN);
			 if ( Lexer.nextToken == Token.KEY_INT ||
			 		Lexer.nextToken == Token.KEY_BOOL ||
			 		Lexer.nextToken == Token.KEY_REAL ){
			 	d = new Decls();
			}
			
			ss = new Stmts();
					///derp, messy.
			 switch (Lexer.nextToken){	
				case Token.KEY_END:
					System.err.println("Found Token END.");
					System.out.println("Program Accepted!");
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
	Idlist idInt, idReal , idBool;
	public Decls(){
		
	System.err.println("Declare Stuff!" );
	//int or real or bool
	if ( Lexer.nextToken == Token.KEY_INT){
			System.err.println("FOUND: Int Token");
			idInt = new Idlist(0);
			TinyPL.find_semi();
 		
		}
	
	if (Lexer.nextToken == Token.KEY_REAL){
		System.err.println("FOUND: Real token");
		idReal = new Idlist(1);
		TinyPL.find_semi();
		}

	if (Lexer.nextToken == Token.KEY_BOOL){ 
		System.err.println("FOUND: Bool Token.");
		idBool = new Idlist(2);
		TinyPL.find_semi();
		
		}
	

	

	}

}

class Idlist {
	Id_Lit id;
	Idlist more;
	int type; // 0 int 1 real 3 bool;
	public Idlist(int t){
		type = t;
	//process first token.
	Lexer.lex();
	process_id(type);
	
	//process middle;
	//System.err.println("Token " + Lexer.nextToken ) ; 
	if ( Lexer.nextToken == Token.COMMA ){
		System.out.println("FOUND: Token COMMA.");
		//We have another id to process, 0 or more.	
		
		more = new Idlist(type);
		}
	}

	public void process_id(int type){
		switch (Lexer.nextToken){
        case Token.ID:
            try{ id = new Id_Lit(type); }
            catch (VariableInitializedException e){ TinyPL.failVar(e); }
            break;
        default:
            System.err.println("ERROR: Missing ID Literal in IDList.");
        }

		}
}

class Stmts {
	Stmt s;
	Stmts ssss;
	public Stmts(){
		
		System.out.println( "HAS MORE?" + TinyPL.hasMoreStatements() );
		
		if ( TinyPL.hasMoreStatements() ){
			s = new Stmt();
			ssss = new Stmts();
			
			}
	 
	}

}

class Stmt {
	Assign ass;
	Loop loop;
	Cond cond;
	Cmpd cmpd;
	
	public Stmt(){
		
	switch (Lexer.nextToken){
		case Token.ID:
			//TODO: Store var names.
			ass = new Assign();
			break;

		case Token.KEY_WHILE:
			loop = new Loop();
			break;
		case Token.KEY_IF:
			cond = new Cond();
			break;
		case Token.LEFT_BRACE:
			System.err.println("FOUND: Left Brace.");
			cmpd = new Cmpd();
			TinyPL.expect_token(Token.RIGHT_BRACE);

			break;

		default:
			System.out.println("ERROR: This statement is garbage.");
			TinyPL.fail(Lexer.nextToken);

		}
	}
} 

class Assign {
	Expr exp;
	public Assign(){
	 System.err.println("BEGIN: ASSIGNMENT OPERATION.");
		String varname = String.valueOf(Lexer.ident);
		if (!TinyPL.map.containsKey(varname)){
			NoSuchVariableException e = new NoSuchVariableException();
			TinyPL.failVar(e); 
			} else {
				System.err.println("VAR IN MAP.");	
			}
			Lexer.lex();
			TinyPL.expect_token( Token.ASSIGN_OP );
			exp = new Expr();
			int conflicts = TinyPL.exp_contains_conflicts(exp);
			if (conflicts < 0){ System.out.println("TYPE MISMATCH");
			} else { System.out.println("TYPES MATCH! YAAAY."); }
			TinyPL.find_semi();
			System.err.println("END: ASSIGNMENT OPERATION.");
	

}
}

class Cond  {
	Expr exp;
	Stmt stmt, els;
	public Cond(){
		System.err.println("BEGIN: CONDITIONAL STATEMENT.");
		Lexer.lex();
		//TinyPL.expect_token(Token.LEFT_PAREN);
		if (Lexer.nextToken != Token.LEFT_PAREN){
			TinyPL.fail(Token.LEFT_PAREN);
			}
		exp = new Expr();
		stmt = new Stmt();
		if ( Lexer.nextToken == Token.KEY_ELSE ){
			System.err.println("FOUND: ELSE STATEMENT.");
			Lexer.lex();
			els = new Stmt();
		}
	}

}

class Loop {
	Expr exp;
	Stmt stmt;
	public Loop(){ 
			System.err.println("BEGIN: WHILE LOOP.");
			Lexer.lex();
			if (Lexer.nextToken != Token.LEFT_PAREN){
				TinyPL.fail(Token.LEFT_PAREN);
				}
			//TinyPL.expect_token( Token.LEFT_PAREN );
			exp = new Expr();
			//TinyPL.expect_token( Token.RIGHT_PAREN );
			stmt = new Stmt();
			System.err.println("END: WHILE LOOP.");
		}
}

class Cmpd  {
	Stmts stmts;
	public Cmpd(){
		System.err.println("BEGIN CMPD STATEMENT:");
		Lexer.lex();
		stmts = new Stmts();
		System.err.println("END CMPD STATEMENT.");
	}	
	 
}


class Expr {  
	Term term;
	Expr expr;
	public Expr(){
		System.err.println("CREATING EXPRESSION...");
	 	term = new Term();
	 	if (Lexer.nextToken == Token.ADD_OP || 
	 		Lexer.nextToken == Token.SUB_OP ||
	 		Lexer.nextToken == Token.OR_OP    ) {
	 		System.err.println("FOUND ADDITIONAL EXPRESSION: " + Token.toString(Lexer.nextToken) );
	 		Lexer.lex();
	 		expr = new Expr();
	 	}

	}
}

class Term {  
	Factor fac;
	Term t;
	public Term(){
		System.err.println("RESOLVE: Term.");
	fac = new Factor();
	if (Lexer.nextToken == Token.MULT_OP || 
	 		Lexer.nextToken == Token.DIV_OP ||
	 		Lexer.nextToken == Token.AND_OP ) {
	 		System.err.println("FOUND ADDITIONAL EXPRESSION: " + Token.toString(Lexer.nextToken) );
	 		Lexer.lex();
	 		t = new Term();
	 	}
	 }

}


class Factor {
	Factor fac;
	Expr exp1, exp2;
	Int_Lit in;
	Real_Lit real;
	Bool_Lit boo;
	Id_Lit id;
	
	public Factor(){
		
		System.err.println("RESOLVE: Factor.");
		switch (Lexer.nextToken){
			case Token.NEG_OP:
				System.err.println("FOUND: Negation Operation.");
				Lexer.lex();
				fac = new Factor();
				break;
			case Token.LEFT_PAREN:
				Lexer.lex();
				//System.out.println("FOUND TOKEN: " + Token.toString(Lexer.nextToken));

				exp1 = new Expr();
				System.err.println(Lexer.nextToken);
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
					exp2 = new Expr();
					TinyPL.expect_token(Token.RIGHT_PAREN);
				}
				///handle weird cases
				break;
			default:
				switch ( Lexer.nextToken ){
					case Token.INT_LIT:
						in = new Int_Lit();
						break;
					case Token.REAL_LIT:
						real = new Real_Lit();
						break;
					case Token.ID:
						if (!TinyPL.map.containsKey(String.valueOf(Lexer.ident)) ){
						NoSuchVariableException e = new NoSuchVariableException();
						TinyPL.failVar(e); 
						} else {
							Lexer.lex();
						}
						break;
					case Token.TRUE_LIT:
						//do something?
						boo = new Bool_Lit();
						break;
					case Token.FALSE_LIT:
						boo = new Bool_Lit();
						break;
					default:
						System.out.println("ERROR: Literal couldn't resolve.");
						TinyPL.fail(Lexer.nextToken);
						
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
	public int value;
	public Int_Lit(){
		System.err.println(Token.toString(Lexer.nextToken) + " RESOLVED TO INT LITERAL " );
		Lexer.lex();

	}
	 
	
}

class Real_Lit extends Literal {
	public double value;
	 public Real_Lit(){
	 	System.err.println("RESOLVED TO: Real Literal.");
	 	Lexer.lex();
	 }
	
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
	public int type;
	public Id_Lit(int t) throws VariableInitializedException{
		System.err.println("Found ID Literal: " + Lexer.ident);
		String ident = String.valueOf(Lexer.ident);
		if ( TinyPL.map.containsKey(ident) ){
			throw new VariableInitializedException();  }
		 else {
			TinyPL.map.put( ident , this );  }
		Lexer.lex();
		//System.out.println(Token.toString( Lexer.nextToken ) );
	}

	
}

@SuppressWarnings("serial")
class VariableInitializedException extends Exception{
	
}

@SuppressWarnings("serial")
class NoSuchVariableException extends Exception{
	
}

@SuppressWarnings("serial")
class TypeMismatchException extends Exception {
	
}



package tinypl;
import java.io.*;

public class Lexer {

	static public int nextToken;
	static public char ident = ' ';
	static public int intValue;
	static public double realValue;
	
	static public char ch = ' ';
	static private Buffer buffer = new Buffer(new DataInputStream(System.in));

	public static int lex() {

		while (Character.isWhitespace(ch))
			ch = buffer.getChar();
		if (Character.isLetter(ch)) {
			ident = Character.toLowerCase(ch);
			ch = buffer.getChar();
				if (ident == 'i' && ch == 'n') {
					ch = buffer.getChar(); // t
					ch = buffer.getChar();
					nextToken = Token.KEY_INT;
				} else if (ident == 'i' && ch == 'f') {
					ch = buffer.getChar(); // f
					nextToken = Token.KEY_IF;
				} else  if (ident == 'r' && ch == 'e') {
					ch = buffer.getChar(); // a
					ch = buffer.getChar(); // l
					ch = buffer.getChar();
					nextToken = Token.KEY_REAL;
				} else  if (ident == 'e' && ch == 'n') {
					ch = buffer.getChar(); // d
					ch = buffer.getChar();
					nextToken = Token.KEY_END;
				} else if (ident == 'e' && ch == 'l') {
					ch = buffer.getChar(); // s
					ch = buffer.getChar(); // e
					ch = buffer.getChar();
					nextToken = Token.KEY_ELSE;
				} else if (ident == 'b' && ch == 'o') {
					ch = buffer.getChar(); // o
					ch = buffer.getChar(); // l
					ch = buffer.getChar();
					nextToken = Token.KEY_BOOL;
				} else if (ident == 't' && ch == 'r') {
					ch = buffer.getChar(); // u
					ch = buffer.getChar(); // e
					ch = buffer.getChar();
					nextToken = Token.TRUE_LIT;
				}
					else if (ident == 'f' && ch == 'a') {
						ch = buffer.getChar(); // l
						ch = buffer.getChar(); // s
						ch = buffer.getChar(); // e
						ch = buffer.getChar();
						nextToken = Token.FALSE_LIT;
				} else if (ident == 'w' && ch == 'h') {
					ch = buffer.getChar(); // i
					ch = buffer.getChar(); // l
					ch = buffer.getChar(); // e
					ch = buffer.getChar();
					nextToken = Token.KEY_WHILE;
			} else if (ident == 'b' && ch == 'e') {
					ch = buffer.getChar(); // g
					ch = buffer.getChar(); // i
					ch = buffer.getChar(); // n
					ch = buffer.getChar();
					nextToken = Token.KEY_BEGIN;
			} else
					nextToken = Token.ID;
			}
		else if (Character.isDigit(ch)) {
			nextToken = getNumToken();
		} else {
			switch (ch) {
			case ';':
				nextToken = Token.SEMICOLON;
				ch = buffer.getChar();
				break;
			case ',':
				nextToken = Token.COMMA;
				ch = buffer.getChar();
				break;
			case '.':
				nextToken = Token.PERIOD;
				ch = buffer.getChar();
				break;
			case '+':
				nextToken = Token.ADD_OP;
				ch = buffer.getChar();
				break;
			case '-':
				nextToken = Token.SUB_OP;
				ch = buffer.getChar();
				break;
			case '*':
				nextToken = Token.MULT_OP;
				ch = buffer.getChar();
				break;
			case '/':
				nextToken = Token.DIV_OP;
				ch = buffer.getChar();
				break;
			case '!':
				nextToken = Token.NEG_OP;
				ch = buffer.getChar();
				if (ch == '=') { 
					nextToken = Token.NE_OP;
					ch = buffer.getChar();
				}
				break;
			case '=':
				nextToken = Token.ASSIGN_OP;
				ch = buffer.getChar();
				if (ch == '=') { 
					nextToken = Token.EQ_OP;
					ch = buffer.getChar();
				}
				break;
			case '<':
				nextToken = Token.LT_OP;
				ch = buffer.getChar();
				if (ch == '=') { 
					nextToken = Token.LE_OP;
					ch = buffer.getChar();
				}
				break;
			case '>':
				nextToken = Token.GT_OP;
				ch = buffer.getChar();
				if (ch == '=') { 
					nextToken = Token.GE_OP;
					ch = buffer.getChar();
				}
				break;
			case '&':
				ch = buffer.getChar(); // &
				ch = buffer.getChar();
				nextToken = Token.AND_OP;
				break;
			case '|':
				ch = buffer.getChar(); // |
				ch = buffer.getChar();
				nextToken = Token.OR_OP;
				break;
			case '(':
				nextToken = Token.LEFT_PAREN;
				ch = buffer.getChar();
				break;
			case ')':
				nextToken = Token.RIGHT_PAREN;
				ch = buffer.getChar();
				break;
			case '{':
				nextToken = Token.LEFT_BRACE;
				ch = buffer.getChar();
				break;
			case '}':
				nextToken = Token.RIGHT_BRACE;
				ch = buffer.getChar();
				break;
			default:
				error("Illegal character " + ch);
				break;
			}
		}
		return nextToken;
	} // lex

	public void match(int x) {
		nextToken = lex();
		if (nextToken != x) {
			error("Invalid token " + Token.toString(nextToken)
					+ "-- expecting " + Token.toString(x));
			System.exit(1);
		} // if
	} // match

	public static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	} // error

	private static int getNumToken() {
		int num = 0;
		do {
			num = num * 10 + Character.digit(ch, 10);
			ch = buffer.getChar();
		} while (Character.isDigit(ch));
		if (ch == '.') {
			double decimal = 0.0;
			ch = buffer.getChar();
			int divisor = 1;
			do {
				divisor = divisor * 10;
				decimal = decimal + Character.digit(ch, 10)*1.0/divisor;
				ch = buffer.getChar();
			} while (Character.isDigit(ch));
			realValue = num + decimal;
			return Token.REAL_LIT;
		} else {
			intValue = num;
			return Token.INT_LIT;
		}
	}

} // Lexer


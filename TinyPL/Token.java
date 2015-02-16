class Token {
	public static final int SEMICOLON = 0;
	public static final int COMMA = 1;
	public static final int PERIOD = 2;
	public static final int ADD_OP = 3;
	public static final int SUB_OP = 4;
	public static final int MULT_OP = 5;
	public static final int DIV_OP = 6;
	public static final int ASSIGN_OP = 7;
	public static final int LT_OP = 8;
	public static final int GT_OP = 9;
	public static final int LEFT_PAREN = 10;
	public static final int RIGHT_PAREN = 11;
	public static final int LEFT_BRACE = 12;
	public static final int RIGHT_BRACE = 13;
	public static final int ID = 14;
	public static final int INT_LIT = 15;
	public static final int REAL_LIT = 16;
	public static final int KEY_IF = 17;
	public static final int KEY_INT = 18;
	public static final int KEY_REAL = 19;
	public static final int KEY_BOOL = 20;
	public static final int TRUE_LIT = 21;
	public static final int FALSE_LIT = 22;
	public static final int KEY_ELSE = 23;
	public static final int AND_OP = 24;
	public static final int OR_OP = 25;
	public static final int KEY_END = 26;
	public static final int NEG_OP = 27;
	public static final int KEY_WHILE = 28;
	public static final int EQ_OP = 29;
	public static final int LE_OP = 30;
	public static final int GE_OP = 31;
	public static final int NE_OP = 32;
	public static final int KEY_BEGIN = 33;

	private static String[] lexemes = { ";", ",", ".", "+", "-", "*", "/", "=",
			"<", ">", "(", ")", "{", "}", "id", "intlit", "reallit", "if",
			"int", "real", "bool", "true", "false", "else", "&&", "||", "!", "end", "while",
			"==", "<=", ">=", "!=", "begin"};

	public static String toString(int i) {
		if (i < 0 || i > 33)
			return "";
		else
			return lexemes[i];
	}
}

import java.util.regex.*;
import java.util.ArrayList;

/* Tokenizer class that translates input text into terminal tokens 
 * generatePatter() uses the java.util.regex.Matcher and Pattern 
 * utilities to create a regex string of the terminal tokens and
 * transforms the strings of characters into tokens
 */

public class Tokenizer {

	public enum TokenType {
		WHITESPACE("[ \t]+"),			// white spaces
		NEWLINE("\n"),					// new lines
		IF("if"),						// if clause
		THEN("then"),					// then clause
		WHILE("while"),					// while clause
		ELSE("else"),
		BOOLEAN("(true|false)"),		// true false clauses
		PRINT("System.out.println"),	// print statement
		OPENBRAC("\\{"),				// open brackets
		CLOSEBRAC("\\}"),				// close brackets
		OPENPARAN("\\("),				// open parantheses
		CLOSEPARAN("\\)"),				// close parantheses
		SEMICOLON("\\;"),				// semicolon
		NOT("\\!"), 					// not operator

		/* TODO: should undefined identifiers/ variables/ undefined non-terminals be tokenized? */
		NUMBER("-?[0-9]+"),				// digits
		IDENTIFIER("[a-zA-Z][a-zA-Z0-9]*");	// identifiers

		private String pattern;

		TokenType(String pattern) {
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}
	}

	public static class Token {
		TokenType type;	
		String tok;
		int line_num;
		int line_offset;

		public Token(TokenType type, String tok, int lineNum, int offset) {
			this.type = type;
			this.tok = tok;
			this.line_num = lineNum;
			this.line_offset = offset;
		}

		public Token() {}

		// override toString method to print out 
		@Override
		public String toString() {
			return ("Token(typ='" + type + "', value='" + tok + "', line=" + line_num + ", offset=" + line_offset);
		}
	}

	public static String generatePattern() {
		// create token pattern string
		StringBuffer patternsBuffer = new StringBuffer();
		for (TokenType t : TokenType.values()) {
			patternsBuffer.append("|(?<" + t.name() + ">" + t.getPattern() + ")");
		}	
		// convert string buffer to string and remove first intance of the "|" character
		// String patterns = patternsBuffer.toString().substring(1);
		return patternsBuffer.toString().substring(1);
	}

	public static ArrayList<Token> tokenize(String s) {
		ArrayList<Token> tokens = new ArrayList<Token>();	// create empty list to add completed tokens to
		// to add tokens to list, call
		// tokens.add(new Token(blah, blah, blah, blah));

		int lineNum = 1; 	// initialize starting line number, start lex at first line
		int offset = 0; 	// initialize offset to start of line, which is 0

		String patterns = generatePattern();

		// create pattern and matcher instances
		Pattern p = Pattern.compile(patterns);
		Matcher m = p.matcher(s);

		// loop through for matches
		while (m.find()) {
			String value = m.group(0);

			if (m.group(TokenType.NEWLINE.name()) != null) {	// encountered new line 
				lineNum++;		// increment line number
				offset = 0;		// reset offset
				// TODO: should I keep the token?
			}
			else if (m.group(TokenType.WHITESPACE.name()) != null) {	// encountered white space
				offset++; 		// increment offset, throw away the token
			}
			// else if (m.group(TokenType.IDENTIFIER.name()) != null || m.group(TokenType.NUMBER.name()) != null) {	
			// 	// encountered random string of letters or numbers not in set of terminal symbols
			// 	System.out.println("Parse error");
			// 	System.exit(0);
			// }
			else {
				for (TokenType t : TokenType.values()) {	// encountered any terminal tokens add to tokens list
					if (m.group(t.name()) != null) {
						tokens.add(new Token(t, value, lineNum, offset));
						offset += value.length();		// increment offset by length of token
					}
				}
			}
		}

		// DEBUG: prints out accepted tokens
		// for (Token t : tokens)
		// 	System.out.println(t);
		// System.out.println("\n");

		return tokens; 		// list of lexed tokens
	}

}
import java.util.*;
import java.util.ArrayList;

public class Parse extends Tokenizer { 

	// declaration of instance variables
	public static ArrayList<Token> tokens;		// holds tokens found by tokenizer
	public static Token currToken;				// holds the current token to be processed
	public static ListIterator<Token> it;

	// called when a parse error is encountered
    public static void parseError() {
    	System.out.println("Parse error");
    	System.exit(0);
    }

    // returns the next token in the arraylist
    public static Token nextToken() {
    	if (it.hasNext()) {
    		return it.next();
    	}
    	else {	// no more tokens, return null token
			Token emptyToken = new Token();
    		return emptyToken;
    	}
    }

    // 'consumes' the current token and acquires the next one
    public static void eatToken(TokenType t) {
    	if (currToken.type == t) {
    		currToken = nextToken();
    	}
    	else {
    		// TODO: error, token eaten is not correct token
    	}
    }

    /* Parse functions:
	 * parseTerminal() handles the terminal symbols which is all the tokens
	 * parseE(), parseL(), and parseS() handles the E, L, and S non-terminals respectively 
    */
    public static Boolean parseTerminal(TokenType t) {
    	if (currToken.type == t) {
    		// System.out.println("Parsing " + t);
    		eatToken(t);
    		return true;
    	}
    	return false;
    }

    public static Boolean parseE() {
    	if (currToken.type == TokenType.BOOLEAN) {
    		// System.out.println("Parsing boolean");
    		eatToken(TokenType.BOOLEAN);
    		// System.out.println(currToken.toString());
    		return true;
    	}
    	else if (currToken.type == TokenType.NOT) {
    		// System.out.println("Parsing not");
    		eatToken(TokenType.NOT);
    		return parseE();
    	}
    	return false;
    }

    public static Boolean parseL() {
    	if (currToken.type == TokenType.OPENBRAC 
    		|| currToken.type == TokenType.PRINT 
    		|| currToken.type == TokenType.IF 
    		|| currToken.type == TokenType.WHILE) {
    		// System.out.println("Parsing L");
    		return ( parseS() && parseL() );
    	}
    	else { 
    		// System.out.println("Parsing epsilon"); 
    		return true; 
    	}
    }

	public static Boolean parseS() {
		if (currToken.type == TokenType.OPENBRAC) {
			// System.out.println("Parsing {");
			eatToken(TokenType.OPENBRAC);
			// System.out.println(currToken.toString());
			return (parseL() && parseTerminal(TokenType.CLOSEBRAC));
		}
		else if (currToken.type == TokenType.PRINT) {
			// System.out.println("Parsing print");
			eatToken(TokenType.PRINT);
			// System.out.println(currToken.toString());
			return ( parseTerminal(TokenType.OPENPARAN) && parseE() && parseTerminal(TokenType.CLOSEPARAN) && parseTerminal(TokenType.SEMICOLON) );
		}
		else if (currToken.type == TokenType.IF) {
			// System.out.println("Parsing if");
			eatToken(TokenType.IF);
			return ( parseTerminal(TokenType.OPENPARAN) && parseE() && parseTerminal(TokenType.CLOSEPARAN) && parseS() && parseTerminal(TokenType.ELSE) && parseS() );
		}
		else if (currToken.type == TokenType.WHILE) {
			// System.out.println("Parsing while");
			eatToken(TokenType.WHILE);
			return ( parseTerminal(TokenType.OPENPARAN) && parseE() && parseTerminal(TokenType.CLOSEPARAN) && parseS() );
		}
		return false;
	}


    public static void main (String [] args) {

    	Scanner scan = new Scanner(System.in);
    	StringBuffer inputStringBuffer = new StringBuffer();

    	while (scan.hasNextLine())
    		inputStringBuffer.append(scan.nextLine()).append("\n");
    	String inputString = inputStringBuffer.toString();

    	tokens = tokenize(inputString);
    	it = tokens.listIterator();
    	currToken = nextToken();

    	Boolean parseStatus = parseS();		// parseS() is the start rule

    	if (!parseStatus || currToken.type != null)		// if any returned false or there are tokens remaining that are not parsed
    		System.out.println("Parse error");
    	else 
    		System.out.println("Program parsed successfully");
    }

}

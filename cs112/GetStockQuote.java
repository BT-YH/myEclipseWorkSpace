import java.util.Scanner;

public class GetStockQuote {

	public static void main(String[] args) {
		try {
			System.out.print("Enter a ticker symbol: ");
			Scanner in = new Scanner(System.in);
			String symbol = in.next().trim().toUpperCase();
			String URLString = String.format("https://finance.yahoo.com/quote/%s?p=%s", symbol, symbol); 
			java.net.URL url = new java.net.URL(URLString);
			in.close();
			
			in = new Scanner(url.openStream());
			
			// Note: finance.yahoo.com page structure changes over time, so you'll likely need to 
			// revise the next line(s) on occasion:
			in.findWithinHorizon("\"regularMarketPrice\":\\{\"raw\":", 0); 
			String restOfLine = in.nextLine();
			int indexAfter = restOfLine.indexOf(",");
			System.out.println("finance.yahoo.com share price: $" + restOfLine.substring(0, indexAfter));
			
			in.close();
		}
		catch (java.net.MalformedURLException ex) {
			System.out.println("Invalid ticker symbol");
		}
		catch (java.io.IOException ex) {
			System.out.println("I/O Errors: no such file");
		}
	}
}

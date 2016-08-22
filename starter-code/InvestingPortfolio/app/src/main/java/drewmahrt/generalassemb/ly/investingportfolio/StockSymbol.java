package drewmahrt.generalassemb.ly.investingportfolio;

/**
 * Created by owlslubic on 8/22/16.
 */
public class StockSymbol {
    String Symbol;
    String LastPrice;

    public String getLastPrice() {
        return LastPrice;
    }

    public void setLastPrice(String lastPrice) {
        LastPrice = lastPrice;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}

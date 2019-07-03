package coindesk;

import java.time.LocalDate;

public interface IData {
    
    String BASE_URL = "https://api.coindesk.com/v1/bpi";
    
    void getBPI() throws CoinException;
    
    String getLastResponse();

	void getBPI(LocalDate minusDays, LocalDate date, String inputData) throws CoinException;
}
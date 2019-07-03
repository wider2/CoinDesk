package coindesk.data;

import coindesk.CoinException;
import coindesk.ConnectData;

public class GetData extends ConnectData {

    private static final String REAL_ENDPOINT = BASE_URL + "/currentprice.json";
	 
    public GetData() throws CoinException {
        super();
        super.setURL(REAL_ENDPOINT);
    }
  
    /*
     * default behavior. Using for test mostly.
     */
    @Override
    public void getBPI() throws CoinException {
        super.setURL(REAL_ENDPOINT);
        getBPI("", getURL());
    }

    /*
     * get real currency rate of bitcoin in real time
     */
    public boolean getBPI(String currencyCode) throws CoinException {
        setURL(REAL_ENDPOINT.replace(".json", String.format("/%s.json", currencyCode)));
        return getBPI(currencyCode, getURL());
    }
   
}

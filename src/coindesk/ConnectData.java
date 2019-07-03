package coindesk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;

import org.json.JSONObject;
import coindesk.utils.TextUtils;

public class ConnectData implements IData {
        
        private static final int DEFAULT_BUFFER_SIZE = 1024;
        private static final String NEWLINE = System.getProperty("line.separator");
        private HttpURLConnection httpUrlConnection;
        private BufferedReader bufferedReader;
        private final StringBuilder stringBuilder;

        private URL url;

        protected ConnectData() {
            this(DEFAULT_BUFFER_SIZE);
        }
        
        protected ConnectData(int initialCapacity) {
            stringBuilder = new StringBuilder(initialCapacity);
        }

        /**
         * grab real data of CoinDesk
         */
        protected boolean getBPI(String currencyCode, URL url) throws CoinException {
            String line, jsonResult;
            try {
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setRequestProperty("accept", "application/json");
                httpUrlConnection.setDoOutput(true);
                bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append(NEWLINE);
                }
                jsonResult = stringBuilder.toString();
                
                if (!TextUtils.isEmpty(jsonResult)) {
                    JSONObject mainJsonObject = new JSONObject(jsonResult);
                    JSONObject bpiObject = mainJsonObject.getJSONObject("bpi");
                    JSONObject gbp = bpiObject.getJSONObject(currencyCode.toUpperCase());
                    String code = gbp.getString("code");
                    String rate = gbp.getString("rate");
                    System.out.println("Today " + code + " bitcoin rate = " + rate);
                } else {
                	System.out.println("No Json data found.");
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        /*
         * get lowest and highest bitcoin rate from archive source
         */
        protected void getArchiveBPI(URL url) throws CoinException {
            String line, jsonResult;
            float currentRate, lowRate = 0, highRate = 0;
            int iCount = 0;
            
            try {
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setRequestProperty("accept", "application/json");
                httpUrlConnection.setDoOutput(true);
                bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append(NEWLINE);
                }
                jsonResult = stringBuilder.toString();
                
                if (!TextUtils.isEmpty(jsonResult)) {
                    JSONObject mainJsonObject = new JSONObject(jsonResult);
                    JSONObject bpiObject  = mainJsonObject.getJSONObject("bpi");
                    
                    Iterator<String> iterator = bpiObject.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        currentRate = Float.parseFloat(bpiObject.optString(key));
                        if (iCount == 0) lowRate = currentRate;
                        if (currentRate > highRate) highRate = currentRate;
                        if (currentRate < lowRate) lowRate = currentRate;
                        iCount +=1;
                    }
                    System.out.println("Lowest rate in the last 30 days = " + lowRate); 
                    System.out.println("Highest rate in the last 30 days = " + highRate);
                } else {
                	System.out.println("No Json archive data found.");
                }
            } catch (Exception ex) {
                System.out.println("IO Exception occurs. Please check the input.");
            }
        }
        
        /**
         * return last data found
         */
        public String getLastResponse() {
            return stringBuilder.toString();
        }

        protected URL getURL() {
            return url;
        }

        protected void setURL(String urlString) throws CoinException {
            if (url == null || !urlString.equals(url.toString())) {
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException malformedURLException) {
                    System.out.println("malformed url: " + malformedURLException.getMessage());
                    throw new CoinException(malformedURLException.getMessage(), CoinException.URL_ERROR);
                }
            }
        }

        @Override
        public String toString() {
            return getLastResponse();
        }

		@Override
		public void getBPI() throws CoinException {			
		}

		@Override
		public void getBPI(LocalDate minusDays, LocalDate date, String inputData) throws CoinException {
		}
}

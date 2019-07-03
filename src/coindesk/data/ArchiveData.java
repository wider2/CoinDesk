package coindesk.data;

import java.time.LocalDate;

import coindesk.CoinException;
import coindesk.ConnectData;

public class ArchiveData extends ConnectData {

    private static final String ARCHIVE_ENDPOINT = BASE_URL + "/historical/close.json";
    //public static final String USD_INDEX = "USD";

    public ArchiveData() throws CoinException {
        super();
    }

    public ArchiveData(int bufferSize) throws CoinException {
        super(bufferSize);
    }

    public void getBPI(LocalDate start, LocalDate end, String currency) throws CoinException {
        setURL(String.format("%s?start=%s&end=%s&currency=%s", ARCHIVE_ENDPOINT, start, end, currency));
        getArchiveBPI(getURL());
    }

}

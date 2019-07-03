package coindesk.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import coindesk.CoinException;
import coindesk.IData;
import coindesk.data.ArchiveData;
import coindesk.data.GetData;

class TestCoin {

    private String inputData = "EUR";
    private IData getData;
    private static DateFormat dateFormat;
    private static int daysToSubtract;
    private static LocalDate date;
    
    public static void main(String args[]) {       

    }

    @BeforeClass
    public static void setUpClass() {
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	daysToSubtract = 30;
    }
     
    @Before
    public void setUp() {
    	
    }
    
    @Test
    void testRealData() {
        try {
            getData = new GetData();
            getData.getBPI();
            String result = getData.getLastResponse();
            System.out.println(result);
               
            
            assertNotNull(result);
        } catch (CoinException coinException) {
            coinException.printStackTrace(System.err);
        }
    }

    @Test
    void testRealDataSuccess() {
        try {
            GetData getData = new GetData();
            boolean success = getData.getBPI(inputData);
            assertTrue(success);
        } catch (CoinException coinException) {
            coinException.printStackTrace(System.err);
        }
    }

    @Test
    void testRealDataFailure() {
        try {
            GetData getData = new GetData();
            boolean success = getData.getBPI("");
            assertFalse(success);
        } catch (CoinException coinException) {
            coinException.printStackTrace(System.err);
        }
    }
    
    @Test
    void testArchive() {
        try {
        	date = LocalDate.now();
            
            getData = new ArchiveData();
            getData.getBPI(date.minusDays(daysToSubtract), date, inputData);
            String result = getData.getLastResponse();
            
            System.out.println(result);
            
            assertNotNull(result);    
        } catch (CoinException coinException) {
            coinException.printStackTrace(System.err);
        }
    }

    @Test
    void testArchiveWrongParams() {
        try {
        	date = LocalDate.now();
        	
            getData = new ArchiveData();
            getData.getBPI(date.plusDays(daysToSubtract), date, inputData);
            String result = getData.getLastResponse();
            
            System.out.println(result);
            assertEquals(0, result.length());
            
        } catch (CoinException coinException) {
            coinException.printStackTrace(System.err);
        }
    }

}

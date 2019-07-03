package coindesk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import coindesk.data.ArchiveData;
import coindesk.data.GetData;
import coindesk.utils.TextUtils;

/**
 * @author Aleksei Jegorov, 25.06.2019
 */

public class Start {

    private static Scanner scannerInput;
    
    public static void main(String[] args) {
        System.out.println("CoinDesk waits for currency input: ");
        
        int daysToSubtract = 30;
        LocalDate date = LocalDate.now();
        
        try {
            scannerInput = new Scanner(System.in);
            String inputData;
            do {
                inputData = scannerInput.nextLine();
                if (!inputData.toLowerCase().matches("quit")) {
                    if (TextUtils.isEmpty(inputData)) {
                        System.out.println("Please input correct currency name.");
                    } else {
                        GetData getData = new GetData();
                        boolean success = getData.getBPI(inputData);
                        
                        if (success) {
                            ArchiveData archieveData = new ArchiveData();
                            archieveData.getBPI(date.minusDays(daysToSubtract), date, inputData);
                        } else {
                            System.out.println("There is no luck to get bitcoin data this time.");
                        }
                    }
                } else {
                    System.out.println("Good bye.");
                }
            }
            while(!inputData.toLowerCase().matches("quit"));
        } catch (Exception ex) {
            System.out.println("Coindesk application error: " + ex.getMessage());
        }
    }
    
}

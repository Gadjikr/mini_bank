package com.minibank.test;

import com.minibank.data.DataSource;
import com.minibank.domain.AccumulateSavingsBatch;
import com.minibank.reporting.CustomerReport;

import java.io.IOException;
import java.util.Locale;

public class TestReport {
    private static final String USAGE = "USAGE: com.minibank.test.TestReport <dataFilePath>";

    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);

        // Retrieve the dataFilePath command-line argument
        if (args.length != 1) {
            System.out.println(USAGE);
        } else {
            String dataFilePath = args[0];

            try {
                System.out.println("Reading data file: " + dataFilePath);
                // Create the data source and load the bank data
                DataSource dataSource = new DataSource(dataFilePath);
                dataSource.loadData();

                //Run the customers report
                CustomerReport report = new CustomerReport();
                report.generateReport();
                AccumulateSavingsBatch jobAddPercents = new AccumulateSavingsBatch();
                jobAddPercents.doBatch();
                report.generateReport();

            } catch (IOException ioe) {
                System.out.println("could not load the data file");
                System.out.println(ioe.getMessage());
                ioe.printStackTrace(System.err);
            }



        }
    }
}

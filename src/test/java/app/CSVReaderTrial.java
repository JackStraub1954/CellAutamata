package app;

import java.io.IOException;
import java.io.StringReader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class CSVReaderTrial
{

    public static void main(String[] args)
        throws CsvValidationException, IOException
    {
        StringReader    strReader   = new StringReader( "arg1, arg2, arg3" );
        CSVReader       csvReader   = new CSVReader( strReader );
        String[]        tokens      = csvReader.readNext();
        csvReader.close();
        for ( String token : tokens )
            System.out.print( token.strip() + " " );
        System.out.println();
    }

}

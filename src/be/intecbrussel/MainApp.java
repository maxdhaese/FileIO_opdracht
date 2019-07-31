package be.intecbrussel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws IOException {
        List<File> allFiles = new ArrayList<>();
        //Link to source and destination file
        File unsorted = new File("C:\\Users\\Public\\unsorted");
        File sorted = new File("C:\\Users\\Public\\sorted");

        //call all methods to create the maps and summary
        try {


            SortingUtils.list(unsorted, allFiles);
            SortingUtils.newDirectory(allFiles, sorted);
            SortingUtils.createSummary(sorted);

            System.out.println("All files sorted and copied");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

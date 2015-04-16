package Logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gonçalo Lobo on 16/04/2015.
 */

public class City {


    ArrayList[][] city;

    public City(String name){

        if(name.equals("Porto"))
            this.city = loadCity("C:\\Users\\Gonçalo Lobo\\Desktop\\IART-Tourist\\porto.csv"); // hardcoded :|
        else
        if(name.equals("Lisboa"))
            this.city = loadCity("C:\\Users\\Gonçalo Lobo\\Desktop\\IART-Tourist\\lisboa.csv");
        else System.err.println("There's no file database for that city.");

    }

    public ArrayList[][] getCity() {
        return city;
    }

    public ArrayList[][] loadCity(String csv) {

        ArrayList[][] c = new ArrayList[10][10];
        c[0][0] = new ArrayList();

        String csvFile = csv;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));

            for (int i = 0; (line = br.readLine()) != null;i++) {

                String[] info = line.split(cvsSplitBy);

                for( int j = 0; j<info.length;j++ ) {
                    c[i][j] = new ArrayList();
                    c[i][j].add(info[j]);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return c;

    }

    public String getCityName() {
        return city[0][0].toString().replace("[","").replace("]","");
    }

    public String getPlaceName(int i) {
        return city[i][0].toString().replace("[","").replace("]","");
    }

    public float getPlaceDuration(int i) {
        return Float.parseFloat(city[i][i].toString().replace("[", "").replace("]", ""));
    }

    public float getPlacesDistance(int i, int j) {
        return Float.parseFloat(city[i][j].toString().replace("[","").replace("]",""));
    }
}

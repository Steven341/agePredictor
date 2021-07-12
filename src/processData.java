import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class processData {
    String [] states = {"AL","AK","AZ","AR","CA",
            "CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS",
            "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE",
            "NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA",
            "RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI",
            "WY"};
    public String ListType() throws IOException {
        InputStream input = new FileInputStream("resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty("ListType");
    }
    public String Directory() throws IOException {
        InputStream input = new FileInputStream("resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty("Directory");
    }
    public void read() throws Exception {

        if (ListType()=="ArrayList"){
            ArrayList<String[]> a = new ArrayList<>();
            for (String s: this.states){
                File myObj = new File(Directory()+s+".TXT");
                System.out.println(Directory()+s+".TXT");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    a.add(parse(data));
                }
                myReader.close();
            }
        }
        else if (ListType()=="LinkedList"){
            LinkedList<String[]> l = new LinkedList<>();
            for (String s: this.states){
                File myObj = new File(Directory()+s+".TXT");
                System.out.println(Directory()+s+".TXT");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    l.add(parse(data));
                }
                myReader.close();
            }
        }


    }
    public String[] parse(String data){
        return data.split(",");
    }

    public static void main(String[] args) throws IOException {
        processData d = new processData();
        try {
            d.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

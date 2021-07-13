import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Properties;

public class processData {
    final String [] states = {"AL","AK","AZ","AR","CA",
            "CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS",
            "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE",
            "NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA",
            "RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI",
            "WY"};
    ArrayList<String[]> a = new ArrayList<>();
    LinkedList<String[]> l = new LinkedList<>();

    //helpers
    //return ListType value in property file
    private String ListType() throws IOException {
        InputStream input = new FileInputStream("resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty("ListType");
    }
    //return Directory value in property file
    private String Directory() throws IOException {
        InputStream input = new FileInputStream("resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty("Directory");
    }
    //parse String to Array separate by comma
    private String[] parse(String data){return data.split(",");}
    //check if State abbreviation is valid
    private boolean validState(String State){
        for (String s:this.states){
            if (s.equals(State))
                return true;
        }
        return false;
    }
    //check gender is valid
    private boolean validGender(String Gender){return (Gender.equals("F")||Gender.equals("M")); }
    //check if combination is contained in ssa files
    public boolean validCombination(){
        if (a.size ==0&&l.size()==0)
            return false;
        return true;}

    // read from txt file and
    // add to ArrayList or LinkedList
    private void read(String State,String Gender,String Name) throws Exception {
        if (ListType().equals("ArrayList"))
            writeToArray(State,Gender,Name);
        else if (ListType().equals("LinkedList"))
            writeToLinkedList(State,Gender,Name);

    }
    //if gender and name matches with input value, put it into ArrayList
    private void writeToArray(String State,String Gender,String Name) throws Exception {
        File myObj = new File(Directory()+State+".TXT");
        System.out.println(Directory()+State+".TXT");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (parse(data)[1].equals(Gender)&&parse(data)[3].equals(Name))
                a.add(parse(data));
        }
        myReader.close();

    }
    //if gender and name matches with input value, put it into LinkedList
    private void writeToLinkedList(String State,String Gender,String Name) throws Exception {
        File myObj = new File(Directory()+State+".TXT");
        System.out.println(Directory()+State+".TXT");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (parse(data)[1].equals(Gender)&&parse(data)[3].equals(Name))
                l.add(parse(data));
        }
        myReader.close();

    }

    private int findMAX() throws Exception {
        int maxValue = 0;
        int maxIndex = 0;
        if (ListType().equals("ArrayList")){
            for (int i = 0;i<a.size-1;i++){
                if (Integer.parseInt(a.get(i)[4])>maxValue){
                    maxValue = Integer.parseInt(a.get(i)[4]);
                    maxIndex = i;
                }
            }
        }
       return maxIndex;
    }

    public void menu() throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter state:");
        String state = reader.readLine();
        while (!validState(state)){
            System.out.println("Invalid State Abbreviation");
            System.out.println("Enter state:");
            state = reader.readLine();
        }
        System.out.println("Enter Gender:");
        String gender = reader.readLine();
        while (!validGender(gender)){
            System.out.println("Invalid Gender Input(F/M)");
            System.out.println("Enter Gender:");
            gender = reader.readLine();
        }
        System.out.println("Enter name");
        String name = reader.readLine();
        read(state,gender,name);


    }
    public static void main(String[] args) throws Exception {
        processData d = new processData();
        d.menu();
        while(!d.validCombination()){
            System.out.println("Did not find state,gender,name combination.Try again");
            d.menu();
        }
        System.out.println(Arrays.toString(d.a.get(d.findMAX())));



    }
}

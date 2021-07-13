import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Properties;
public class predictor {
    final String [] states = {"AL","AK","AZ","AR","CA",
            "CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS",
            "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE",
            "NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA",
            "RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI",
            "WY"};
    ArrayList<String[]> a = new ArrayList<>();//ArrayList
    LinkedList<String[]> l = new LinkedList<>();//LinkedList
    ArrayList<String[]> popularNameArray = new ArrayList<>();
    LinkedList<String[]> popularNameLinkedList = new LinkedList<>();
    int numberOfMaxiumValues = 0;
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
        return true;
    }

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

    private int findMAXIndex() throws Exception {
        int maxValue = 0;
        int maxIndex = 0;
        if (ListType().equals("ArrayList")){
            for (int i = 0;i<a.size-1;i++){
                if (Integer.parseInt(a.get(i)[4])>maxValue){
                    maxValue = Integer.parseInt(a.get(i)[4]);
                    popularNameArray.add(a.get(i));
                    maxIndex = i;
                }
                else if (Integer.parseInt(a.get(i)[4])== maxValue) {
                   this.numberOfMaxiumValues+=1;
                    popularNameArray.add(a.get(i));
                }
            }

        }
        else if (ListType().equals("LinkedList")){
            for (int i = 0;i<l.size()-1;i++){
                if (Integer.parseInt(l.get(i)[4])>maxValue){
                    maxValue = Integer.parseInt(l.get(i)[4]);
                    maxIndex = i;
                }
            }
            for (int i = 0;i<l.size()-1;i++){
                if (Integer.parseInt(l.get(i)[4])==maxValue&&i!=maxIndex)
                    popularNameLinkedList.add(l.get(i));
            }
        }

        //return the index of max value
        return maxIndex;
    }


    public void menu() throws Exception {

        while(true){

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("State of birth (two-letter state code):");
            String state = reader.readLine();
            //check state input until valid
            while (!validState(state)){
                System.out.println("Invalid State Abbreviation");
                System.out.println("State of birth (two-letter state code):");
                state = reader.readLine();
            }

            System.out.println("Gender (M/F):");
            String gender = reader.readLine();
            //check gender input until valid
            while (!validGender(gender)){
                System.out.println("Invalid Gender Input(M/F)");
                System.out.println("Gender (M/F):");
                gender = reader.readLine();
            }

            System.out.println("Name of the person (or EXIT to quit):");
            String name = reader.readLine();
            //if name is exit, break out from while loop
            if (name.equals("Exit"))
                break;
            //if not exit
            else{
                //match state gender and name and put into arraylist or linkedlist
                read(state, gender, name);
                //if no such combination, prompt error
                if (!validCombination())
                    System.out.println("Did not find state,gender,name combination. Try again");
                else {
                    //if there is valid match
                    if (ListType().equals("LinkedList")) {
                        if (numberOfMaxiumValues>0){
                            System.out.println(popularNameLinkedList.get(0)[3] + "," + "born in " +
                                    popularNameLinkedList.get(0)[0] +
                                    " is most likely around " +
                                    (2021 - Integer.parseInt(popularNameLinkedList.get(0)[2])) + " to"+
                                    (2021-Integer.parseInt(popularNameLinkedList.get(numberOfMaxiumValues-1)[2]))
                                    +" years old.");
                        }
                        //put in linkedlist
                        String[] temp = l.get(findMAXIndex());

                        //reset
                        this.l = new LinkedList<>();
                        System.out.println(temp[3] + "," + "born in " + temp[0] + " is most likely around " +
                                (2021 - Integer.parseInt(temp[2])) + " years old.");
                    } else if (ListType().equals("ArrayList")) {

                        if (numberOfMaxiumValues>0){
                            System.out.println(popularNameArray.get(0)[3] + "," + "born in " +
                                    popularNameArray.get(0)[0] +
                                    " is most likely around " +
                                    (2021 - Integer.parseInt(popularNameArray.get(0)[2])) + " to"+
                                    (2021- Integer.parseInt(popularNameArray.get(numberOfMaxiumValues-1)[2]))
                                    +" years old.");
                        }
                        //put in arraylist
                        String[] temp = a.get(findMAXIndex());
                        //reset
                        this.a = new ArrayList<>();
                        System.out.println(temp[3] + "," + "born in " + temp[0] + " is most likely around " +
                                    (2021 - Integer.parseInt(temp[2])) + " years old.");
                    }
                }
            }
        }



    }
    public static void main(String[] args) throws Exception {
        predictor d = new predictor();
        d.menu();

    }
}

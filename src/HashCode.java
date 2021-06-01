import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


class Node
    {
        String acct_number;
        Node next; // Reference to next node
        String lastName, firstName;
        int month;
        int day;
        int year;  // 4 digits
        float annual_salary;
        String dept_code;  //    Note: may be entered in either upper or lower case (two characters)
        String phone_number; //example 850-345-9999


        //constructor
        public Node(String id, String last_Name, String first_Name, int Month, int Day, int Year,
                    float annual_Salary,String dept_Code, String phone_Number)
        {
            acct_number = id;
            this.next=null;
            lastName = last_Name;
            firstName = first_Name;
            month = Month;
            day= Day;
            year= Year;
            dept_code=dept_Code;
            annual_salary= annual_Salary;
            phone_number=phone_Number;
        }
    }

//Hash table class
class HashTable {
    //Current capacity of array
    private int currentSize, HashTableSize;

    //array used to store array of chains
    private Node[] keys;
    private Node[] vals;

    //Constructor
    public HashTable(int arraySize){
        currentSize=0;
        HashTableSize = arraySize;

        keys= new Node[HashTableSize];
        vals= new Node[HashTableSize];

    }


    /* Function to insert a key value pair */
    void insert(String key, String last_Name, String first_Name, int Month, int Day, int Year,
                       float annual_Salary,String dept_Code, String phone_Number) {
        int check =0;
        int hash = indexToKey(key);
        int temp = hash;
        do {
            if (keys[temp] == null) {
                keys[temp] = new Node(key, last_Name, first_Name, Month, Day, Year, annual_Salary, dept_Code, phone_Number);
                vals[temp] = new Node(key, last_Name, first_Name, Month, Day, Year, annual_Salary, dept_Code, phone_Number);
                //if collisions happens add "*" to the key indicating it
                if(check!=0)
                    keys[temp].acct_number =checkKey(keys[temp].acct_number)+"*";
                currentSize++;
                return;
            }
            if (keys[temp].acct_number == key) {
                vals[temp] = new Node(key, last_Name, first_Name, Month, Day, Year, annual_Salary, dept_Code, phone_Number);
                return;
            }

            temp = (temp + 1) % HashTableSize; //if slot hash(x) % HashtableSize is full, then try hash(x)+1 % Hashtablesize ...
            check++;
        }
        while (temp != hash);
    }

    //Function find index for a key
    private int indexToKey(String key){
        //give an index value for each given data
        //index is the remainder of the number mod hash table size
        int index = Integer.parseInt(checkKey(key)) % HashTableSize;
        if(index<0)
            index*=-1;

        return index;
    }

    //Check valid key no "*"
    private static String checkKey(String key) {

        String temp ="";
        // Loop looking for character "c" in phone number
        for(Character c: key.toCharArray()){
            //if character is a number add to temp variable
            if(Character.isDigit(c)){
                temp +=c;
            }
        }
        return temp;
    }
    //Sort the data
    public static void bubbleSort(Node[] arr){

        for (int i = 0; i < arr.length-1; i++)
            for (int j = i+1; j < arr.length; j++)
                if(arr[j]!=null && arr[i]!=null)
                    if (arr[j].acct_number.compareTo(arr[i].acct_number) <0)
                    {
                        Node temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
    }

    /* Function to print hash table */
    public void printHashTable() {
        WriteFileToTopic();
        //create an clone array to sort the hashtable
        Node[] array = new Node[HashTableSize];
        for (int i = 0; i < HashTableSize; i++) {
            array[i]=keys[i];
        }
        //Bubblesort the hash table
        bubbleSort(array);

        for (int i = 0; i < HashTableSize; i++) {
            Node entry = array[i];

            if(entry!=null)
                System.out.print("\nIndex "+ (i) +" : ");

            while (entry != null) {

               System.out.print(entry.acct_number+" " +entry.lastName + " ");
               WriteToFile(String.valueOf(entry.acct_number),entry.lastName,entry.firstName,entry.month,entry.day,entry.year,
                            entry.annual_salary,  entry.dept_code,entry.phone_number);
                entry = entry.next;
            }
        }
    }

    //Function writes the titles and subtitles in the output file
    private static void WriteFileToTopic() {
        //format the date and time in the report
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //check the date and time for the report
        LocalDateTime now = LocalDateTime.now();
        //format the currency

        try {
            File file = null;
            // Get the file
            file = new File("report_out.rpt");

            if (file.exists()) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw"); //Open the file for reading and writing
                raf.setLength(0); //set the length of the character sequence equal to 0
            }
            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);

            //Create new format for the report
            Formatter formatter = new Formatter();
            //Convert all the data to string, including the current date and time
            printer.append(String.valueOf(formatter.format("%50s %20s %15s", "Employee Report", " ", dtf.format(now))));
            printer.append('\n');
            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%50s","*************")));
            printer.append('\n');

            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %15s","Acct#", "Last", "First","Date of","Annual","Department","Age","Phone")));
            printer.append('\n');

            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %15s","     ", "Name", "Initial","Birth","Salary","Code","   ","Number")));
            printer.append('\n');
            printer.append('\n');
            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
    //Create the output file
    void WriteToFile(String acct_number,String lastName, String firstName, int month, int day, int year,
                     float annual_salary,String dept_code, String phone_number) {
        //format the currency
        DecimalFormat moneyFormat = new DecimalFormat("$0,000.00");
        try {

            //  Print the records ordered by last name into the output file
            File file = null;
            // Get the file
            file = new File("report_out.rpt");

            // Create new format for the report
            //Create new format for the report
            Formatter formatter = new Formatter();
            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);
            //Print all the objects from the node into the output file
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %20s", acct_number, lastName, firstName.charAt(0)+".", getMonth(month,day,year),
                    moneyFormat.format(annual_salary), dept_code,checkAge(nearestYear(year,month,day)), phone_number)));
            printer.append('\n');

            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
    //check if is a valid age
    public static int checkAge(int year){
        // if the employer is still working, the end date is today's date
        LocalDate currentdate = LocalDate.now();
        //get today`s year, month and day
        int currentYear = currentdate.getYear();
        //return 0 if is an invalid age(e.g. future date of birth)
        if(year>currentYear) return 0;

        return currentYear -year;
    }

    // service rounded to the nearest year
    private int nearestYear(int year, int month,int day) {

        // if the employer is still working, the end date is today's date
        LocalDate currentdate = LocalDate.now();
        //get today`s year, month and day
        int currentMonth = currentdate.getMonthValue();
        int currentDay = currentdate.getDayOfMonth();


        //if the employer retired before Jun, the nearest year is decreased by one
        if(Math.abs(currentMonth -month)>6) year =year+1;

        else if(Math.abs(currentMonth-month)==6)
            if(currentDay-day<0)
                year= year+1;

        return year;

    }
    //Format the date to a short date notation
    public static String getMonth(int month, int day, int year) {
        switch (month){
            case 1:
                return "Jan."+day+","+year;
            case 2:
                return "Feb."+day+","+year;
            case 3:
                return "Mar."+day+","+year;
            case 4:
                return "Apr."+day+","+year;
            case 5:
                return "May."+day+","+year;
            case 6:
                return "Jun."+day+","+year;
            case 7:
                return "Jul."+day+","+year;
            case 8:
                return "Aug."+day+","+year;
            case 9:
                return "Set."+day+","+year;
            case 10:
                return "Oct."+day+","+year;
            case 11:
                return "Nov."+day+","+year;
            case 12:
                return "Dec."+day+","+year;
        }
        return "";
    }

}



public class HashCode {

    static String MasterFileInput = "emp5_hash.txt";
    private static int ArraySize=1000;

    //Check if the lastName or firstName contains digit
    private static boolean checkName(String name) {
        String temp ="";
        // Loop looking for character "c" in the word
        for (Character c :name.toCharArray()) {
            //if character is a letter return the letter, if not temp contains digit
            if (Character.isLetter(c))
                temp +=c;
            else if(Character.isDigit(c))
                temp+=c;
        }
        //if temp contains digit return false
        if(temp.matches(".*\\d.*"))
            return false;
            //if temp is just letter return true
        else
            return true;
    }
    //Check how many days has each month
    private static int checkDaysMonth(int month, boolean leap) {
        switch (month){
            //Jan, Mar, May, Jul, Aug, Oct, Dec - 31 days
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            //Fev. Common year - 28 days , Leap year 29 days
            case 2:
                if(leap == true)
                    return 29;
                return 28;
            //Apr, Jun, Set, Nov - 30 days
            case 4: case 6: case 9: case 11:
                return 30;
        }
        return 0;
    }
    // check for leap year
    private static boolean checkLeapYear(int year) {
        // year to be checked
        boolean leap = false;
        // if the year is divided by 4
        if (year % 4 == 0) {
            // if the year is century
            if (year % 100 == 0) {
                // year that is divisible by 100 is a leap year only if it is also divisible by 400
                //if is %400 is leap year
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }
            // if the year is not century
            else
                leap = true;
        }
        else
            leap = false;

        return leap;
    }

    //Check valid phoneNumber
    private static boolean checkPhoneNumber(String phone_Number) {

        String temp ="";
        // Loop looking for character "c" in phone number
        for(Character c: phone_Number.toCharArray()){
            //if character is a number add to temp variable
            if(Character.isDigit(c)){
                temp +=c;
            }
        }
        //if temp variable has 9 digits, returns true
        if(temp.length()==10) {
            return true;}
        return false;
    }

    public static void main(String[] args) throws IOException {

        /* Make object of HashTable */
        HashTable hashtableArray = new HashTable(ArraySize);

        // Open and read the Input file
        File file = new File(MasterFileInput);
        // Scanner all the file
        Scanner scannerFile = new Scanner(file);

        //While until have lines in the file
        while (scannerFile.hasNext()) {

            //Store all the data line by line into variable to populate the node
            //Each variable will be an object in the node
            String acct_number = scannerFile.next();
            String lastName = scannerFile.next();
            String FirstName = scannerFile.next();
            int month = Integer.parseInt(scannerFile.next());
            int day = Integer.parseInt(scannerFile.next());
            int year = Integer.parseInt(scannerFile.next());
            float annual_salary = Float.parseFloat(scannerFile.next());
            String dept_codes = scannerFile.next().toUpperCase();
            String phone_number = scannerFile.next();

            //Check the errors name, invalid date, annual salary = 0, unknown department code
            //if does not contains errors
            if (checkName(lastName) != false && checkName(FirstName) != false && month != 0 && day != 0 && year !=0 && annual_salary != 0
                    && month <= 12 && day <= checkDaysMonth(month, checkLeapYear(year)) && dept_codes != "" && checkPhoneNumber(phone_number)!=false){
                //Record the data into the tree
                hashtableArray.insert(acct_number,lastName, FirstName, month, day, year, annual_salary,dept_codes, phone_number);

            }
        }

        hashtableArray.printHashTable();
    }
}

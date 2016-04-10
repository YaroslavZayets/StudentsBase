import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.io.Serializable;

public class Group implements Serializable{
    private Student[] students = new Student[10];
    int n = 0;
    private static final long serialVersionUID = 1L;


    public  void addStudents(Student stud)
    {

        try {
            students[n++]=stud;
            if (n>10){
                throw new MyException();
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException r){
            System.out.println("Не больше 10 студентов в группе! ");
        }
    }

    public void searchStudent()  {
        String surname;
        surname = String.valueOf(JOptionPane.showInputDialog("Введите фамилию"));

        boolean isFound = false;
        for (int i = 0; i<students.length; i++){
            if (students[i]!=null){
                if (students[i].getSurname().equals(surname)){
                    isFound = true;
                    JOptionPane.showMessageDialog(null, students[i].toString());
                    break;
                }
            }
        }
        if (!isFound) JOptionPane.showMessageDialog(null, " Не найдено такого студента ");
    }


    public void showInfo(){
        for (int i = 0; i<students.length; i++){
            if (students[i]!=null){
                System.out.println(students[i].toString());
            } else
                break;
        }
    }



    public void addNow () {
        int count = Integer.valueOf(JOptionPane.showInputDialog("Введите количество студентов в группе "));

        for (int i = 0; i<count; i++){
            try {
                Student ST = new Student(String.valueOf(JOptionPane.showInputDialog("Введите имя " + (i+1) + "ого студента")),
                        String.valueOf(JOptionPane.showInputDialog("Введите фамилию " + (i+1) + "ого студента")),
                        Integer.valueOf(JOptionPane.showInputDialog("Введите возвраст "+ (i+1) + "ого студента")),
                        Double.valueOf(JOptionPane.showInputDialog("Введите оценку "+ (i+1) + "ого студента")));
                students[n++]=ST;
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null," Не верный формат! ");
            }catch (ArrayIndexOutOfBoundsException r) {
                System.out.println("Не больше 10 студентов в группе! ");
            }
        }

    }

    public void sortBySurname(){

        try {
            Arrays.sort(students);

        } catch (NullPointerException e){
            System.out.println(" Неполная группа студентов! ");
        } finally {
            for (int i = 0; i<students.length; i++) {
                if (students[i]!=null){
                    System.out.println(students[i].toString());
                }
            }
        }
    }

    public void saveDoc (){
        try (BufferedWriter f = new BufferedWriter(new FileWriter("student.txt")))
        {
            for (int i = 0; i <students.length; i++){
                if (students[i]!=null){
                    f.write(students[i].toString());
                    f.newLine();
                }
            }
            System.out.println(" Студенты записаны ");
        } catch (IOException e){
            System.out.println("Error");
        }
    }

    public void readDoc(){
        try (BufferedReader f  = new BufferedReader(new FileReader("newStud.txt")))
        {
            String str = "";
            String[] arr = new String[3];
            for (;(str = f.readLine())!=null;){
                arr = str.split(" ");
                int age = Integer.parseInt(arr[2]);
                double rating = Double.parseDouble(arr[3]);
                Student st = new Student(arr[0],arr[1],age,rating);
                addStudents(st);
            }
            System.out.println(" Студенты считаны ");

        } catch (IOException e){
            System.out.println("Error");
        }

    }

    public void saveBase (){
        try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream("base"))){
            OOS.writeObject(students);
            System.out.println(" Студенты записанны в базу ");
        }
        catch (IOException e){
            System.out.println(" Error save group ");
        }
    }

    public void readBase (){
        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream("base"))){
            students = (Student[]) OIS.readObject();
            System.out.println(" Студенты считаны с базы ");
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error load stud! ");
        }

    }



}

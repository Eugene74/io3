/*
3. Усовершенствуйте класс описывающий группу студентов (D:\Java\OtherCurs2\Exceptions\src  - подключил как .jar)
добавив возможность сохранения списка студентов в файл.
4. Реализовать обратный процесс — т.е. считать данные о
студентах из файла.
*/

import people.Student;
import utils.StudentsGroup;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentsGroup group = new StudentsGroup(10);
        group.add(new Student("Kolya", "Saychuk", 1975, 5, 15));
        group.add(new Student("Gena", "Saychuk", 1976, 7, 16));
        group.add(new Student("Seva", "Evgienko", 1986, 3, 11));
        group.add(new Student("Vasya", "Pupko", 1988, 4, 12));
        group.add(new Student("Tolya", "Avko", 1989, 5, 13));
        group.add(new Student("Olya", "Blavko", 1991, 6, 14));
        System.out.println("-----------Write to file list of student---------------");

        File listOfStudent = new File("listOfStudent.txt");
        if (listOfStudent.exists()) {
            listOfStudent.delete(); // почистить файл,потому что накапливается там список студентов
            listOfStudent = new File("listOfStudent.txt");
        }
        writeStudentGroupToFile(group, listOfStudent);
        System.out.println("-----------Write to file list of student-----DONE!!!----------");
        System.out.println("-----------Create Group of Student from list of student from file----------");
        StudentsGroup group1 = new StudentsGroup(10);
        group1.add(new Student("Asya", "Lavko", 1993, 11, 4));
        group1 = createStudentGroupFromFile(listOfStudent, group1);
        for (Student s : group1.getStudents()) {
            if (s != null) {
                System.out.println(s);
            } else {
                System.out.println("vacant ");
            }
        }
    }

    private static void writeStudentGroupToFile(StudentsGroup group, File listOfStudent) {
        List<Student> list = Arrays.asList(group.getStudents());
        try (FileOutputStream fos = new FileOutputStream(listOfStudent, true)) {
            String text;
            for (Student student : list) {
                try {
                    text = student.toCSVString();
                    byte[] buffer = text.getBytes();
                    fos.write(buffer, 0, buffer.length);
                    buffer = "\r\n".getBytes();
                    fos.write(buffer);
                } catch (NullPointerException e) {
                    //do nothing
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static StudentsGroup createStudentGroupFromFile(File listOfStudent, StudentsGroup studentsGroup) {
        try (BufferedReader fr = new BufferedReader(new FileReader(listOfStudent))) {
            String csvStudent = "";
            while ((csvStudent = fr.readLine()) != null) {
                Student student = new Student().fromCSVString(csvStudent);
                studentsGroup.add(student);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return studentsGroup;
    }
}

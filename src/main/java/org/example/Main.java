package org.example;

//        Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
//        Фамилия Имя Отчество дата рождения номер телефона пол
//        Форматы данных:
//        фамилия, имя, отчество - строки
//
//        дата_рождения - строка формата dd.mm.yyyy
//
//        номер_телефона - целое беззнаковое число без форматирования
//
//        пол - символ латиницей f или m.
//
//        Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
//        вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//
//        Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
//        Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
//        Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано,
//        пользователю выведено сообщение с информацией, что именно неверно.
//
//        Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
//        в него в одну строку должны записаться полученные данные, вида
//
//        <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//
//        Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//
//        Не забудьте закрыть соединение с файлом.
//
//        При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
//        пользователь должен увидеть стектрейс ошибки.

// Черленяк Анна Леонидовна 22.11.1987 89080000000 f

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int dataSize = 6;
        String[] data = new String[dataSize];
        String[] gendarOptions  = {"f", "m"};
        String surname = data[0];

        boolean temp = true;
        while (temp) {
            String text = inputText("Введите свои данные по шаблону: " +
                        "Фамилия Имя Отчество дата_рождения(dd.mm.yyyy) номер_телефона пол(f или m)", dataSize);
            if (text.equals("1")) {
                System.out.println("Вы ввели лишние данные.");
                continue;
            } else if (text.equals("2")) {
                System.out.println("Вы ввели недостаточно данных.");
                continue;
            } else {
                data = text.split(" ");
                surname = data[0];
                String firstName = data[1];
                String patronymic = data[2];
                String dateBirth = data[3];
                String numberPhone = data[4];
                String gender = data[5];

                try {
                    parameterIsNotNull(surname);
                    parameterIsNotNull(firstName);
                    parameterIsNotNull(patronymic);
                    isDate(dateBirth);
                    isDigit(numberPhone);
                    matchingParameter(gender, gendarOptions);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    continue;
                } catch (DateFormatException e) {
                    System.out.println(e.getMessage());
                    continue;
                } catch (StringOfOnlyDigitsException e) {
                    System.out.println("номер_телефона - " + e.getMessage());
                    continue;
                } catch (ParameterMeetsRequirementsException e) {
                    System.out.println("пол - " + e.getMessage());
                    continue;
                }
            }

            temp = false;
        }
        sc.close();
        String nameFile = surname + ".txt";
        String absFilePath = "C:\\Users\\Admin\\IdeaProjects\\Exceptoins_DZ_3\\src\\main\\resources\\" + nameFile;
        try {
            File f = new File(absFilePath);
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String text = stringForWriter(data);
            bw.write(text);
            bw.append('\n');
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void parameterIsNotNull(String datum) throws NullPointerException {
        if (datum.length() == 0){
            throw new NullPointerException("Ввели пустой параметр");
        }
    }


    private static String inputText(String s, int size) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(s);
        String text = scanner.nextLine();
        String[] temp = text.split(" ");

        if (temp.length > size) {
            return "1";
        }
        if (temp.length < size) {
            return "2";
        }
        return text;
    }

    private static void isDate(String myDate) throws DateFormatException {
        if (myDate.matches("^([0-2][0-9]||3[0-1]).(0[0-9]||1[0-2]).([0-9][0-9][0-9][0-9])$")){
        } else throw new DateFormatException();
    }

    private static void isDigit(String text) throws StringOfOnlyDigitsException {
        if  (text.matches("\\d+")) {
        } else throw new StringOfOnlyDigitsException();
    }

    private static void matchingParameter(String text, String[] regex) throws ParameterMeetsRequirementsException{
        int count = 0;
        for (int i = 0; i < regex.length; i++) {
            if (text.equals(regex[i])) {
                count ++;
            }
        }
        if (count == 0) {
            throw new ParameterMeetsRequirementsException();
        }
    }

    private static String stringForWriter(String[] data) {
        String resuit = "";
        for (int i = 0; i < data.length; i++) {
            resuit += "<" + data[i] + ">";
        }
        return resuit;
    }

}
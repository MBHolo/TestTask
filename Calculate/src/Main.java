
import java.util.Scanner;

public class Main {

    public static String arOperation;
    public static String result;

    public static void main(String[] args) throws MathOperationException, NumberTypeException{

        Scanner inString = new Scanner(System.in);

        arOperation = inString.nextLine();

        result = calc(arOperation);
        System.out.println(result);

        inString.close();

    }

    public static String calc(String input) throws MathOperationException, NumberTypeException{

        input = checkedOperation(input); //Преобразование строки
        String [] el = input.split(" ");

        boolean romCheck = checkTypeRom(input);

        if(romCheck){
            Rom numberOne = Rom.valueOf(el[0]);
            el[0] = numberOne.notRomType();
            Rom numberTwo = Rom.valueOf(el[2]);
            el[2] = numberTwo.notRomType();
        }

        int resultInt = 0;
        switch (el[1]) {
            case "+":
                resultInt = Integer.parseInt(el[0]) + Integer.parseInt(el[2]);
                break;
            case "-":
                if(romCheck && Integer.parseInt(el[0]) <= Integer.parseInt(el[2])){ //Проверка на отрицательность римских чисел
                    throw new MathOperationException("throws Exception //т.к. в римской системе нет отрицательных чисел и нет цифры ноль");
                }
                else{
                    resultInt = Integer.parseInt(el[0]) - Integer.parseInt(el[2]);
                }
                break;
            case "*":
                resultInt = Integer.parseInt(el[0]) * Integer.parseInt(el[2]);
                break;
            case "/":
                resultInt = Integer.parseInt(el[0]) / Integer.parseInt(el[2]);
                break;
        }

        if(romCheck){
            result = convertRom(resultInt); //Конвертирование арабских чисел в римские предстовления
        }
        else{
            result = Integer.toString(resultInt);
        }

        return result;
    }

    public static String checkedOperation(String input) throws MathOperationException{

        int k = 0;
        boolean space = false;
        String newInput = "";
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/'){
                k++;
                if(input.charAt(i - 1) != ' ' && input.charAt(i + 1) != ' '){ //Привод к формату с пробелалми для разделения строки
                    space = true;
                    newInput = newInput + ' ' + input.charAt(i) + ' ';
                }
                else{
                    if(input.charAt(i - 1) != ' '){ //Привод к формату с пробелалми для разделения строки
                        space = true;
                        newInput = newInput + ' ' + input.charAt(i);
                    }
                    if(input.charAt(i + 1) != ' '){ //Привод к формату с пробелалми для разделения строки
                        space = true;
                        newInput = newInput + input.charAt(i) + ' ';
                    }
                }
            }
            if(!space){
                newInput += input.charAt(i);
            }
            space = false;
        }

        if(k > 1){
            throw new MathOperationException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if(k == 0){
            throw new MathOperationException("throws Exception //т.к. строка не является математической операцией");
        }

        return newInput;
    }

    public static boolean checkTypeRom(String input) throws NumberTypeException{

        boolean check = false;
        boolean arTypeBumber = false;

        for(int i = 0; i<input.length(); i++){
            if(input.charAt(i) == 'I' || input.charAt(i) == 'V' || input.charAt(i) == 'X'){
                check = true;
            }
            if(input.charAt(i) == '0' || input.charAt(i) == '1' || input.charAt(i) == '2' || input.charAt(i) == '3' || input.charAt(i) == '4'
                    || input.charAt(i) == '5' || input.charAt(i) == '6' || input.charAt(i) == '7' || input.charAt(i) == '8' || input.charAt(i) == '9'){
                arTypeBumber = true;
            }
        }

        if(check == arTypeBumber){
            throw new NumberTypeException("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        return check;
    }

    public static String convertRom(int input){ //Преобразование арабских в римские

        String romNumber = "";

        int c1 = input / 100;
        romNumber += C(c1, input);
        int c2 = input % 100;

        int l1 = c2 / 50;
        romNumber += L(l1, c2);
        int l2 = c2 % 50;

        int x1 = l2 / 10;
        romNumber += X(x1);
        int x2 = l2 % 10;

        int v1 = x2 / 5;
        romNumber += V(v1, x2);
        int v2 = x2 % 5;

        int i1 = v2 / 1;
        romNumber += I(i1, v2);

        return romNumber;
    }

    public static String C(int c1, int input){

        String romNumber = "";

        int i = 0;

        if(input >= 90 && input > 100){
            romNumber += "XC";
        }
        else{
            while(c1 > i){
                romNumber += "C";
                i++;
            }
        }

        return romNumber;
    }

    public static String L(int l1, int c2){
        String romNumber = "";

        int i = 0;

        if(c2 >= 40 && c2 < 50){
            romNumber += "XL";
        }
        else{
            while(l1 > i){
                romNumber += "L";
                i++;
            }
        }

        return romNumber;
    }

    public static String X(int x1){
        String romNumber = "";

        int i = 0;

        while(x1 > i){
            romNumber += "X";
            i++;
        }

        return romNumber;
    }

    public static String V(int v1, int x2){
        String romNumber = "";

        if(x2 == 9){
            romNumber += "IX";
        }
        else{
            if(v1 == 1){
                romNumber += "V";
            }
        }

        return romNumber;
    }

    public static String I(int i1, int v2){
        String romNumber = "";

        int i = 0;

        if(v2 == 4){
            romNumber += "IV";
        }
        else{
            while(i1 > i){
                romNumber += "I";
                i++;
            }
        }

        return romNumber;
    }
}
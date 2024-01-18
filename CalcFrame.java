/**
*  Tasks:
*  Limit amount of numbers shown on screen to only 10.
*  Limit so the max/min number is 1.0E9/-1.0E9.
*  Need to adjust square and squareRoot to coninue calculation if its a second term.
*  Have an all clear button and have it function like the clear button.
*  Change clear button to have it just clear the text but keep operation and num1.(optional: replace delete button).
*  Add hotkeys so user can input numbers and certain operations using keyboard.
*  Add icons/images to make it appealing.
*  Add more functions(pi, roots, exponents, sin, cos, tan, etc...).
*  Add history so user can see previous calculations.
*  Try to reduce the amount of lines used.
*
*  Known Bugs:
*  Adding to an 11 digit number causes it to be undefined.
*  hitting equals multiple times does not conitnue calculation using same number and operator.
*/

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class CalcFrame implements ActionListener{
      JFrame frame = new JFrame();
      JPanel panel = new JPanel();
      JTextField text = new JTextField("0");
      JButton[] number = new JButton[10];
      JButton[] function = new JButton[11];
      JButton add, subtract, multiply, divide, decimal, equal, delete, clear, negative, squareRoot, square;
      Border loweredBevel = BorderFactory.createLoweredBevelBorder();
      Font font = new Font("Ariel Black", Font.BOLD, 75);
      Font font2 = new Font("Ariel Black", Font.BOLD, 40);
      double num1 = 0, num2 = 0, result = 0;
      char operator;
      boolean zero = true;
      boolean operatorInUse = false;
      boolean saveResult = false;
      int intResult, intResultLength, digits, exponent, wholeNum, decToInt;
      double decResult;
      
   public CalcFrame(){
      //initialize function buttons
      add = new JButton("+");
      subtract = new JButton("-");
      multiply = new JButton("*");
      divide = new JButton("/");
      decimal = new JButton(".");
      equal = new JButton("=");
      delete = new JButton("Delete");
      clear = new JButton("Clear");
      negative = new JButton("(-)");
      squareRoot = new JButton("x^(1/2)");
      square = new JButton("x^2");
      
      //adds function buttons to an array
      function[0] = add;
      function[1] = subtract;
      function[2] = multiply;
      function[3] = divide;
      function[4] = decimal;
      function[5] = equal;
      function[6] = delete;
      function[7] = clear;
      function[8] = negative;
      function[9] = squareRoot;
      function[10] = square;
      
      //JFrame frame attributes
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(545,600);
      frame.setTitle("Shane Calculator");
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(Color.BLUE);
      
      //JPanel panel attributes
      panel.setBounds(10,120,510,430);
      panel.setBackground(Color.GRAY);
      panel.setBorder(loweredBevel);
      panel.setLayout(null);
      
      //JTextField text attributes
      text.setBounds(10,10,510,100);
      text.setFont(font);
      text.setEditable(false);
      text.setBorder(loweredBevel);
      text.setBackground(Color.YELLOW);
      
      //button attributes for function[]
      for(int i = 0; i < 11; i++){
         function[i].addActionListener(this);
         function[i].setFont(font2);
         function[i].setFocusable(false);
         panel.add(function[i]);
      }
      
      //button attributes for number[]
      for(int i = 0; i < 10; i++){
         number[i] = new JButton(String.valueOf(i));
         number[i].addActionListener(this);
         number[i].setFont(font2);
         number[i].setFocusable(false);
         panel.add(number[i]);
      }
      
      //sets the bounds for each button
      decimal.setBounds(210,360,90,60);
      number[0].setBounds(310,360,90,60);
      number[1].setBounds(210,290,90,60);
      number[2].setBounds(310,290,90,60);
      number[3].setBounds(410,290,90,60);
      number[4].setBounds(210,220,90,60);
      number[5].setBounds(310,220,90,60);
      number[6].setBounds(410,220,90,60);
      number[7].setBounds(210,150,90,60);
      number[8].setBounds(310,150,90,60);
      number[9].setBounds(410,150,90,60);
      add.setBounds(410,80,90,60);
      subtract.setBounds(210,10,90,60);
      multiply.setBounds(310,10,90,60);
      divide.setBounds(410,10,90,60);    
      equal.setBounds(10,360,190,60);
      delete.setBounds(10,290,190,60);
      clear.setBounds(10,220,190,60);
      negative.setBounds(410,360,90,60);
      squareRoot.setBounds(210,80,190,60);
      square.setBounds(10,80,190,60);
      
      //adds panel and text to frame
      frame.add(panel);
      frame.add(text);
      
      //sets frame visibiltiy to true
      frame.setVisible(true);
   }
   public void actionPerformed(ActionEvent e){
      //inputs the number of corresponding button to textfield
      for(int i = 0; i < 10; i++){
         if(e.getSource() == number[i]){
            switch(i){
               case 0:
                  if(zero){                 
                     text.setText("0");
                     zero = true;
                  }
                  else{
                     text.setText(text.getText().concat(String.valueOf(i)));
                  }
                  break;
               default:
                  if(zero){
                     text.setText("");
                     zero = false;
                  }
                  text.setText(text.getText().concat(String.valueOf(i)));
                  break;
            }
         } 
      }
      
      //adds a decimal point
      if(e.getSource() == decimal){
         if(zero){
            text.setText("0");
            zero = false;
         }
         text.setText(text.getText().concat("."));
      }
      
      //clears textfield and restart
      if(e.getSource() == clear){
         text.setText("0");
         zero = true;
         saveResult = false;
      }
      
      //deletes last number input but does not restart
      if(e.getSource() == delete){
         String string = text.getText();
         if(string.length() == 1){
            text.setText("0");
            zero = true;
         }
         else{
            text.setText("");
            for(int i = 0; i < string.length()-1; i++){
               text.setText(text.getText() + string.charAt(i));
            }
         }
      }
      
      //calculates the numbers inputed by the user
      try{
         if(e.getSource() == negative){
            text.setText(String.valueOf(Double.parseDouble(text.getText()) * -1));
         }
         if(e.getSource() == add){ 
            calculate();            
            operator = '+';
         }
         if(e.getSource() == subtract){
            calculate();
            operator = '-';
         }
         if(e.getSource() == multiply){
            calculate();
            operator = '*';
         }
         if(e.getSource() == divide){
            calculate();
            operator = '/';
         }
         if(e.getSource() == square){
            num1 = Double.parseDouble(text.getText());
            result = Math.pow(num1, 2);
            printResult();
            zero = true;
            saveResult = false;
         }
         
         if(e.getSource() == squareRoot){
            num1 = Double.parseDouble(text.getText());
            result = Math.sqrt(num1);
            printResult();
            zero = true;
            saveResult = false;
         }
         if(e.getSource() == equal){
            calculate();
            zero = true;
            saveResult = false;
         }
      }
      //catches NumberFormatException and ends the calculations.
      //known possibilities: when user inputs 2 decimals in a line.
      catch(NumberFormatException nfe){
         text.setText("Undefined");
         zero = true;
         saveResult = false;
      }
   }
   
   /*
   *Method to print result onto the screen.
   *Limits the amount of digits so that only 10 digits are shown on screen.
   *Rounds the result if total digits is greater than 10.
   *no parameters
   *return void and displays result onto the screen.
   */
   public void printResult(){
      intResult = (int)Math.floor(result);
      decResult = result - intResult;
      intResultLength = String.valueOf(intResult).length();
      digits = String.valueOf(result).length() - 1;
      
      //executes if total digits in result is more than 10      
      if(digits > 11){
         exponent = 11 - intResultLength;
         decToInt = (int)(decResult * Math.pow(10,exponent));
         result = Double.parseDouble(intResult + "." + String.valueOf(decToInt));
         text.setText(String.valueOf(result));
      }
      else{
         text.setText(String.valueOf(result));
      }
      
      wholeNum = (int)result;
      
      if(wholeNum == result){
        text.setText(String.valueOf(wholeNum)); 
      }
   }
   
   /*
   *method to calculate 2 numbers using the operation chosen by user
   *no parameters
   *return void
   */
   public void calculate(){
      if(saveResult){
         num2 = Double.parseDouble(text.getText());
         switch(operator){
            case '+':
               result = num1 + num2;
               printResult();
               break;
            case '-':
               result = num1 - num2;
               printResult();
               break;
            case '*':
               result = num1 * num2;
               printResult();
               break;
            case '/':
               if(num2 == 0){
                  text.setText("Undefined");
                  zero = true;
                  saveResult = false;
               }
               else{
                  result = num1 / num2;
                  printResult();
               }
               break;
         }
      }
      num1 = Double.parseDouble(text.getText()); 
      zero = true;
      saveResult = true;   
   }
}
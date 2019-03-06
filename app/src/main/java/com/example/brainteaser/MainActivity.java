package com.example.brainteaser;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;




public class MainActivity extends AppCompatActivity {

    Button startButton;

    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int score;
    int numberOfQuestions;

    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    RelativeLayout gameRelativeLayout;



    public void start (View view){

        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);


        score = 0;
        numberOfQuestions = 0;


        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");




        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf((millisUntilFinished)/1000) + "s");

            }

            @Override
            public void onFinish() {

                timerTextView.setText("0s");

                String getFinalScore = Integer.toString(score) + "/" + Integer.toString(numberOfQuestions);

                Intent intent = new Intent(getApplicationContext(), PlayAgain.class);

                //Create the bundle
                Bundle bundle = new Bundle();

                //Add your data to bundle
                bundle.putString("points", getFinalScore);

                //Add the bundle to the intent
                intent.putExtras(bundle);

                startActivity(intent);

                //playAgainButton.setVisibility(View.VISIBLE);

                // resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));



            }
        }.start();

    }



    public void generateQuestion(){

        Random rand = new Random();
        String operators = "+-*/";


        int a = rand.nextInt(10);
        int b = rand.nextInt(10) + 1;
        int c = rand.nextInt(10) + 1 ;

        String expression = "";

        expression += a + " ";
        expression += operators.charAt(rand.nextInt(operators.length())) +" ";
        expression += b +" ";
        expression += operators.charAt(rand.nextInt(operators.length())) + " ";
        expression += c;

        sumTextView.setText(expression);


        answers.clear();

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        int correctAnswer = evaluate(expression);


        for(int i=0; i<4; i++) {

            if (i == locationOfCorrectAnswer) {

                answers.add(correctAnswer);

            } else {

                incorrectAnswer = rand.nextInt(99);

                while (correctAnswer == incorrectAnswer) {

                    incorrectAnswer = rand.nextInt(99);

                }

                answers.add(incorrectAnswer);
            }

        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }




    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }


            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }




    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {

        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }




    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }



    public void chooseAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

            score++;
            resultTextView.setText("Correct");

        } else{

            resultTextView.setText("Wrong");

        }

        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();




    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        timerTextView = findViewById(R.id.timerTextView);
        //playAgainButton = findViewById(R.id.playAgainButton);
        gameRelativeLayout = findViewById(R.id.gameRelativeLayout);
        //gridLayout = findViewById(R.id.gridLayout);


        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        generateQuestion();


    }
}



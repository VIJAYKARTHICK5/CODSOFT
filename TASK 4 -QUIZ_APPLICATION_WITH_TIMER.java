import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.InputMismatchException; // Added import for InputMismatchException

public class QuizApplication {

    private static final int QUESTION_TIME = 15; // Time limit in seconds (adjustable)

    public static void main(String[] args) {
        List<Question> questions = prepareQuestions(); // Replace with your question data
        int score = 0;

        for (Question question : questions) {
            score += displayAndAnswerQuestion(question);
        }

        displayResult(score, questions.size());
    }

    private static List<Question> prepareQuestions() {
        // Replace this with your actual method to prepare questions
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question("What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Rome"}, 2);
        questions.add(question1);
        // Add more questions here
        return questions;
    }

    private static int displayAndAnswerQuestion(Question question) {
        System.out.println(question.getText());
        for (int i = 0; i < question.getOptions().length; i++) {
            System.out.println((i + 1) + ". " + question.getOptions()[i]);
        }

        Scanner scanner = new Scanner(System.in);
        int answer;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                timer.cancel();
            }
        };
        timer.schedule(task, QUESTION_TIME * 1000); // Convert to milliseconds

        try {
            System.out.print("Enter your answer (1-" + question.getOptions().length + "): ");
            answer = scanner.nextInt();
            timer.cancel();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Time's up!");
            answer = 0; // Mark as incorrect if invalid input
            timer.cancel();
        } finally {
            scanner.close();
        }

        if (answer == question.getCorrectAnswerIndex() + 1) {
            System.out.println("Correct!");
            return 1;
        } else {
            System.out.println("Incorrect. The correct answer was " + (question.getCorrectAnswerIndex() + 1));
            return 0;
        }
    }

    private static void displayResult(int score, int totalQuestions) {
        System.out.println("------------------");
        System.out.println("Final Score: " + score + " out of " + totalQuestions);
    }
}

class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String text, String[] options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

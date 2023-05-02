import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class ChatBotController {
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private List<Integer> questionsId = new ArrayList<>();
    private List<Integer> answersId = new ArrayList<>();

    private int questionsSize = 0;
    private int answersSize = 0;
    private int idsSize = 0;

    
    public ChatBotController() {
        questions = readDataFromFile("questions.txt");
        answers = readDataFromFile("answers.txt");
        readQuestionAnswerFile();
        questionsSize = questions.size();
        answersSize = answers.size();
        idsSize = questionsId.size();
    }
    public String findAnswer(String question){
        String answer = "";
        int questionId = findQuestionId(question);
        int answerId = findAnswerIdByQuestionId(questionId);
        answer = answers.get(answerId);
        return answer;
    }
    private int findAnswerIdByQuestionId(int questionId){
        ArrayList<Integer> answs = new ArrayList<>();
        
        for(int i = 0; i < questionsId.size(); i++){
            if(questionsId.get(i) == questionId){
                answs.add(answersId.get(i));
            }
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(answs.size());
        
        return answs.get(randomIndex);
    }
    private List<String> readDataFromFile(String filename){
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while (line != null) {
                data.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return data;
    }
    private void readQuestionAnswerFile(){
        String filename = "rel.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                questionsId.add(Integer.parseInt(parts[0]));
                answersId.add(Integer.parseInt(parts[1]));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int  findQuestionId(String question){
        int questionId = questions.indexOf(question);
        if(questionId == -1){
            questions.add(question);
            questionsId.add(questions.size()-1);
            
            answers.add(askAnswer());
            answersId.add(answers.size()-1);
            return findQuestionId(question);
        }
        return questionId;
    }
    
    public String askAnswer() {
        return JOptionPane.showInputDialog(null, "Todavía no sé responder a estar pregunta\t¿Cómo debería responder?", "Agregar pregunta", 0);
    }

    private void saveData(String filename,List<String> data,int start){

        try (FileWriter writer = new FileWriter(filename,true);){
            for(int i = start; i < data.size(); i++){
                writer.write("\n"+data.get(i));
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    public void saveData(){
        System.out.println("saving...");
        saveData("questions.txt",questions,questionsSize);
        saveData("answers.txt",answers,answersSize);

        List<String> ids = new ArrayList<>();
        for(int i = 0; i < questionsId.size(); i++){
            ids.add(questionsId.get(i)+","+answersId.get(i));
        }
        saveData("rel.txt",ids,idsSize);
    }
    
}
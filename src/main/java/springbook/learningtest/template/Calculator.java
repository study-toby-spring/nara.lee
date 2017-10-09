package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
            String line = null;
            while((line = br.readLine()) != null){
                res = callback.doSomthingWithLine(line, res);
            }
            return res;
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw e;

        } finally {
            if(br != null){
                try{
                    br.close();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Integer calcSum(String filepath) throws IOException {
        LineCallback<Integer> sumCallback =
                new LineCallback<Integer>() {
                    public Integer doSomthingWithLine(String line, Integer value){
                        return value += Integer.valueOf(line);
                    }
                };
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String numFilepath) throws IOException {
        LineCallback<Integer> multiplyCallback =
                new LineCallback<Integer>() {
                    public Integer doSomthingWithLine(String line, Integer value){
                        return value *= Integer.valueOf(line);
                    }
                };
        return lineReadTemplate(numFilepath, multiplyCallback, 1);
    }

    public String concatenate(String filepath) throws IOException{
        LineCallback<String> concatenateCallback =
                new LineCallback<String>() {
                    public String doSomthingWithLine(String line, String value) {
                        return value + line;
                    }
                };
        return lineReadTemplate(filepath, concatenateCallback, "");
    }

}

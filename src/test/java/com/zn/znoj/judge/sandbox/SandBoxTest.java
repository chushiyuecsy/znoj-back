package com.zn.znoj.judge.sandbox;

import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.model.enums.RunSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@SpringBootTest
class SandBoxTest {

    @Value("${sandbox.type}")
    private String type;
    @Test
    void executeCodeByValue() {
        Sandbox sandBox = SandboxFactory.newInstance(type);


    }

    @Test
    void executeCodeByProxy() {
        Sandbox sandBox = SandboxFactory.newInstance(type);
        sandBox = new SanboxProxy(sandBox);
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNextLine()) {
//            String type = sc.next();
//            Sandbox sandBox = SandboxFactory.newInstance(type);
//
//            String code = "#include <stdio.h> int main(){printf(\"Hello World\");return 0;}";
//            String language = RunSubmitLanguageEnum.C.getValue();
//            List<String> inputList = Arrays.asList("12543 4532", "456 5663");
//            ExecuteCodeRequest build = ExecuteCodeRequest
//                    .builder()
//                    .code(code)
//                    .language_id(RunSubmitLanguageEnum.getIndexByValue(language))
//                    .inputs(inputList)
//                    .build();
//            ExecuteCodeResponse executeCodeResponse = sandBox.executeCode(build);
//        }
//        sc.close();
    }
}
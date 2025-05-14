package com.zn.znoj.judge.sandbox.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.judge.sandbox.Sandbox;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.model.dto.question.JudgeCase;
import com.zn.znoj.model.entity.Question;
import com.zn.znoj.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 调用第三方沙箱
 * @Author zhaoning
 * @Date 2025/3/5
 */

@Slf4j
@Service
public class MyCloudSandboxImpl implements Sandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request, QuestionService questionService) throws IOException, CloneNotSupportedException {
        return getResponses(getTokens(request, questionService));
    }

    @Override
    public List<String> getTokens(ExecuteCodeRequest request, QuestionService questionService) throws IOException, CloneNotSupportedException {


        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.putOnce("source_code", Base64.encode(request.getCode()));

        jsonObject.putOnce("language_id", request.getLanguage_id());


        String url = "http://39.99.237.71:2358/submissions?base64_encoded=true";
        List<String> inputs = request.getInputs();
        List<String> tokens = new ArrayList<>();
        // 获取答案
        Question question = questionService.getById(request.getQuestionId());
        List<String> ans = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class).stream()
                .map(JudgeCase::getOutput).collect(Collectors.toList());
        double timeLimit = JSONUtil.parseObj(question.getJudgeConfig()).get("timeLimit", Integer.class) / 1000.0;
        int memoryLimit = JSONUtil.parseObj(question.getJudgeConfig()).get("memoryLimit", Integer.class) * 1000;

        for (int i = 0; i < inputs.size(); i++) {
            CloseableHttpClient client = HttpClients.createDefault();
            JSONObject subJson = jsonObject.clone();
            subJson.putOnce("stdin", Base64.encode(inputs.get(i)));
            subJson.putOnce("expected_output", Base64.encode(ans.get(i)));
            subJson.putOnce("cpu_time_limit", timeLimit);
            subJson.putOnce("memory_limit", memoryLimit);
            subJson.putOnce("stack_limit", memoryLimit / 2);
            StringEntity entity = new StringEntity(subJson.toString(), "UTF-8");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            client.close();
            String token = EntityUtils.toString(response.getEntity(), "UTF-8");
            token = JSONUtil.parseObj(token).get("token").toString();
            tokens.add(token);
        }

        return tokens;
    }
    @Override
    public ExecuteCodeResponse getResponses(List<String> tokens) throws IOException, CloneNotSupportedException {

        String url = "http://39.99.237.71:2358/submissions";
        CloseableHttpClient client = HttpClients.createDefault();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();

        executeCodeResponse.setTokens(tokens);
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setTime(-1);
        executeCodeResponse.setMemory(-1);
        List<String> outputs = new ArrayList<>();
        int cnt = 0;
        int time = -1;
        int memory = -1;
        for (String token : tokens) {
            try {
                client = HttpClients.createDefault();
                System.out.println("token is\t" + token);

                HttpGet httpGet = new HttpGet(url + "/" + token);
                HttpResponse response = client.execute(httpGet);
                client.close();
                if (response.getStatusLine().getStatusCode() / 100 != 2) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "请求沙箱失败");
                }

                JSONObject jsonObj = JSONUtil.parseObj(EntityUtils.toString(response.getEntity(), "UTF-8"));
                Integer status = JSONUtil.parseObj(jsonObj.get("status")).get("id", Integer.class);



                if (status == 3 || status == 4) {
                    executeCodeResponse.setStatus(status);
                    String output = jsonObj.get("stdout").toString();
                    outputs.add(output);
                    System.out.println(output);
                    int timeTmp = (int)(jsonObj.get("time", Double.class) * 1000);
                    int memoryTmp = jsonObj.get("memory", Integer.class);
                    time = Math.max(timeTmp, time);
                    memory = Math.max(memoryTmp, memory);
//                    executeCodeResponse.setMemory(Math.max(executeCodeResponse.getMemory(), memory));
//                    executeCodeResponse.setStatus(Math.min(executeCodeResponse.getStatus(), status));
                    if (status == 3) cnt++;
                }
                else if (status > 4) {
//                    if (status == 4) {
////                        time = (int)(jsonObj.get("time", Double.class) * 1000);
////                        memory = jsonObj.get("memory", Integer.class);
////                        executeCodeResponse.setTime(Math.max(executeCodeResponse.getTime(), time));
////                        executeCodeResponse.setMemory(Math.max(executeCodeResponse.getMemory(), memory));
//                        executeCodeResponse.setStatus(4);
//                    }
                    executeCodeResponse.setStatus(Math.max(executeCodeResponse.getStatus(), status));
                    executeCodeResponse.setOutputs(outputs);
                    executeCodeResponse.setAcNum(cnt);
                    return executeCodeResponse;
                }
                else executeCodeResponse.setStatus(Math.min(executeCodeResponse.getStatus(), status));

            } catch (Exception e) {
                log.error("第三方沙箱请求失败捏");
                e.printStackTrace();
            }
        }
        client.close();
        if (cnt == tokens.size()) executeCodeResponse.setStatus(3);
        else if (executeCodeResponse.getStatus() == 2) {
            return executeCodeResponse;
        }
        executeCodeResponse.setOutputs(outputs);
        executeCodeResponse.setAcNum(cnt);
        executeCodeResponse.setTime(time);
        executeCodeResponse.setMemory(memory);
        return executeCodeResponse;
    }
}
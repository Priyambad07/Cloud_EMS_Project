package com.expenditure;

import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.regions.Region;

import java.nio.charset.StandardCharsets;

public class Handler {
    public static void invokeLambdaFunction() {
        LambdaClient lambdaClient = LambdaClient.builder()
                .region(Region.of("ap-south-1")) // Set region to AP_SOUTH_1 (Mumbai)
                .build();

        // Define the request to invoke the Lambda function
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("MyLambdaFunction") // Replace with your Lambda function name
                .payload("{\"key\":\"value\"}".getBytes(StandardCharsets.UTF_8)) // Payload as a JSON string
                .build();

        // Invoke the Lambda function
        InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);

        // Get the response from the Lambda function
        String responsePayload = invokeResponse.payload().asUtf8String();

        System.out.println("Lambda Response: " + responsePayload);
    }
}

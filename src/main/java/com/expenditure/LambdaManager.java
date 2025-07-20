package  com.expenditure;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.Runtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LambdaManager {
    public static void createLambdaFunction() {
        LambdaClient lambdaClient = LambdaClient.create();

        try {
            byte[] functionCodeBytes = Files.readAllBytes(Paths.get("lambda-function.zip"));
            SdkBytes functionCode = SdkBytes.fromByteArray(functionCodeBytes);

            CreateFunctionRequest request = CreateFunctionRequest.builder()
                    .functionName("StoreExpensesFunction")
                    .runtime(Runtime.JAVA11)
                    .role("arn:aws:iam::YOUR_ACCOUNT_ID:role/CloudEMS_LambdaRole") // Replace with your actual role ARN
                    .handler("com.expenditure.Handler::handleRequest")
                    .code(FunctionCode.builder().zipFile(functionCode).build())
                    .build();

            lambdaClient.createFunction(request);
            System.out.println("✅ Lambda function created successfully.");

        } catch (IOException e) {
            System.err.println("❌ Error reading lambda zip file: " + e.getMessage());
        }
    }
}

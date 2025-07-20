package com.expenditure;

public class MainApp {
    public static void main(String[] args) {
        IAMManager.createIAMRole();
        EC2Manager.launchEC2WithEBS();
        S3Manager.createBucketAndUpload("expenses.csv");
        LambdaManager.createAndInvokeLambda();
        System.out.println("🎉 All AWS Tasks Completed Successfully.");
    }
}
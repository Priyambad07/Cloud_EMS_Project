package com.expenditure;

import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.*;

public class IAMManager {
    public static void createIAMRole() {
        IamClient iam = IamClient.create();

        String trustPolicy = "{\n" +
                "  \"Version\": \"2012-10-17\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Principal\": {\"Service\": \"ec2.amazonaws.com\"},\n" +
                "      \"Action\": \"sts:AssumeRole\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Create Role
        CreateRoleRequest roleRequest = CreateRoleRequest.builder()
                .roleName("CloudEMS_EC2Role")
                .assumeRolePolicyDocument(trustPolicy)
                .build();

        CreateRoleResponse roleResponse = iam.createRole(roleRequest);

        // Attach Policies
        iam.attachRolePolicy(AttachRolePolicyRequest.builder()
                .roleName("CloudEMS_EC2Role")
                .policyArn("arn:aws:iam::aws:policy/AmazonEC2FullAccess")
                .build());
        iam.attachRolePolicy(AttachRolePolicyRequest.builder()
                .roleName("CloudEMS_EC2Role")
                .policyArn("arn:aws:iam::aws:policy/AmazonS3FullAccess")
                .build());

        System.out.println("✅ IAM Role Created and Policies Attached.");
    }
}

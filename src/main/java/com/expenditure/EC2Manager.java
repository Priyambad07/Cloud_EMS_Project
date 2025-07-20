package com.expenditure;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;
import software.amazon.awssdk.regions.Region;

import java.util.Base64;

public class EC2Manager {
    public static void launchEC2WithEBS() {
        Ec2Client ec2 = Ec2Client.builder()
                .region(Region.AP_SOUTH_1) // AP_SOUTH_1 region
                .build();

        RunInstancesRequest request = RunInstancesRequest.builder()
                .imageId("ami-0c02fb55956c7d316")  // Change to your preferred AMI
                .instanceType(InstanceType.T2_MICRO)
                .minCount(1)
                .maxCount(1)
                .iamInstanceProfile(IamInstanceProfileSpecification.builder()
                        .name("CloudEMS_EC2Role")
                        .build())
                .userData(Base64.getEncoder().encodeToString(("#!/bin/bash\n" +
                        "yum update -y\n" +
                        "yum install -y java-11-amazon-corretto unzip\n" +
                        "aws s3 cp s3://cloud-ems-bucket/ExpenditureApp.jar /home/ec2-user/\n" +
                        "java -jar /home/ec2-user/ExpenditureApp.jar\n").getBytes()))
                .build();

        RunInstancesResponse response = ec2.runInstances(request);
        String instanceId = response.instances().get(0).instanceId();
        System.out.println("✅ EC2 Instance Launched: " + instanceId);

        // Create and attach EBS volume
        CreateVolumeRequest volumeRequest = CreateVolumeRequest.builder()
                .availabilityZone("ap-south-1a") // Change to your AZ
                .size(8)  // 8GB volume size
                .volumeType(VolumeType.GP2)
                .build();

        CreateVolumeResponse volumeResponse = ec2.createVolume(volumeRequest);
        String volumeId = volumeResponse.volumeId();
        System.out.println("✅ EBS Volume Created: " + volumeId);
    }
}

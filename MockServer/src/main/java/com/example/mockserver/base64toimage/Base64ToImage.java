package com.example.mockserver.base64toimage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class Base64ToImage {
    public static void main(String[] args) throws Exception {
        // base64 문자열이 저장된 파일 경로
        String base64FilePath = "src/main/java/com/example/mockserver/images/image.txt";
        // base64 문자열 읽기
        String base64String = new String(Files.readAllBytes(Paths.get(base64FilePath)));
        // 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        // 이미지 파일로 저장
        Files.write(Paths.get("src/main/java/com/example/mockserver/images/decoded-image.png"), decodedBytes);
    }
}
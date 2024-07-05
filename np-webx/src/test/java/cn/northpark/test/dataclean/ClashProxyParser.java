package cn.northpark.test.dataclean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤掉SS节点的精选节点
 * @author bruce
 * @date 2024年07月05日 10:56:32
 */
public class ClashProxyParser {
    public static void main(String[] args) {
        String sourceFilePath = "C:\\Users\\Bruce\\Desktop\\clash.yaml";
        String targetFilePath = "C:\\Users\\Bruce\\Desktop\\output.yaml";


        List<String> lines = readLinesFromFile(sourceFilePath);

        if (lines != null) {
            List<String> updatedLines = removeShadowsocksProxies(lines);
            writeLinesToFile(updatedLines, targetFilePath);
            System.out.println("Proxy configuration updated and saved to " + targetFilePath);
        } else {
            System.out.println("Failed to read the proxy configuration file.");
        }
    }

    private static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lines;
    }

    private static void writeLinesToFile(List<String> lines, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> removeShadowsocksProxies(List<String> lines) {
        List<String> updatedLines = new ArrayList<>();
        List<String> rmNames = new ArrayList<>();
        boolean inProxiesSection = false;
        boolean inProxyGroupSection = false;
        boolean passed = false;

        for (String line : lines) {
            if (line.trim().equals("proxies:") && !passed) {
                inProxiesSection = true;
            }

            if(inProxiesSection){
                if ( line.trim().startsWith("- {name:")) {
                    if (!line.contains("type: ss")) {
                        updatedLines.add(line);
                    }else{
                        String replace = line.replace("- ", "").trim();
                        rmNames.add(replace);
                    }
                } else {
                    updatedLines.add(line);
                }
            }



            if (line.trim().equals("proxy-groups:")) {
                inProxiesSection = false;
                inProxyGroupSection = true;
                passed = true;
            }

            if(inProxyGroupSection){
                if(!inProxiesSection){
                    if(line.trim().startsWith("- ") && rmNames.toString().contains((line.replace("- ","").trim()))){
                        //删除
                    }else{
                        if(!line.trim().equals("proxy-groups:")) {
                            updatedLines.add(line);
                        }
                    }
                }
            }

            if (!inProxyGroupSection && !inProxiesSection && !line.trim().equals("proxy-groups:")) {
                updatedLines.add(line);
            }



        }

        return updatedLines;
    }
}

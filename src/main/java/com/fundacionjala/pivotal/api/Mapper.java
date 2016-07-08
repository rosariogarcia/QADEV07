package com.fundacionjala.pivotal.api;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jayway.restassured.response.Response;

import static com.jayway.restassured.path.json.JsonPath.from;

public final class Mapper {

    private static final String REGEX_INSIDE_BRACKETS = "(?<=\\[)(.*?)(?=\\])";

    private static final String REGEX_DOT = "\\.";

    private static final String REGEX_BRACKETS = "[\\[\\]]";

    private static final String REGEX_UNTIL_PROJECT = "^(\\/.*?\\/.*?\\/)";

    private Mapper() {
    }

    public static String getField(Response response, String parameter) {
        return from(response.asString()).get(parameter).toString();
    }

    public static String mapEndpoint(String endPoint, Map<String, Response> listResponses) {
        Matcher matches = Pattern.compile(REGEX_INSIDE_BRACKETS).matcher(endPoint);
        StringBuffer newEndPoint = new StringBuffer();
        String replaceParameter = "";
        while (matches.find()) {
            String[] parametersParts = matches.group().split(REGEX_DOT, 2);
            String parameter = parametersParts[0];
            String field = parametersParts[1];
            replaceParameter = Mapper.getField(listResponses.get(parameter), field);
            matches.appendReplacement(newEndPoint, replaceParameter);
        }
        matches.appendTail(newEndPoint);
        return newEndPoint.toString().replaceAll(REGEX_BRACKETS, "");
    }

    public static String mapUrlToDeleteProject(String endPoint) {
        String projectUrlPart = "";
        Matcher matches = Pattern.compile(REGEX_UNTIL_PROJECT).matcher(endPoint);
        while (matches.find()) {
            projectUrlPart = matches.group();
        }
        return projectUrlPart;
    }
}

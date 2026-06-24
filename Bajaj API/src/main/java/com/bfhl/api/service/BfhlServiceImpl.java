package com.bfhl.api.service;

import com.bfhl.api.config.UserProperties;
import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BfhlServiceImpl implements BfhlService {

    private final UserProperties userProperties;

    public BfhlServiceImpl(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<Character> alphaChars = new ArrayList<>();
        long sum = 0;

        for (String item : request.getData()) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                long value = Long.parseLong(item);
                sum += value;
                if (value % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                alphabets.add(item.toUpperCase());
                for (char c : item.toCharArray()) {
                    if (Character.isLetter(c)) {
                        alphaChars.add(c);
                    }
                }
            } else {
                specialCharacters.add(item);
            }
        }

        BfhlResponse response = new BfhlResponse();
        response.setSuccess(true);
        response.setUserId(buildUserId());
        response.setEmail(userProperties.getEmail());
        response.setRollNumber(userProperties.getRollNumber());
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcatString(buildConcatString(alphaChars));
        return response;
    }

    private String buildUserId() {
        String namePart = userProperties.getFullName()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "_");
        return namePart + "_" + userProperties.getDob();
    }

    private boolean isNumeric(String value) {
        if (!value.matches("-?\\d+")) {
            return false;
        }
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isAlphabetic(String value) {
        for (char c : value.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private String buildConcatString(List<Character> chars) {
        if (chars.isEmpty()) {
            return "";
        }

        List<Character> reversed = new ArrayList<>(chars);
        Collections.reverse(reversed);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}

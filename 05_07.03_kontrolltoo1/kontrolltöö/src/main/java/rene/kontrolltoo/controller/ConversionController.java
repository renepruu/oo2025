package rene.kontrolltoo.controller;

import rene.kontrolltoo.entity.ConversionEntity;
import rene.kontrolltoo.entity.NumberEntity;
import rene.kontrolltoo.repository.ConversionRepository;
import rene.kontrolltoo.repository.NumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class ConversionController {

    @Autowired
    private NumberRepository numberRepository;

    @Autowired
    private ConversionRepository conversionRepository;

    @PostMapping
    public ResponseEntity<String> convertNumbers(@RequestBody Map<String, Object> request) {
        int base = (int) request.get("base");

        List<NumberEntity> numbers = numberRepository.findAll();
        if (numbers.isEmpty()) {
            return ResponseEntity.badRequest().body("No numbers to convert.");
        }

        String conversionType;
        switch (base) {
            case 2:
                conversionType = "binary";
                break;
            case 8:
                conversionType = "octal";
                break;
            case 16:
                conversionType = "hex";
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid base. Use 2, 8, or 16.");
        }

        for (NumberEntity numberEntity : numbers) {
            int number = numberEntity.getNumber();
            String convertedValue = convertToBase(number, base);

            ConversionEntity conversion = new ConversionEntity();
            conversion.setOriginalNumber(number);
            conversion.setConversionType(conversionType);
            conversion.setConvertedValue(convertedValue);
            conversionRepository.save(conversion);
        }

        return ResponseEntity.ok("Converted all numbers to base " + base);
    }

    private String convertToBase(int number, int base) {
        return switch (base) {
            case 2 -> Integer.toBinaryString(number);
            case 8 -> Integer.toOctalString(number);
            case 16 -> Integer.toHexString(number);
            default -> throw new IllegalArgumentException("Invalid base");
        };
    }

    @PostMapping("/reverse")
    public ResponseEntity<String> reverseConversion(@RequestBody Map<String, Object> request) {
        String conversionType = (String) request.get("conversionType");

        List<ConversionEntity> conversions = conversionRepository.findAll();
        if (conversions.isEmpty()) {
            return ResponseEntity.badRequest().body("No conversions found.");
        }

        StringBuilder result = new StringBuilder("Original numbers:\n");

        for (ConversionEntity conversion : conversions) {
            if (!conversion.getConversionType().equals(conversionType)) {
                continue;
            }

            String convertedValue = conversion.getConvertedValue();
            int originalNumber = convertBackToDecimal(convertedValue, conversionType);
            result.append(convertedValue).append(" -> ").append(originalNumber).append("\n");
        }

        return ResponseEntity.ok(result.toString());
    }
    private int convertBackToDecimal(String value, String conversionType) {
        return switch (conversionType) {
            case "binary" -> Integer.parseInt(value, 2);
            case "octal" -> Integer.parseInt(value, 8);
            case "hex" -> Integer.parseInt(value, 16);
            default -> throw new IllegalArgumentException("Invalid conversion type");
        };
    }
}

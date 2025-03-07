package rene.kontrolltoo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conversion_results")
public class ConversionEntity {
@Getter
@Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int originalNumber;
    private String conversionType;  // "binary", "octal", "hex"
    private String convertedValue;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getOriginalNumber() { return originalNumber; }
    public void setOriginalNumber(int originalNumber) { this.originalNumber = originalNumber; }

    public String getConversionType() { return conversionType; }
    public void setConversionType(String conversionType) { this.conversionType = conversionType; }

    public String getConvertedValue() { return convertedValue; }
    public void setConvertedValue(String convertedValue) { this.convertedValue = convertedValue; }
}

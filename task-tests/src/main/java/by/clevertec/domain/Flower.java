package by.clevertec.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
public class Flower {

    private UUID id;
    private String commonName;
    private String plantFamily;
}

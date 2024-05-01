package model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j;
import model.entity.enums.Group;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Log4j
public class Product {
    private int id;
    private String name;
    private Group productGroup;
    private int buyPrice;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

package se.lexicon.almgru.assignmentjpaj33.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RecipeInstruction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;

    private String instructions;

    public RecipeInstruction(String uuid, String instructions) {
        this.uuid = uuid;
        this.instructions = instructions;
    }

    public RecipeInstruction(String instructions) {
        this(null, instructions);
    }

    public RecipeInstruction() { }

    public String getUuid() {
        return uuid;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeInstruction that = (RecipeInstruction) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, instructions);
    }

    @Override
    public String toString() {
        return "RecipeInstruction{" +
                "uuid='" + uuid + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}

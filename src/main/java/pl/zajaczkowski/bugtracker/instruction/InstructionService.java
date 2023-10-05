package pl.zajaczkowski.bugtracker.instruction;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.project.Project;

import java.util.List;

@Service
public class InstructionService {

    private final InstructionRepository instructionRepository;

    public InstructionService(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    public List<Instruction> findAllInstructions() {
        return instructionRepository.findAll();
    }

    void saveInstruction(Instruction instruction) {
        instructionRepository.save(instruction);
    }

}

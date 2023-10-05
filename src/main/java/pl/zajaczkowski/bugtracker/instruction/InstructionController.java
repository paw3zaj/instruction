package pl.zajaczkowski.bugtracker.instruction;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zajaczkowski.bugtracker.auth.PersonService;
import pl.zajaczkowski.bugtracker.project.Project;

import jakarta.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/instructions")
public class InstructionController {

    private final InstructionService instructionService;
    private final PersonService personService;

    public InstructionController(InstructionService instructionService, PersonService personService) {
        this.instructionService = instructionService;
        this.personService = personService;
    }

    @GetMapping
    String showInstructionList(Model model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("instructions", instructionService.findAllInstructions());
        model.addAttribute("access", true);
        model.addAttribute("currentPage", "instructions");
        return "instruction/instructions";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_PROJECT")
    String showAdd(Principal principal, Model model) {
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "instructions");
        model.addAttribute("permission", permission);
        model.addAttribute("instruction", new Instruction());
        return "instruction/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    public String save(@Valid Instruction instruction, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            var permission = personService.hasPermission(principal.getName());

            model.addAttribute("permission", permission);

            model.addAttribute("instruction", instruction);
            return "project/add";
        }
        instructionService.saveInstruction(instruction);
        return "redirect:/instructions";
    }

//    @GetMapping("/edit")
//    @Secured("ROLE_MANAGE_PROJECT")
//    public String showUpdate(@RequestParam Long id, Principal principal, Model model) {
//        Issue issue = issueService.findIssueById(id).orElse(null);
//        if (issue == null) {
//            return "redirect:/issues";
//        }
//        var permission = personService.hasPermission(principal.getName());
//
//        model.addAttribute("currentPage", "issues");
//        model.addAttribute("permission", permission);
//        model.addAttribute("issue", issue);
//        return "issue/add";
//    }
//
//    @GetMapping("/remove")
//    @Secured("ROLE_MANAGE_PROJECT")
//    public String delete(@RequestParam Long id) {
//        var optionalIssue = instructionService.findIssueById(id);
//        instructionService.deleteIssue(id);
//
//        return "redirect:/issues";
//    }

}

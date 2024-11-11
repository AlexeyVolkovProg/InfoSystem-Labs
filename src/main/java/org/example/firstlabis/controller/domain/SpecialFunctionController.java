package org.example.firstlabis.controller.domain;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.model.domain.enums.WeaponType;
import org.example.firstlabis.service.domain.HumanBeingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/function")
@RequiredArgsConstructor
public class SpecialFunctionController {

    private final HumanBeingService humanBeingService;

    @Operation(summary = "Delete all HumanBeing entities by WeaponType")
    @DeleteMapping("/delete/weaponType/{weaponType}")
    public ResponseEntity<Void> deleteAllByWeaponType(@PathVariable WeaponType weaponType){
        humanBeingService.deleteAllByWeaponType(weaponType);
        return ResponseEntity.noContent().build();

    }
    @Operation(summary = "Deletes all HumanBeing entities where the hasToothpick field is false.")
    @DeleteMapping("/delete/toothpick-false")
    public ResponseEntity<Void> deleteAllByFalseToothpick(){
        humanBeingService.deleteAllWithOutToothpicks();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Sets the mood field of all HumanBeing entities to SORROW.")
    @PutMapping("/assign/sorrow-mood")
    public ResponseEntity<Void> assignSorrowMoodForAllHumans(){
        humanBeingService.setAllMoodToSorrow();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Returns the count of HumanBeing entities where minutesOfWaiting " +
            "is less than the specified value.")
    @GetMapping("/count-minutes-less-than")
    public ResponseEntity<Long> countByMinutesOfWaitingLessThan(@RequestParam long maxMinutesOfWaiting){
        long count = humanBeingService.countByMinutesOfWaitingLessThan(maxMinutesOfWaiting);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Returns a set of unique values of the impactSpeed field across all HumanBeing entities.")
    @GetMapping("/uniqueImpactSpeeds")
    public ResponseEntity<Set<Integer>> getUniqueImpactSpeeds() {
        Set<Integer> uniqueImpactSpeeds = humanBeingService.getUniqueImpactSpeeds();
        return ResponseEntity.ok(uniqueImpactSpeeds);
    }



}

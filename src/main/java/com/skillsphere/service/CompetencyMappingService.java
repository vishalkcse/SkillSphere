package com.skillsphere.service;

import com.skillsphere.dto.CompetencyGapDTO;
import com.skillsphere.entity.CompetencyFramework;
import com.skillsphere.entity.EmployeeSkill;
import com.skillsphere.repository.CompetencyFrameworkRepository;
import com.skillsphere.repository.EmployeeSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetencyMappingService {

    private final EmployeeSkillRepository employeeSkillRepository;
    private final CompetencyFrameworkRepository competencyFrameworkRepository;

    public List<CompetencyGapDTO> getGapsForRole(UUID empId, String targetRoleTitle) {
        List<EmployeeSkill> currentSkills = employeeSkillRepository.findByEmployeeEmpId(empId);
        List<CompetencyFramework> requirements = competencyFrameworkRepository.findByRoleTitleIgnoreCase(targetRoleTitle);
        if (requirements.isEmpty()) {
            requirements = competencyFrameworkRepository.findByRoleTitle(targetRoleTitle);
        }

        return requirements.stream()
                .filter(req -> req.getSkill() != null)
                .map(req -> {
                    int current = currentSkills.stream()
                            .filter(es -> es.getSkill() != null && es.getSkill().getSkillId().equals(req.getSkill().getSkillId()))
                            .findFirst()
                            .map(EmployeeSkill::getProficiency)
                            .orElse(0);

                    int required = req.getRequiredProficiency() != null ? req.getRequiredProficiency() : 0;
                    int gap = required - current;

                    return CompetencyGapDTO.builder()
                            .skillName(req.getSkill().getName())
                            .currentProficiency(current)
                            .requiredProficiency(required)
                            .gap(gap)
                            .build();
                }).collect(Collectors.toList());
    }
}

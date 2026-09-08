package com.skillsphere.service;

import com.skillsphere.dto.SkillDTO;
import com.skillsphere.entity.Skill;
import com.skillsphere.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillCatalogService {

    private final SkillRepository skillRepository;

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SkillDTO addSkill(SkillDTO dto) {
        String categoryStr = dto.getCategory();
        if (categoryStr != null) {
            try {
                categoryStr = Skill.Category.valueOf(categoryStr.toUpperCase()).name();
            } catch (Exception ignored) {
            }
        } else {
            categoryStr = "TECHNICAL";
        }

        Skill skill = Skill.builder()
                .skillName(dto.getName())
                .category(categoryStr)
                .build();

        return toDTO(skillRepository.save(skill));
    }

    private SkillDTO toDTO(Skill skill) {
        String categoryStr = skill.getCategory();
        if (categoryStr != null) {
            try {
                categoryStr = Skill.Category.valueOf(categoryStr.toUpperCase()).name();
            } catch (Exception ignored) {
            }
        } else {
            categoryStr = "TECHNICAL";
        }

        return SkillDTO.builder()
                .skillId(skill.getSkillId())
                .name(skill.getName())
                .category(categoryStr)
                .build();
    }
}

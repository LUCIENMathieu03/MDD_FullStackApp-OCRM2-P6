package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ThemeCreateRequestDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThemeService {

    @Autowired
    ThemeRepository themeRepository;

    public ThemeDTO createTheme(ThemeCreateRequestDTO dto){
        Theme themeToCreate = new Theme();

        themeToCreate.setName(dto.getName());
        themeToCreate.setDescription(dto.getDescription());

        Theme savedTheme = themeRepository.save(themeToCreate);

        return toThemeDTO(savedTheme);
    }

    public List<ThemeDTO> getAllTheme(){
        List<ThemeDTO> ThemeDtoList = new ArrayList<>();

        for(Theme theme :themeRepository.findAll()){
            ThemeDtoList.add(toThemeDTO(theme));
        }
        return ThemeDtoList;
    }

    private ThemeDTO toThemeDTO(Theme theme) {
        ThemeDTO dto = new ThemeDTO();

        dto.setId(theme.getId());
        dto.setName(theme.getName());
        dto.setDescription(theme.getDescription());
        dto.setCreatedAt(theme.getCreatedAt());
        dto.setUpdatedAt(theme.getUpdatedAt());

        return dto;

    }


}

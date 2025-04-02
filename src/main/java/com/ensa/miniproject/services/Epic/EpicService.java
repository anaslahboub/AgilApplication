package com.ensa.miniproject.services.Epic;

import com.ensa.miniproject.DTO.EpicDTO;

import java.util.List;

public interface EpicService {
    public EpicDTO createEpic(EpicDTO epicDTO);
    public EpicDTO getEpicById(Long id);
    public EpicDTO updateEpic(EpicDTO epicDTO);
    public void deleteEpic(Long id);
    public List<EpicDTO> getEpics();

    /// /////////// METIER /////////////////
    EpicDTO affectUserStoriesToEpic(Long epicId, List<Long> userStoryIds);
    EpicDTO removeUserStoryFromEpic(Long epicId, Long userStoryId);

}

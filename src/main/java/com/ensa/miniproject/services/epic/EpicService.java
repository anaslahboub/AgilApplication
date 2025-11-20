package com.ensa.miniproject.services.epic;

import com.ensa.miniproject.dto.EpicDTO;

import java.util.List;

public interface EpicService {
     EpicDTO createEpic(EpicDTO epicDTO);
     EpicDTO getEpicById(Long id);
     EpicDTO updateEpic(EpicDTO epicDTO);
     void deleteEpic(Long id);
     List<EpicDTO> getEpics();
     EpicDTO affectUserStoriesToEpic(Long epicId, List<Long> userStoryIds);
     EpicDTO removeUserStoryFromEpic(Long epicId, Long userStoryId);

}

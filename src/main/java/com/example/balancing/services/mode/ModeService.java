package com.example.balancing.services.mode;

import com.example.balancing.entity.mode.Mode;

public interface ModeService {
    Mode getModeById(Long id);

    Mode createMode(Mode mode);

    Mode updateMode(Long id, Mode mode);

    void deleteMode(Long id);
}

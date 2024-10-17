package com.example.balancing.services.mode;

import com.example.balancing.exception.mode.ModeNotFoundException;
import com.example.balancing.models.mode.Mode;
import com.example.balancing.repositories.mode.ModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeServiceImpl implements ModeService {

    @Autowired
    ModeRepository modeRepository;

    @Override
    public Mode getModeById(Long id) {
        return modeRepository.findById(id).orElseThrow(ModeNotFoundException::new);
    }

    @Override
    public Mode createMode(Mode mode) {
        return modeRepository.save(mode);
    }

    @Override
    public Mode updateMode(Long id, Mode mode) {
        Mode existingMode = getModeById(id);
        existingMode.setName(mode.getName());
        return createMode(existingMode);
    }

    @Override
    public void deleteMode(Long id) {
        modeRepository.deleteById(id);
    }
}

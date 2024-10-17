package com.example.balancing.services.unit;

import com.example.balancing.exception.unit.UnitNotFoundException;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(UnitNotFoundException::new);
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setPlanes(unit.getPlanes());
        existingUnit.setModes(unit.getModes());
        existingUnit.setPoints(unit.getPoints());
        existingUnit.setDescription(unit.getDescription());
        existingUnit.setVibrationPrecision(unit.getVibrationPrecision());
        existingUnit.setVibrationUnitMeasure(unit.getVibrationUnitMeasure());
        existingUnit.setWeightPrecision(unit.getWeightPrecision());
        existingUnit.setWeightUnitMeasure(unit.getWeightUnitMeasure());
        return createUnit(existingUnit);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    public Unit getFilledUnitById(Long id) {
        return calculateSensitivities(getUnitById(id));
    }

    /**
     * Вычисляет суммарные комплексные веса всех пусков,
     * связанных с агрегатом {@link Unit}.
     * <p>
     * Метод сортирует пуски агрегата по порядковому номеру и последовательно
     * добавляет веса. Для каждого пуска проверяется, ссылается ли он на
     * предыдущий пуск (по полю referenceRunId). Если ссылка на предыдущий пуск есть,
     * то его вес добавляется к суммарному весу. Если ссылки нет, текущий вес
     * пуска добавляется к суммарному весу и обновляется поле
     * {@code complexTotalWeight}.
     * </p>
     *
     * @param unit агрегат {@link Unit}, для которого нужно вычислить
     *             суммарные веса пусков
     * @return объект {@link Unit} с обновленными пусками и суммарными весами
     */
    private Unit calculateTotalWeights(Unit unit) {
        List<Run> runs = unit.getRuns().stream()
                .sorted(Comparator.comparing(Run::getNumber)).toList();

        Complex totalWeight = new Complex(0d, 0d);

        for (Run run : runs) {
            Weight weight = run.getWeight();

            if (weight == null)
                continue;

            Long refRunId = run.getReferenceRunId();

            if (refRunId != null) {
                // Найти предыдущий пуск
                findPreviousRun(runs, refRunId).ifPresent(previousRun -> {
                    Complex previousWeight = previousRun.getWeight() != null
                            ? previousRun.getWeight().getComplexWeight()
                            : new Complex(0d, 0d);
                    totalWeight.plus(previousWeight);
                });

            } else {

                // Обновляем вес текущего пуска
                totalWeight.plus(weight.getComplexWeight());
                weight.setComplexTotalWeight(totalWeight);
                run.setWeight(weight);

            }
        }

        unit.setRuns(runs);
        return unit;
    }

    /**
     * Вычисляет чувствительности для всех пусков, связанных с агрегатом
     * {@link Unit}.
     * <p>
     * Метод сначала вычисляет суммарные комплексные веса для всех пусков,
     * вызвав метод {@link #calculateTotalWeights(Unit)}.
     * Затем проходит по каждому пуску агрегата, и если пуск имеет ссылку на
     * другой пуск (через поле {@code referenceRunId}), то для каждого
     * {@link Record} текущего пуска ищутся записи из предыдущего пуска,
     * соответствующие одинаковым {@code point} и {@code mode}. После этого
     * для каждой такой записи рассчитывается комплексная чувствительность
     * с использованием метода {@link Record#calculateComplexSensitivity(Record)}.
     * </p>
     *
     * <p>
     * Если ссылки на предыдущий пуск нет, этот пуск пропускается.
     * </p>
     *
     * @param unit агрегат {@link Unit}, для которого нужно вычислить
     *             чувствительности пусков
     * @return объект {@link Unit} с обновленными пусками и рассчитанными
     * чувствительностями
     * @throws NullPointerException если у пусков или записей отсутствуют
     *                              обязательные данные, такие как вес или запись
     */
    private Unit calculateSensitivities(Unit unit) {

        // Рассчитываем суммарные веса для всех пусков
        calculateTotalWeights(unit);

        // Получаем список пусков
        var runs = unit.getRuns();

        for (Run run : runs) {
            Long refRunId = run.getReferenceRunId();

            // Если у пуска есть ссылка на предыдущий пуск
            if (refRunId != null && run.getWeight() != null) {
                // Ищем референтный пуск с нужным id
                Optional<Run> refRun = findPreviousRun(runs, refRunId);

                // Если предыдущий пуск найден, производим вычисления
                refRun.ifPresent(previousRun -> {
                    List<Record> previousRecords = previousRun.getWeight().getRecords();

                    // Проходим по записям пуска
                    for (Record record : run.getWeight().getRecords()) {

                        // Ищем совпадающие записи по точкам и режимам в предыдущем пуске
                        List<Record> matchedRefRecords = previousRecords.stream()
                                .filter(r -> r.getPoint().equals(record.getPoint()) &&
                                        r.getMode().equals(record.getMode()))
                                .toList();

                        // Для каждой совпавшей записи рассчитываем чувствительность
                        matchedRefRecords.forEach(r -> r.setComplexSensitivity(
                                record.calculateComplexSensitivity(r.getRecord())));

                    }
                });

            }
        }

        return unit;
    }

    private Optional<Run> findPreviousRun(List<Run> runs, Long refRunId) {
        return runs.stream()
                .filter(r -> r.getId().equals(refRunId))
                .findFirst();
    }

}
package com.example.balancing.dtos.record;

import com.example.balancing.dtos.weight.WeightDto;
import com.example.balancing.dtos.mode.SimplifiedModeDto;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.point.Point;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * DTO записи вибрации.
 * Этот класс инкапсулирует данные о вибрационных измерениях, включая
 * параметры вибрации, чувствительность, режим работы агрегата и связанную нагрузку.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>point:</b> Место измерения вибрации.</li>
 *   <li><b>mode:</b> Режим работы агрегата, в котором выполнено измерение.</li>
 *   <li><b>magVibration:</b> Значение амплитуды вибрации.</li>
 *   <li><b>phaseVibration:</b> Значение фазы вибрации в градусах.</li>
 *   <li><b>complexVibration:</b> Комплексное значение вибрации (в форме реальной и мнимой частей).</li>
 *   <li><b>isUsed:</b> Флаг использования записи в оптимизационном расчете.</li>
 *   <li><b>weightDto:</b> Груз, при котором было выполнено измерение вибрации.</li>
 *   <li><b>isManualSensitivity:</b> Флаг использования ручного задания чувствительности.</li>
 *   <li><b>magSensitivity:</b> Значение амплитуды чувствительности.</li>
 *   <li><b>phaseSensitivity:</b> Значение фазы чувствительности в градусах.</li>
 *   <li><b>complexSensitivity:</b> Комплексное значение чувствительности (в форме реальной и мнимой частей).</li>
 * </ul>
 */
@Data
@Schema(description = "DTO записи вибрации")
public class RecordDto {

    @Schema(description = "Место измерения вибрации", example = "ВГП или 1")
    private Point point;

    @Schema(description = "Режима работы агрегата в упрощенном предсталении," +
            " в котором выполнено измерение")
    private SimplifiedModeDto mode;

    @Schema(description = "Значение амплитуды вибрации", example = "123.1")
    private Double magVibration;

    @Schema(description = "Значение фазы вибрации в градусах (deg)",
            example = "180.0")
    private Double phaseVibration;

    @Schema(description = "Комплексное значение вибрации", example = "10.0+13.1i")
    private Complex complexVibration;

    @Schema(description = "Флаг использования в оптимизационном расчете",
            example = "true")
    private Boolean isUsed;

    @Schema(description = "Груз, при котором выполнено измерение")
    private WeightDto weightDto;

    @Schema(description = "Флаг использования ручного задчания чувствительности",
            example = "false")
    private Boolean isManualSensitivity;

    @Schema(description = "Значение амплитуды чувствительности", example = "4.1")
    private Double magSensitivity;

    @Schema(description = "Значение фазы чувствительности", example = "181.1")
    private Double phaseSensitivity;

    @Schema(description = "Комплесное значение чувствительности",
            example = "181.1+10i")
    private Complex complexSensitivity;

}

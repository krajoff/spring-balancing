package com.example.balancing.dtos.record;

import com.example.balancing.dtos.mode.ModeDto;
import com.example.balancing.dtos.point.SimplifiedPointDto;
import com.example.balancing.models.complex.Complex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO записи вибрации.
 * Этот класс инкапсулирует данные о вибрационных измерениях, включая
 * параметры вибрации, чувствительность, режим работы агрегата.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>point:</b> Место измерения вибрации.</li>
 *   <li><b>mode:</b> Режим работы агрегата, в котором выполнено измерение.</li>
 *   <li><b>magVibration:</b> Значение амплитуды вибрации.</li>
 *   <li><b>phaseVibration:</b> Значение фазы вибрации в градусах.</li>
 *   <li><b>complexVibration:</b> Комплексное значение вибрации (в форме реальной и мнимой частей).</li>
 *   <li><b>isUsed:</b> Флаг использования записи в оптимизационном расчете.</li>
 *   <li><b>isManualSensitivity:</b> Флаг использования ручного задания чувствительности.</li>
 *   <li><b>magSensitivity:</b> Значение амплитуды чувствительности.</li>
 *   <li><b>phaseSensitivity:</b> Значение фазы чувствительности в градусах.</li>
 *   <li><b>complexSensitivity:</b> Комплексное значение чувствительности (в форме реальной и мнимой частей).</li>
 * </ul>
 */
@Data
@Schema(description = "DTO записи вибрации")
public class RecordDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private  Long id;

    @Schema(description = "Название места измерения вибрации в упрощенном " +
            "представлении", example = "ВГП или 1")
    private SimplifiedPointDto point;

    @Schema(description = "Режима работы агрегата в упрощенном представлении")
    private ModeDto mode;

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

    @Schema(description = "Флаг использования ручного задания чувствительности",
            example = "false")
    private Boolean isManualSensitivity;

    @Schema(description = "Значение амплитуды чувствительности", example = "4.1")
    private Double magSensitivity;

    @Schema(description = "Значение фазы чувствительности", example = "181.1")
    private Double phaseSensitivity;

    @Schema(description = "Комплексное значение чувствительности",
            example = "181.1+10i")
    private Complex complexSensitivity;

}

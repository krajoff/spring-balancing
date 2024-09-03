package com.example.balancing.dtos.unit;

import com.example.balancing.dtos.weight.WeightDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * DTO агрегата, используемый на станции. Этот класс инкапсулирует
 * различные свойства агрегата, такие как его иден тификационный
 * номер, тип, и параметры измерений.
 * <p>
 * Класс предоставляет основную информацию об агрегате, включая его
 * уникальный идентификатор, номер на станции, тип агрегата, единицы
 * измерения для веса и вибрации, а также описание и список грузов,
 * относящихся к агрегату.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор агрегата.</li>
 *   <li><b>unitNumber:</b> Номер агрегата на станции (максимум 3 символа).</li>
 *   <li><b>type:</b> Тип агрегата (например, модель) (максимум 50 символов).</li>
 *   <li><b>weightPrecision:</b> Количество знаков после запятой при отображении веса (максимум 2 символа).</li>
 *   <li><b>weightUnitMeasure:</b> Единица измерения веса (например, кг, т) (максимум 5 символов).</li>
 *   <li><b>vibrationPrecision:</b> Количество знаков после запятой при отображении вибрации (максимум 2 символа).</li>
 *   <li><b>vibrationUnitMeasure:</b> Единица измерения вибрации (например, мкм, мм/с) (максимум 5 символов).</li>
 *   <li><b>description:</b> Дополнительное описание агрегата (максимум 255 символов).</li>
 *   <li><b>weights:</b> Список грузов, относящихся к агрегату.</li>
 *   <li><b>station:</b> Название станции.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO агрегата")
public class UnitDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Номер агрегата на станции", example = "1, 2 и т.д.")
    @Size(max = 3)
    private Integer unitNumber;

    @Schema(description = "Тип агрегата", example = "СВ 477/180-16 УХЛ4")
    @Size(max = 50)
    private String type;

    @Schema(description = "Количество знаков после запятой при " +
            "отображении значений грузов", example = "0")
    @Size(max = 2)
    private Integer weightPrecision;

    @Schema(description = "Единица измерения грузов", example = "г, кг, т")
    @Size(max = 5)
    private String weightUnitMeasure;

    @Schema(description = "Количество знаков после запятой " +
            "при отображении значений вибрации", example = "0")
    @Size(max = 2)
    private Integer vibrationPrecision;

    @Schema(description = "Единица измерения вибрации", example = "мкм, мм/с")
    @Size(max = 5)
    private String vibrationUnitMeasure;

    @Schema(description = "Дополнительное описание", example = "Измерения после ремонта")
    @Size(max = 255)
    private String description;

    @Schema(description = "Список грузов, относящихся к агрегату")
    private List<WeightDto> weights;

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС")
    private String station;

}

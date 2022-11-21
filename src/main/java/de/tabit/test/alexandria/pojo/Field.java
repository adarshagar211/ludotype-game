package de.tabit.test.alexandria.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {

	private int fieldPosition;
	private FieldType fieldType;
}
